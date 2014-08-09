package com.ldaniels528.fxcore3d

/**
 * Represents a 3D dimensions or scale (width, height, and depth)
 * @author lawrence.daniels@gmail.com
 */
case class FxScale3D(w: Double = 0, h: Double = 0, d: Double = 0)   {

  def magnitude(): Double = Math.sqrt(w * w + h * h + d * d)

  def toPoint: FxPoint3D = new FxPoint3D(w, h, d)

  def reducedHeight(scaleH:Double) : FxScale3D = copy(h = this.h * scaleH)

}
