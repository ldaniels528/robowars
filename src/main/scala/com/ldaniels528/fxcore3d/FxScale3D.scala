package com.ldaniels528.fxcore3d

/**
 * Represents a 3D dimensions or scale (width, height, and depth)
 * @author lawrence.daniels@gmail.com
 */
case class FxScale3D(w: Double = 0, h: Double = 0, d: Double = 0)   {

  def toPoint: FxPoint3D = new FxPoint3D(w, h, d)

}
