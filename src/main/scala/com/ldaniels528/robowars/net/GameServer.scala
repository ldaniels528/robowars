package com.ldaniels528.robowars.net

import java.net.{ServerSocket, Socket}

import akka.actor.{Actor, ActorSystem, Props}
import com.ldaniels528.robowars.VirtualWorldReader
import com.ldaniels528.robowars.net.NetworkActionProcessor.{ShutdownServer, WelcomeRequest, WorldResponse, _}
import org.slf4j.LoggerFactory

import scala.util.Try

/**
 * RoboWars Game Server
 * @author lawrence.daniels@gmail.com
 */
object GameServer {
  private val logger = LoggerFactory.getLogger(getClass)
  var alive: Boolean = true
  private val system = ActorSystem("RemoteClients")
  private val clientMgr = new ClientManager()
  private var level = 1

  // create the client side actor
  private val serverSide = system.actorOf(Props[ServerSideActor], name = "serverSide")

  // create the logging actor
  private val log = system.actorOf(Props[LoggingActor], name = "logger")

  // start the thread manager
  new Thread(clientMgr).start()

  /**
   * Main application entry-point
   * @param args the given command line arguments
   */
  def main(args: Array[String]) {
    // get the listen port
    val port = args.headOption map (_.toInt) getOrElse 8855

    // start listening on the port
    logger.info(s"Binding to port $port...")
    val serverSock = new ServerSocket(port)
    logger.info(s"Listening on port $port")

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
    // define the client container
    var clients: List[Client] = Nil
    val bytes = new Array[Byte](65536)

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

        // sleep 5 millis
        Thread.sleep(5)
      }
    }

    private def manage(client: Client) {
      Try {
        if (client.in.available() > 0) {
          // write the chunk of data to the client's buffer
          val count = client.in.read(bytes)
          log ! s"Read $count bytes for client ${client.socket.getInetAddress.getHostName}"
          client.buffer.put(bytes, 0, count)
        }

        // attempt to decode messages from the client
        if(client.buffer.nonEmpty) {
          decodeNext(client) foreach (serverSide ! _)
        }
      }
    }
  }

  /**
   * Asynchronous logging actor
   * @author lawrence.daniels@gmail.com
   */
  class LoggingActor() extends Actor {
    def receive = {
      case message: String => logger.info(message)
      case unknown => this.unhandled(unknown)
    }
  }

  /**
   * Server-Side Client Handler Actor
   * @author lawrence.daniels@gmail.com
   */
  class ServerSideActor() extends Actor with Transmitter {
    def receive = {
      case ShutdownServer(client) =>
      case WelcomeRequest(client) => send(client, WelcomeResponse(client, availableSlots = 4))
      case r @ WelcomeResponse(client, _) => send(client, r)
      case WorldRequest(client, _) => send(client, WorldResponse(client, VirtualWorldReader.load(f"/worlds/world_$level%04d.xml")))
      case r @ WorldResponse(client, _) => send(client, r)
      case unknown => this.unhandled(unknown)
    }

  }

}
