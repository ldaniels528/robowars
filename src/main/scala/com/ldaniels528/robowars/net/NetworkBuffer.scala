package com.ldaniels528.robowars.net

import java.nio.ByteBuffer

import com.ldaniels528.fxcore3d.FxPoint3D
import org.slf4j.LoggerFactory

/**
 * Network Buffer
 * @author lawrence.daniels@gmail.com
 */
class NetworkBuffer(capacity: Int, var autoReset: Boolean = true) {
  private lazy val logger = LoggerFactory.getLogger(getClass)
  private val bytes = new Array[Byte](capacity)
  private val buf = ByteBuffer.wrap(bytes)

  var position: Int = 0
  var length: Int = 0

  def isEmpty: Boolean = remaining == 0

  def nonEmpty: Boolean = remaining != 0

  def remaining: Int = length - position

  def reset(): Unit = {
    position = 0
    length = 0
  }

  def rewind(): Unit = position = 0

  def get: Byte = {
    val v = bytes(position)
    position += 1
    limitCheck()
    v
  }

  def getDouble: Double = {
    val p = position
    position += 8
    limitCheck()
    buf.getDouble(p)
  }

  def getFloat: Float = {
    val p = position
    position += 4
    limitCheck()
    buf.getFloat(p)
  }

  def getInt: Int = {
    val p = position
    position += 4
    limitCheck()
    buf.getInt(p)
  }

  def getLong: Long = {
    val p = position
    position += 8
    limitCheck()
    buf.getLong(p)
  }

  def getShort: Short = {
    val p = position
    position += 2
    limitCheck()
    buf.getShort(p)
  }

  def getPoint: FxPoint3D = {
    val p = position
    position += 24
    limitCheck()
    val x = buf.getDouble(p)
    val y = buf.getDouble(p + 8)
    val z = buf.getDouble(p + 16)
    FxPoint3D(x, y, z)
  }

  def getString(len: Int): String = {
    logger.info(s"Retrieving string [$len bytes]")
    val data = new Array[Byte](len)
    buf.position(position)
    buf.get(data, 0, data.length)
    position += len
    limitCheck()
    new String(data)
  }

  def peek: Byte = bytes(position)

  def peekDouble: Double = buf.getDouble(position)

  def peekFloat: Float = buf.getFloat(position)

  def peekInt: Int = buf.getInt(position)

  def peekLong: Long = buf.getLong(position)

  def peekShort: Short = buf.getShort(position)

  def +=(v: Byte) = put(v)

  def +=(values: Array[Byte]): Unit = put(values)

  def +=(values: Seq[AnyVal]): Unit = values foreach {
    case v: Byte => put(v)
    case v: Double => putDouble(v)
    case v: Float => putFloat(v)
    case v: Int => putInt(v)
    case v: Long => putLong(v)
    case v: Short => putShort(v)
    case v =>
      throw new IllegalArgumentException(s"Value '$v' (${v.getClass.getName}) is not supported")
  }

  def put(v: Byte) {
    bytes(length) = v
    length += 1
  }

  def put(src: Array[Byte]) {
    System.arraycopy(src, 0, bytes, length, src.length)
    length += src.length
  }

  def put(src: Array[Byte], start: Int, count: Int) {
    System.arraycopy(src, start, bytes, length, count)
    length += count
  }

  def putDouble(v: Double) {
    buf.putDouble(length, v)
    length += 8
  }

  def putFloat(v: Float) {
    buf.putFloat(length, v)
    length += 4
  }

  def putInt(v: Int) {
    buf.putInt(length, v)
    length += 4
  }

  def putLong(v: Long) {
    buf.putLong(length, v)
    length += 8
  }

  def putShort(v: Short) {
    buf.putShort(length, v)
    length += 2
  }

  def toArray: Array[Byte] = {
    val dest = new Array[Byte](length)
    System.arraycopy(bytes, 0, dest, 0, length)
    dest
  }

  private def limitCheck() {
    if (position >= length && autoReset) reset()
  }

}
