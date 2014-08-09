package com.ldaniels528.fxcore3d.camera

import java.awt.Color

/**
 * Day-Night Cycle
 * @author lawrence.daniels@gmail.com
 */
object FxDayNightCycle {
  val DAYLIGHT_ADJUSTMENT = 384

  /**
   * Returns the color of the sky
   * @param time the world time (in seconds)
   * @return the [[Color]]
   */
  def skyColor(time: Double): Color = {
    // adjust the time so that the game starts during the day
    val adjTime = time + DAYLIGHT_ADJUSTMENT

    // a day is 512 seconds
    val index = (adjTime % 512).toInt

    // generate the amount of blue (0 to 255)
    val blue = if (index < 256) index else 511 - index

    // return the sky color
    new Color(0, 0, blue)
  }

  def groundColor(time: Double) : Color = {
    // adjust the time so that the game starts during the day
    val adjTime = time + DAYLIGHT_ADJUSTMENT

    // a day is 512 seconds
    val index = (adjTime % 512).toInt

    val intensity  = if (index < 256) index else 511 - index

    new Color(intensity/4, intensity/2, intensity/4)
  }

}
