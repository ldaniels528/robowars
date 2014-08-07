package com.ldaniels528.fxcore3d.camera

import java.awt.Color

/**
 * Day-Night Sky Cycle
 * @author lawrence.daniels@gmail.com
 */
object FxDayNightSky {

  /**
   * Returns the color of the sky
   * @param time the world time (in seconds)
   * @return the [[Color]]
   */
  def getColor(time: Double): Color = {
    // a day is 512 seconds
    val index = (time % 512).toInt

    // generate the amount of blue (0 to 255)
    val blue = if (index < 256) index else 511 - index

    // return the sky color
    new Color(0, 0, blue)
  }

}
