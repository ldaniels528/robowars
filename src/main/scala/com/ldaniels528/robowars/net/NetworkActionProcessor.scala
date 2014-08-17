package com.ldaniels528.robowars.net

import java.nio.ByteBuffer._

import com.ldaniels528.robowars.util.Compression
import com.ldaniels528.robowars.{VirtualWorld, VirtualWorldReader, VirtualWorldWriter}
import org.slf4j.LoggerFactory

/**
 * Network Action Processor
 * @author lawrence.daniels@gmail.com
 */
object NetworkActionProcessor extends Compression {
  private val logger = LoggerFactory.getLogger(getClass)

  /**
   * Decodes the next message from the underlying buffer
   * @param client the client whose buffer is being monitored
   * @return an option of a network action
   */
  def decodeNext(client: NetworkPeer): Option[NetworkAction] = {
    val buf = client.buffer
    val remaining = buf.remaining
    if (remaining == 0) None
    else {
      val position = buf.position
      val code = buf.get
      logger.info(f"Received ${OP_CODES.getOrElse(code, "Unknown Code")} ($code%02x) at position $position [$remaining remaining]")
      code match {
        case OP_HELLO_REQ => Some(HelloRequest(client))
        case OP_HELLO_RESP => Some(HelloResponse(client, availableSlots = buf.getShort))
        case OP_JOIN_REQ => Some(JoinRequest(client, level = buf.getShort))
        case OP_JOIN_RESP => Some(JoinResponse(client, world = decodeWorld(buf)))
        case unknown =>
          logger.info(f"Unhandled network code $unknown%02x")
          None
      }
    }
  }

  /**
   * Encodes a network action into a byte array
   * @param action the given network action
   * @return a byte array
   */
  def encode(action: NetworkAction): Array[Byte] = {
    import java.nio.ByteBuffer.allocate

    action match {
      case HelloRequest(_) => Array(OP_HELLO_REQ)
      case HelloResponse(_, slots) => Array(OP_HELLO_RESP)
      case JoinRequest(_, level) => allocate(3).put(OP_JOIN_REQ).putShort(level.toShort).array()
      case JoinResponse(_, world) => encodeWorld(world)
    }
  }

  private def decodeWorld(buf: NetworkBuffer): VirtualWorld = {
    val length = buf.getInt
    val xml = new String(decompress(buf.getArray(length)))
    VirtualWorldReader.decode(xml)
  }

  private def encodeWorld(world: VirtualWorld): Array[Byte] = {
    val compressed = compress(VirtualWorldWriter.save(world).toString().getBytes)
    allocate(compressed.length + 5).put(OP_JOIN_RESP).putInt(compressed.length).put(compressed).array()
  }

  /**
   * Operation Code definitions
   */

  val OP_HELLO_REQ = 0x10: Byte
  val OP_HELLO_RESP = 0x11: Byte
  val OP_JOIN_REQ = 0x22: Byte
  val OP_JOIN_RESP = 0x23: Byte

  val OP_CODES = Map(
    OP_HELLO_REQ -> "HELLO_REQ",
    OP_HELLO_RESP -> "HELLO_RESP",
    OP_JOIN_REQ -> "JOIN_REQ",
    OP_JOIN_RESP -> "JOIN_RESP")

  /**
   * Network Action definitions
   */

  trait NetworkAction

  case class HelloRequest(client: NetworkPeer) extends NetworkAction

  case class HelloResponse(client: NetworkPeer, availableSlots: Int) extends NetworkAction

  case class JoinRequest(client: NetworkPeer, level: Int) extends NetworkAction

  case class JoinResponse(client: NetworkPeer, world: VirtualWorld) extends NetworkAction

}