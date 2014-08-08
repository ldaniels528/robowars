package com.ldaniels528.fxcore3d

/**
 * A class that encapsulates and array of 3D points.
 * @author lawrence.daniels@gmail.com
 */
case class FxArrayOf3DPoints(capacity: Int) {
  var x = new Array[Double](capacity)
  var y = new Array[Double](capacity)
  var z = new Array[Double](capacity)
  var length: Int = capacity

  def apply(n: Int): FxProjectedPoint3D = FxProjectedPoint3D(x(n), y(n), z(n), n)

  /**
   * Creates a cloned copy of the instance
   */
  def makeClone: FxArrayOf3DPoints = this.copy()

  override def toString = {
    (0 to (length - 1)) map (n => "(%.1f, %.1f, %.1f)".format(x(n), y(n), z(n))) mkString ","
  }

}

/**
 * Represents a 3D Projected Point
 * @param x the X-axis of the point
 * @param y the Y-axis of the point
 * @param z the Z-axis of the point
 * @param index the index of the point in the sequence
 */
case class FxProjectedPoint3D(var x: Double = 0, var y: Double = 0, var z: Double = 0, index: Int) {

  /**
   * Creates a cloned copy of the instance
   */
  def makeClone: FxProjectedPoint3D = this.copy()

  override def toString = "(%.1f, %.1f, %.1f)".format(x, y, z)

}

/**
 * FxArrayOf3DPoints Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxArrayOf3DPoints {

  def apply(x: Array[Double], y: Array[Double], z: Array[Double]): FxArrayOf3DPoints = {
    val pp = new FxArrayOf3DPoints(x.length)
    System.arraycopy(x, 0, pp.x, 0, x.length)
    System.arraycopy(y, 0, pp.y, 0, y.length)
    System.arraycopy(z, 0, pp.z, 0, z.length)
    pp
  }

}