package com.ldaniels528.fxcore3d.polygon

import java.awt.{Graphics2D, Polygon}

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.polygon.FxIndexingPolygon._
import com.ldaniels528.fxcore3d.{FxArrayOf3DPoints, FxPoint3D, FxProjectedPoints}

/**
 * Describes an abstract indexing polygon.
 * @author lawrence.daniels@gmail.com
 */
abstract class FxIndexingPolygon(myIndices: Seq[Int]) {

  def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, cam: FxCamera) {
    paint(g, p)
  }

  def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera, intensity: Double) {
    paint(g, p)
  }

  /**
   * pokes out the 2d coordinates and stores them into the scratch polygon.
   */
  protected def copyIndexedPoints(p: FxProjectedPoints) {
    var n = 0
    myIndices foreach { index =>
      ourScratchPoly.xpoints(n) = p(index).x
      ourScratchPoly.ypoints(n) = p(index).y
      n += 1
    }
    ourScratchPoly.npoints = myIndices.length
  }

  /**
   * returns the normal of this polygon.
   */
  def getNormal(a: FxArrayOf3DPoints): FxPoint3D = {
    val i0 = myIndices(0)
    val i1 = myIndices(1)
    val i2 = myIndices(myIndices.length - 1)

    val p0 = a.point(i0)
    val p1 = a.point(i1)
    val p2 = a.point(i2)

    val v1 = p0.vectorTo(p1)
    val v2 = p0.vectorTo(p2)

    val norm = new FxPoint3D()
    norm.vectorProduct(v1, v2)
    norm.normalize(1)
    norm
  }

  def makeClone: FxIndexingPolygon

  /**
   * paints a polygon. the 2d list of coordinates must be supplied
   */
  def paint(g: Graphics2D, p: FxProjectedPoints)

  def paintWithShading(g: Graphics2D, p: FxProjectedPoints, intensity: Double) {
    paint(g, p)
  }

}

/**
 * Describes an abstract indexing polygon.
 * @author lawrence.daniels@gmail.com
 */
object FxIndexingPolygon {

  /**
   * the "scratch" polygon that is used for painting
   */
  val ourScratchPoly = new Polygon(new Array[Int](50), new Array[Int](50), 50)

  /**
   * determine the orientation of the scratch polygon. if the result is
   * positive then it is CW.
   */
  def orientation(): Int = {
    val p1x = ourScratchPoly.xpoints(1)
    val p1y = ourScratchPoly.ypoints(1)

    // vector from vertex #1 to vertex #2
    val v1x = ourScratchPoly.xpoints(2) - p1x
    val v1y = ourScratchPoly.ypoints(2) - p1y

    // vector from vertex #1 to vertex #0
    val v2x = ourScratchPoly.xpoints(0) - p1x
    val v2y = ourScratchPoly.ypoints(0) - p1y

    // return the determinant of the vectors
    v1x * v2y - v2x * v1y
  }

}