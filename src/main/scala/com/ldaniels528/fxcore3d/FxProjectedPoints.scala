package com.ldaniels528.fxcore3d

/**
 * FxEngine Array Of 2D Points
 * @author lawrence.daniels@gmail.com
 */
case class FxProjectedPoints(var length: Int) {
  val myPoints = (0 to length - 1) map (n => FxProjectedPoint(index = n))
  var clipOrOp: Int = _
  var clipAndOp: Int = _

  def points = myPoints.slice(0, length)

  def apply(n: Int): FxProjectedPoint = myPoints(n)

}

case class FxProjectedPoint(var x: Int = 0, var y: Int = 0, var z: Double = 0, var clipFlags: Int = 0, index: Int)
