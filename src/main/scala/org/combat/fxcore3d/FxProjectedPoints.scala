package org.combat.fxcore3d

/**
 * FxEngine Array of Projected Points
 * @author lawrence.daniels@gmail.com
 */
class FxProjectedPoints(val x: Array[Int], val y: Array[Int], val z: Array[Double], var npoints: Int)
  extends FxArrayOf2DPoints {
  val clipFlags = new Array[Int](npoints)
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