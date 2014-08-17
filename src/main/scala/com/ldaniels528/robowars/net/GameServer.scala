package com.ldaniels528.robowars.net

import java.net.{ServerSocket, Socket}

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
  var alive: Boolean = true
  private val clientMgr = new ClientManager()
  private var world: VirtualWorld = _
  private var level: Int = 1

  def run() {
    // load the virtual world
    logger.info(s"Loading virtual world for level $level...")
    world = ContentManager.loadWorld(f"/worlds/world_$level%04d.xml")

    // start the thread manager
    logger.info("Starting client manager...")
    new Thread(clientMgr).start()

    // start listening on the port
    logger.info(s"Binding to port $port...")
    val serverSock = new ServerSocket(port)
    logger.info(s"Listening on port $port")

    // loop indefinitely
    while (alive) {
      // get the client connection
      val socket = serverSock.accept()
      logger.info(s"Client ${socket.getInetAddress.getHostName} connected")

      // track the client
      clientMgr += socket
    }
  }

  /**
   * Client Connection Manager
   * @author lawrence.daniels@gmail.com
   */
  class ClientManager() extends Runnable {
    private val logger = LoggerFactory.getLogger(getClass)

    // define the client container
    var clients: List[Client] = Nil

    def +=(socket: Socket) {
      // create a client wrapper
      val client = Client(socket)

      // track this client
      clients = client :: clients
    }

    override def run() {
      while (alive) {
        // check for input from each client
        clients foreach (client => manage(client))

        // sleep 1 millis
        Thread.sleep(1)
      }
    }

    private def manage(peer: Client) {
      Try {
        // fill the peer's data buffer
        peer.fillBuffer()

        // attempt to decode messages from the client
        if (peer.buffer.nonEmpty) {
          decodeNext(peer) foreach {
            case HelloRequest(client) => client.send(HelloResponse(client, availableSlots = 4))
            case r@HelloResponse(client, _) => client.send(r)
            case WorldRequest(client, _) => client.send(WorldResponse(client, world))
            case r@WorldResponse(client, _) => client.send(r)
            case unknown =>
              logger.error(s"Unhandled message $unknown (${unknown.getClass.getName}})")
          }
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
