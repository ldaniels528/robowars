package com.ldaniels528.fxcore3d

/**
 * Represents a 3D dimension size (width, height, and depth)
 * @author lawrence.daniels@gmail.com
 */
case class FxSize3D(w: Int = 0, h: Int = 0, d: Int = 0)   {

  def toPoint: FxPoint3D = new FxPoint3D(w, h, d)

}
