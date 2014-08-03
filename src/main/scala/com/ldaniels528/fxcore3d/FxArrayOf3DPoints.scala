package com.ldaniels528.fxcore3d

/**
 * A class that encapsulates and array of 3D points.
 * @author lawrence.daniels@gmail.com
 */
trait FxArrayOf3DPoints {

  def apply(n: Int): (Double, Double, Double)

  def makeClone(): FxArrayOf3DPoints

  def x: Array[Double]

  def y: Array[Double]

  def z: Array[Double]

  def length: Int

}