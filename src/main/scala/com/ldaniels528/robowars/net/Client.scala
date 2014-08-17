package com.ldaniels528.robowars.net

import java.io.{BufferedInputStream, DataOutputStream}
import java.net.Socket

import com.ldaniels528.robowars.net.NetworkActionProcessor._
import org.slf4j.LoggerFactory

/**
 * Represents a client connection
 * @param socket the given socket
 */
case class Client(socket: Socket) {
  private val logger = LoggerFactory.getLogger(getClass)

  // get the input and output streams
  val in = new BufferedInputStream(socket.getInputStream, 1024)
  val out = new DataOutputStream(socket.getOutputStream)

  // create a network input buffer
  val buffer = new NetworkBuffer(16384, autoReset = true)

  // create a scratch buffer for reading data
  private val scratch = new Array[Byte](8192)

  def fillBuffer(): Unit = {
    if (in.available() > 0) {
      // write the chunk of data to the client's buffer
      val count = in.read(scratch)
      logger.info(s"Read $count bytes from client ${socket.getInetAddress.getHostName}")
      buffer.put(scratch, 0, count)
    }
  }

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
