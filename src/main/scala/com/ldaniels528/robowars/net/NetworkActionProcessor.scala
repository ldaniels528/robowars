package com.ldaniels528.robowars.net

import com.ldaniels528.robowars.{VirtualWorld, VirtualWorldReader, VirtualWorldWriter}
import org.slf4j.LoggerFactory

/**
 * Network Action Processor
 * @author lawrence.daniels@gmail.com
 */
object NetworkActionProcessor {
  private val logger = LoggerFactory.getLogger(getClass)

  /**
   * Decodes the next message from the underlying buffer
   * @param client the client whose buffer is being monitored
   * @return an option of a network action
   */
  def decodeNext(client: Client): Option[NetworkAction] = {
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
        case OP_WORLD_REQ => Some(WorldRequest(client, level = buf.getShort))
        case OP_WORLD_RESP => Some(WorldResponse(client, world = decodeWorld(buf)))
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
      case WorldRequest(_, level) => allocate(3).put(OP_WORLD_REQ).putShort(level.toShort).array()
      case WorldResponse(_, world) =>
        val worldBytes = VirtualWorldWriter.save(world).toString().getBytes
        allocate(worldBytes.length + 5).put(OP_WORLD_RESP).putInt(worldBytes.length).put(worldBytes).array()
    }
  }

  private def decodeWorld(buf: NetworkBuffer): VirtualWorld = {
    val length = buf.getInt
    VirtualWorldReader.decode(buf.getString(length))
  }

  // Operation Code definitions

  val OP_HELLO_REQ = 0x10: Byte
  val OP_HELLO_RESP = 0x11: Byte
  val OP_WORLD_REQ = 0x22: Byte
  val OP_WORLD_RESP = 0x23: Byte

  val OP_CODES = Map(
    OP_HELLO_REQ -> "HELLO_REQ",
    OP_HELLO_RESP -> "HELLO_RESP",
    OP_WORLD_REQ -> "JOIN_REQ",
    OP_WORLD_RESP -> "JOIN_RESP")

  // Network Action definitions

  trait NetworkAction

  case class HelloRequest(client: Client) extends NetworkAction

  case class HelloResponse(client: Client, availableSlots: Int) extends NetworkAction


  case class WorldRequest(client: Client, level: Int) extends NetworkAction

  case class WorldResponse(client: Client, world: VirtualWorld) extends NetworkAction

}