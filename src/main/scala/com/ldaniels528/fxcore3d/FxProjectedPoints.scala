package com.ldaniels528.fxcore3d

/**
 * FxEngine Array Of 2D Points
 * @author lawrence.daniels@gmail.com
 */
case class FxProjectedPoints(capacity: Int) {
  private val myPoints: Seq[FxProjectedPoint] = (0 to capacity - 1) map (n => FxProjectedPoint(index = n))
  var length = capacity
  var clipOrOp: Int = _
  var clipAndOp: Int = _

  /**
   * Returns the projected point at the given index
   * @param index the given index
   * @return the projected point
   */
  def apply(index: Int): FxProjectedPoint = myPoints(index)

  /**
   * Returns the current slice of the scratch buffer based on the length attribute
   * @return
   */
  def points: Seq[FxProjectedPoint] = myPoints.slice(0, length)

}

/**
 * Represents a projected point
 * @param x the given X-axis of the point (2D Plane)
 * @param y the given Y-axis of the point (2D Plane)
 * @param z the given Z-axis of the point (3D Plane)
 * @param clipFlags the clipping plane flags
 * @param index the index of the projected point
 */
case class FxProjectedPoint(var x: Int = 0, var y: Int = 0, var z: Double = 0, var clipFlags: Int = 0, index: Int)
