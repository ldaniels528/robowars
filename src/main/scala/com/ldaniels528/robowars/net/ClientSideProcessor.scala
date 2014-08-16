package com.ldaniels528.robowars.net

import akka.actor.{Actor, ActorSystem, Props}
import com.ldaniels528.robowars.VirtualWorld
import com.ldaniels528.robowars.net.ClientSideProcessor._
import com.ldaniels528.robowars.net.NetworkActionProcessor._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
 * RoboWars Client-Side Processor
 * @author lawrence.daniels@gmail.com
 */
trait ClientSideProcessor {

  def client: Client

  def ~>(action: NetworkAction): Unit = clientSide ! action

  def <~[T](action: NetworkAction)(implicit ec: ExecutionContext): Future[T] = {
    import akka.pattern.ask
    import akka.util.Timeout

    implicit val timeout = new Timeout(30 seconds)

    (clientSide ? action).map(_.asInstanceOf[T])
  }

  def getRemoteWorld(client: Client): Future[VirtualWorld] = {
    clientSide ! WorldRequest(client, level = 1)
    makePromise
  }

  /**
   * Sets up a task to receive network actions
   */
  def scheduleUpdates(interval: FiniteDuration = 5.millis)(implicit ec: ExecutionContext) {
    system.scheduler.schedule(initialDelay = 0.millis, interval, new Runnable {
      val bytes = new Array[Byte](1024)
      override def run(): Unit = {
        if(client.in.available > 0) {
          // write the chunk of data to the client's buffer
          val count = client.in.read(bytes)
          //log ! s"Read $count bytes for client ${client.socket.getInetAddress.getHostName}"
          client.buffer.put(bytes, 0, count)
        }

        if(client.buffer.nonEmpty) {
          decodeNext(client) foreach (clientSide ! _)
        }
      }
    })
  }

}

object ClientSideProcessor {
  private val logger = LoggerFactory.getLogger(getClass)

  // create the client side actor
  private val system = ActorSystem("ClientSideActors")
  private val clientSide = system.actorOf(Props[ClientSideActor], name = "clientSide")
  private val callbacks = collection.mutable.Stack[Promise[VirtualWorld]]()

  def makePromise: Future[VirtualWorld] = {
    val promise = Promise[VirtualWorld]()
    callbacks.push(promise)
    promise.future
  }

  /**
   * Client Handler Actor
   * @author lawrence.daniels@gmail.com
   */
  class ClientSideActor() extends Actor with Transmitter {
    import com.ldaniels528.robowars.net.NetworkActionProcessor._

    def receive = {
      case r@WorldRequest(peer, level) => peer.out.write(encode(r))
      case WorldResponse(_, world) =>
        logger.info(s"Fulfilling promise: callbacks = $callbacks")
        val promise = callbacks.pop()
        promise.success(world)
      case WelcomeResponse(_, slots) =>
      case unknown => this.unhandled(unknown)
    }

  }

  case class Callback(promise: Promise[VirtualWorld])

}