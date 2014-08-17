package com.ldaniels528.robowars.net

import akka.actor.{Actor, ActorSystem, Cancellable, Props}
import com.ldaniels528.robowars.VirtualWorld
import com.ldaniels528.robowars.net.ClientSideProcessor._
import com.ldaniels528.robowars.net.NetworkActionProcessor._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
 * Client-Side Network Action Processor
 * @author lawrence.daniels@gmail.com
 */
trait ClientSideProcessor {

  /**
   * Retrieves a virtual world from a remote peer
   * @param client the given remote peer
   * @return the virtual world
   */
  def getRemoteWorld(client: NetworkPeer): Future[VirtualWorld] = {
    clientSide ! JoinRequest(client, level = 1)
    makePromise(client.id)
  }

  /**
   * Sets up a task to receive network actions
   */
  def scheduleUpdates(client: NetworkPeer, interval: FiniteDuration = 5.millis)(implicit ec: ExecutionContext): Cancellable = {
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

/**
 * Client-Side Network Action Processor Companion Object
 * @author lawrence.daniels@gmail.com
 */
object ClientSideProcessor {
  // create the client side actor
  private val system = ActorSystem("ClientSideActors")
  private val clientSide = system.actorOf(Props[ClientSideActor], name = "clientSideActor")
  private val callbacks = collection.concurrent.TrieMap[Long, Promise[VirtualWorld]]()

  /**
   * Returns a future representing a promise of a virtual world
   * @param id the given peer ID
   * @return a future representing a promise of a virtual world
   */
  def makePromise(id: Long): Future[VirtualWorld] = {
    val promise = Promise[VirtualWorld]()
    callbacks += (id -> promise)
    promise.future
  }

  /**
   * Client Handler Actor
   * @author lawrence.daniels@gmail.com
   */
  class ClientSideActor() extends Actor {
    def receive = {
      case r@JoinRequest(peer, level) => peer.send(r)
      case JoinResponse(peer, world) =>
        callbacks.remove(peer.id) foreach (_.success(world))
        ()
      case HelloResponse(_, slots) =>
      case unknown => this.unhandled(unknown)
    }
  }

}