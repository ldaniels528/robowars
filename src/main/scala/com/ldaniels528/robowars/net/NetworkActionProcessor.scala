package com.ldaniels528.robowars.net

import java.io.ByteArrayOutputStream

import com.ldaniels528.robowars.util.ResourceUtil._
import com.ldaniels528.robowars.{ContentManager, VirtualWorld, VirtualWorldReader, VirtualWorldWriter}
import org.apache.commons.io.IOUtils
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
        case OP_SHUTDOWN_REQ => Some(ShutdownServer(client))
        case OP_WELCOME_REQ => Some(WelcomeRequest(client))
        case OP_WELCOME_RESP => Some(WelcomeResponse(client, availableSlots = buf.getShort))
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
      case ShutdownServer(_) => Array(OP_SHUTDOWN_REQ)
      case WelcomeRequest(_) => Array(OP_WELCOME_REQ)
      case WelcomeResponse(_, slots) => Array(OP_WELCOME_RESP)
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

  private def encodeWorld(level: Int): Array[Byte] = {
    val out = new ByteArrayOutputStream(8192)
    ContentManager.getResource(f"/worlds/world_$level%04d.xml") use (IOUtils.copy(_, out))
    out.toByteArray
  }

  // Operation Code definitions

  val OP_SHUTDOWN_REQ = 0x0F: Byte
  val OP_WELCOME_REQ = 0x10: Byte
  val OP_WELCOME_RESP = 0x11: Byte
  val OP_WORLD_REQ = 0x22: Byte
  val OP_WORLD_RESP = 0x23: Byte

  val OP_CODES = Map(
    OP_SHUTDOWN_REQ -> "SHDN_REQ",
    OP_WELCOME_REQ -> "WELCM_REQ",
    OP_WELCOME_RESP -> "WELCM_RESP",
    OP_WORLD_REQ -> "JOIN_REQ",
    OP_WORLD_RESP -> "JOIN_RESP")

  // Network Action definitions

  trait NetworkAction

  case class ShutdownServer(client: Client) extends NetworkAction

  case class WorldRequest(client: Client, level: Int) extends NetworkAction

  case class WorldResponse(client: Client, world: VirtualWorld) extends NetworkAction

  case class WelcomeRequest(client: Client) extends NetworkAction

  case class WelcomeResponse(client: Client, availableSlots: Int) extends NetworkAction

}