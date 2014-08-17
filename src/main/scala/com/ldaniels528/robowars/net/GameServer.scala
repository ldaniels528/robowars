package com.ldaniels528.robowars.net

import java.net.ServerSocket

import com.ldaniels528.robowars.net.NetworkActionProcessor._
import com.ldaniels528.robowars.{ContentManager, VirtualWorld}
import org.slf4j.LoggerFactory

import scala.util.Try

/**
 * RoboWars Game Server
 * @author lawrence.daniels@gmail.com
 */
class GameServer(port: Int) {
  private val logger = LoggerFactory.getLogger(getClass)
  private var world: VirtualWorld = _
  private var level: Int = 1
  private var alive: Boolean = _

  // define the client container
  private var clients: List[NetworkPeer] = Nil

  /**
   * Kills the process
   */
  def die(): Unit = alive = false

  /**
   * Server main loop
   */
  def run() {
    // load the virtual world
    logger.info(s"Loading virtual world for level $level...")
    world = ContentManager.loadWorld(f"/worlds/world_$level%04d.xml")

    // the process is now live
    alive = true

    // continually update the server
    new Thread {
      override def run() = {
        val times = Array[Double](25)
        var ticker = 0
        var dt: Double = 0.3

        while (alive) {
          // capture the start time
          val startTime = System.nanoTime()

          // update all clients
          clients foreach manage

          // update the world
          world.update(dt)

          // compute the frame rate
          val endTime = System.nanoTime()
          dt = (endTime - startTime) / 1e6
          times(ticker % times.length) = dt
          ticker += 1

          if(ticker % 10000 == 0) {
            logger.info(f"Frames: max = ${times.max}%.01f, min = ${times.min}%.01f")

          }
        }
      }
    }.start()

    // start listening on the port
    val serverSock = new ServerSocket(port)
    logger.info(s"Listening on port $port")

    // loop indefinitely
    while (alive) {
      // get the client connection
      val client = NetworkPeer(socket = serverSock.accept(), capacity = 512)
      logger.info(s"Client ${client.hostName} connected")

      // track the client
      clients = client :: clients
    }
  }

  private def manage(peer: NetworkPeer) {
    Try {
      // fill the peer's data buffer
      peer.fillBuffer()

      // attempt to decode messages from the client
      if (peer.buffer.nonEmpty) {
        decodeNext(peer) foreach {
          case HelloRequest(client) => client.send(HelloResponse(client, availableSlots = 4))
          case r@HelloResponse(client, _) => client.send(r)
          case JoinRequest(client, _) => client.send(JoinResponse(client, world))
          case r@JoinResponse(client, _) => client.send(r)
          case unknown =>
            logger.error(s"Unhandled message $unknown (${unknown.getClass.getName}})")
        }
      }
    }
  }

}

/**
 * RoboWars Game Server
 * @author lawrence.daniels@gmail.com
 */
object GameServer {

  /**
   * Main application entry-point
   * @param args the given command line arguments
   */
  def main(args: Array[String]) {
    // get the listen port
    val port = args.headOption map (_.toInt) getOrElse 8855

    // start the server
    val server = new GameServer(port)
    server.run()
  }

}
