package com.ldaniels528.fxcore3d

/**
 * Represents a Sequence of 3D Points
 * @author lawrence.daniels@gmail.com
 */
case class Fx3DPointSeq(x: Array[Double], y: Array[Double], z: Array[Double]) extends FxArrayOf3DPoints {

  def length: Int = x.length

  def apply(n: Int) = (x(n), y(n), z(n))

  /**
   * Returns a clone.
   */
  def makeClone(): Fx3DPointSeq = this.copy()

  override def toString = {
    ((0 to (length - 1)) map (n => "(%.1f, %.1f, %.1f)".format(x(n), y(n), z(n)))).mkString(",")
  }

}

/**
 * Fx3DPointSeq Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Fx3DPointSeq {

  /**
   * Constructs an empty array of 3D points having the given size
   */
  def apply(length: Int): Fx3DPointSeq = {
    val x = new Array[Double](length)
    val y = new Array[Double](length)
    val z = new Array[Double](length)
    new Fx3DPointSeq(x, y, z)
  }

}