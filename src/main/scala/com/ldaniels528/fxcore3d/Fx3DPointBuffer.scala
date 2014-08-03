package com.ldaniels528.fxcore3d

/**
 * A class that encapsulates and array of 3D points.
 * @author lawrence.daniels@gmail.com
 */
case class Fx3DPointBuffer(x: Array[Double], y: Array[Double], z: Array[Double]) extends FxArrayOf3DPoints {

  var length: Int = x.length

  def apply(n: Int) = (x(n), y(n), z(n))

  /**
   * Returns a clone.
   */
  def makeClone(): Fx3DPointBuffer = this.copy()

  override def toString = {
    ((0 to (length - 1)) map (n => "(%.1f, %.1f, %.1f)".format(x(n), y(n), z(n)))).mkString(",")
  }

}

/**
 * FxArrayOf3DPoints Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Fx3DPointBuffer {

  /**
   * Constructs an empty array of 3D points having the given size
   */
  def apply(length: Int): Fx3DPointBuffer = {
    val x = new Array[Double](length)
    val y = new Array[Double](length)
    val z = new Array[Double](length)
    new Fx3DPointBuffer(x, y, z)
  }

}