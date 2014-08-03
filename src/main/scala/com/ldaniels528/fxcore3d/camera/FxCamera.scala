package com.ldaniels528.fxcore3d.camera

import com.ldaniels528.fxcore3d._

/**
 * FxEngine Camera
 * @author lawrence.daniels@gmail.com
 */
trait FxCamera {

  def clipAndStoreZ(xs1: Int, ys1: Int, zs1: Double,
                    xs2: Int, ys2: Int, zs2: Double,
                    xStore: Array[Int],
                    yStore: Array[Int], p: Int)

  /**
   * Transforms and projects the points to the screen. It also stores the z
   * coordinate and clipping info in case the polygons need clipping.
   */
  def projectWithCheck(p3d: FxArrayOf3DPoints): FxProjectedPoints

  /**
   * Updates the camera
   */
  def update(dt: Double)

  /**
   * projects an array of 3d points to the temporary 2d buffer
   */
  def project(p3d: FxArrayOf3DPoints): FxProjectedPoints

  /**
   * @return the Z-clipping plane
   */
  def zClip: Double

}

/**
 * FxEngine Camera
 * @author lawrence.daniels@gmail.com
 */
object FxCamera {
  val CLIP_TOP = 1
  val CLIP_BOTTOM = 2
  val CLIP_RIGHT = 4
  val CLIP_LEFT = 8
  val CLIP_Z = 16

}
