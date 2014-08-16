package com.ldaniels528.robowars.net

import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * Created by ldaniels on 8/15/14.
 */
class NetworkBufferTest {
  val logger = LoggerFactory.getLogger(getClass)

  @Test
  def testReadingToTheLimit(): Unit = {
    val buf = new NetworkBuffer(24, autoReset = true)
    buf.putDouble(1.5d)
    buf.putDouble(2.5d)
    buf.putDouble(1.5d)
    logger.info(s"position = ${buf.position}, length = ${buf.length}")

    logger.info(s"x = ${buf.getDouble}, remaining = ${buf.remaining}")
    logger.info(s"y = ${buf.getDouble}, remaining = ${buf.remaining}")
    logger.info(s"z = ${buf.getDouble}, remaining = ${buf.remaining}")
    logger.info(s"position = ${buf.position}, length = ${buf.length}")
  }

  @Test
  def testReadingToEnd(): Unit = {
    val buf = new NetworkBuffer(24, autoReset = false)
    buf.putDouble(1.5d)
    buf.putDouble(2.5d)
    buf.putDouble(1.5d)
    logger.info(s"position = ${buf.position}, length = ${buf.length}")

    logger.info(s"x = ${buf.getDouble}, remaining = ${buf.remaining}")
    logger.info(s"y = ${buf.getDouble}, remaining = ${buf.remaining}")
    logger.info(s"z = ${buf.getDouble}, remaining = ${buf.remaining}")
    logger.info(s"position = ${buf.position}, length = ${buf.length}")
  }

}
