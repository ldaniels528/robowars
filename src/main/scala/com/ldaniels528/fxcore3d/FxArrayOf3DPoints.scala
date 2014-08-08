package com.ldaniels528.fxcore3d

/**
 * A class that encapsulates and array of 3D points.
 * @author lawrence.daniels@gmail.com
 */
case class FxArrayOf3DPoints(capacity: Int) {
  val myPoints: Seq[FxProjectedPoint3D] = (0 to (capacity - 1)) map (n => FxProjectedPoint3D(index = n))
  var length: Int = capacity

  def apply(n: Int): FxProjectedPoint3D = myPoints(n)

  /**
   * Creates a cloned copy of the instance
   */
  def makeClone: FxArrayOf3DPoints = this.copy()

  def point(n: Int): FxPoint3D = {
    val pp = myPoints(n)
    FxPoint3D(pp.x, pp.y, pp.z)
  }

  def points: Seq[FxProjectedPoint3D] = if (capacity == length) myPoints else myPoints.slice(0, length)

  override def toString = points mkString ","

}

/**
 * Represents a 3D Projected Point
 * @param x the X-axis of the point
 * @param y the Y-axis of the point
 * @param z the Z-axis of the point
 * @param index the index of the point in the sequence
 */
case class FxProjectedPoint3D(var x: Double = 0, var y: Double = 0, var z: Double = 0, index: Int) {

  def dotProduct(p: FxPoint3D): Double = p.x * x + p.y * y + p.z * z

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
    val app = new FxArrayOf3DPoints(x.length)
    var n = 0
    app.points foreach { pp =>
      pp.x = x(n)
      pp.y = y(n)
      pp.z = z(n)
      n += 1
    }
    app
  }

}