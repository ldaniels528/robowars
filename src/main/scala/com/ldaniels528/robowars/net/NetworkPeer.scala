package com.ldaniels528.robowars.net

import java.net.Socket

import com.ldaniels528.robowars.net.NetworkActionProcessor._
import com.ldaniels528.robowars.net.NetworkPeer._
import org.slf4j.LoggerFactory

/**
 * Represents a connection to a network peer
 * @param socket the given socket
 */
case class NetworkPeer(socket: Socket, capacity: Int) {
  private val logger = LoggerFactory.getLogger(getClass)

  // get a unique ID
  val id = generateUniqueID
  val hostName = socket.getInetAddress.getHostName

  // get the input and output streams
  private val in = socket.getInputStream
  private val out = socket.getOutputStream

  // create a network input buffer
  val buffer = new NetworkBuffer(capacity, autoReset = true)

  /**
   * Reads bytes from the input stream and populated the network buffer
   */
  def fillBuffer(): Unit = {
    val total = buffer.read(in)
    if (total > 0) {
      logger.info(s"Read $total bytes from client $hostName")
    }
  }

  /**
   * Transmits the given network action to a remote peer
   * @param action the given network action
   */
  def send(action: NetworkAction) {
    // capture the start time (in nanoseconds)
    val startTime = System.nanoTime()

    // encode the action
    val bytes = encode(action)

    // transmit the data to the remote peer
    out.write(bytes)
    out.flush()

    // capture the end time (in nanoseconds)
    val endTime = System.nanoTime()

    // report the information back to the operator
    val elapsed = (endTime - startTime).toDouble / 1e+6
    logger.info(f"Transmitted ${bytes.length} bytes to remote peer [$elapsed%.1f msec]...")
  }

}

/**
 * Network Peer Companion Object
 * @author lawrence.daniels@gmail.com
 */
object NetworkPeer {

  def generateUniqueID = System.nanoTime()

}
