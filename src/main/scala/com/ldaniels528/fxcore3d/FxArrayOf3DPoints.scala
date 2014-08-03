package com.ldaniels528.fxcore3d

/**
 * A class that encapsulates and array of 3d points.
 * @author lawrence.daniels@gmail.com
 */
case class FxArrayOf3DPoints(x: Array[Double], y: Array[Double], z: Array[Double], var npoints: Int) {

  def apply(n:Int) = FxPoint3D(x(n), y(n), z(n))

  /**
   * Returns a clone.
   */
  def makeClone(): FxArrayOf3DPoints = this.copy()

  override def toString = {
    ((0 to (npoints - 1)) map (n => "(%.1f, %.1f, %.1f)".format(x(n), y(n), z(n)))).mkString(",")
  }
}

/**
 * FxArrayOf3DPoints Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxArrayOf3DPoints {

  /**
   * Constructs an empty array of 3D points having the given size
   */
  def apply(size: Int): FxArrayOf3DPoints = {
    val x = new Array[Double](size)
    val y = new Array[Double](size)
    val z = new Array[Double](size)
    new FxArrayOf3DPoints(x, y, z, size)
  }

}