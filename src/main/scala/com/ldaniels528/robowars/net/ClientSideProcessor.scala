package com.ldaniels528.robowars.net

import akka.actor.{Actor, ActorSystem, Cancellable, Props}
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

  def client: NetworkPeer

  def getRemoteWorld(client: NetworkPeer): Future[VirtualWorld] = {
    clientSide ! WorldRequest(client, level = 1)
    makePromise
  }

  /**
   * Sets up a task to receive network actions
   */
  def scheduleUpdates(interval: FiniteDuration = 5.millis)(implicit ec: ExecutionContext): Cancellable = {
    system.scheduler.schedule(initialDelay = 0.millis, interval, new Runnable {
      override def run() {
        // fill the peer's data buffer
        client.fillBuffer()

        // decode and process the next action
        decodeNext(client) foreach (clientSide ! _)
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
  class ClientSideActor() extends Actor {

    import com.ldaniels528.robowars.net.NetworkActionProcessor._

    def receive = {
      case r@WorldRequest(peer, level) => peer.send(r)
      case WorldResponse(_, world) =>
        logger.info(s"Fulfilling promise: callbacks = $callbacks")
        val promise = callbacks.pop()
        promise.success(world)
        ()
      case HelloResponse(_, slots) =>
      case unknown => this.unhandled(unknown)
    }

  }

  case class Callback(promise: Promise[VirtualWorld])

}