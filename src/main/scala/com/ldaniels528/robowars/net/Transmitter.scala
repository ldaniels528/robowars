package com.ldaniels528.robowars.net

import com.ldaniels528.robowars.net.NetworkActionProcessor._
import org.slf4j.LoggerFactory

/**
 * This trait is implement primary by actors, whom are required to transmit
 * network action messages across the wire from peer-to-peer.
 */
trait Transmitter {

  protected def send(peer: Client, action: NetworkAction) {
    val bytes = encode(action)
    LoggerFactory.getLogger(getClass).info(s"Transmitting ${bytes.length} bytes to remote peer...")
    peer.out.write(bytes)
    peer.out.flush()
  }

}
