package com.ldaniels528.fxcore3d

/**
 * FxEngine Array Of 2D Points
 * @author lawrence.daniels@gmail.com
 */
case class FxProjectedPoints(val x: Array[Int], val y: Array[Int], val z: Array[Double], var length: Int) {
  val clipFlags = new Array[Int](length)
  var clipOrOp: Int = _
  var clipAndOp: Int = _

}

/**
 * FxEngine Array of Projected Points
 * @author lawrence.daniels@gmail.com
 */
object FxProjectedPoints {

  def apply(pts: Int): FxProjectedPoints = {
    new FxProjectedPoints(new Array[Int](pts), new Array[Int](pts), new Array[Double](pts), pts)
  }

}