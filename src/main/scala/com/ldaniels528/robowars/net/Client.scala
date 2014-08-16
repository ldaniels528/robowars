package com.ldaniels528.robowars.net

import java.io.{BufferedInputStream, DataOutputStream}
import java.net.Socket

/**
 * Represents a client connection
 * @param socket the given socket
 */
case class Client(socket: Socket) {
  // get the input and output streams
  val in = new BufferedInputStream(socket.getInputStream, 1024)
  val out = new DataOutputStream(socket.getOutputStream)

  // create a buffer
  val buffer = new NetworkBuffer(16384, autoReset = true)

}
