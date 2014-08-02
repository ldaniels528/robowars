package com.ldaniels528.fxcore3d

import java.awt.Color

/**
 * Holds RGB and can return different colors for different intensities.
 * @author lawrence.daniels@gmail.com
 */
case class FxColor(r: Int, g: Int, b: Int) {
  var myBaseColor = new Color(r, g, b)

  /**
   * returns a "Color" for the intensity
   */
  def setIntensity(i: Double) {
    val ii = (i * 16).toInt
    val rr = Math.max(0, Math.min(r + ii, 255))
    val gg = Math.max(0, Math.min(g + ii, 255))
    val bb = Math.max(0, Math.min(b + ii, 255))
    myBaseColor = new Color(rr, gg, bb)
  }

  /**
   * returns the base color
   */
  def getColor(): Color = myBaseColor

  def makeClone(): FxColor = new FxColor(r, g, b)

  /**
   * make a string representation
   */
  override def toString = "#%02x%02x%02x".format(r, g, b)

}
