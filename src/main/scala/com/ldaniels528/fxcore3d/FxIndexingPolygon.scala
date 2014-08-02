package com.ldaniels528.fxcore3d

import java.awt._

import FxIndexingPolygon._
import com.ldaniels528.fxcore3d.camera.FxGenericCamera

/**
 * Describes an abstract indexing polygon.
 * @author lawrence.daniels@gmail.com
 */
abstract class FxIndexingPolygon(myIndices: Array[Int], nbrIndices: Int) {

  def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, cam: FxGenericCamera) {
    paint(g, p.x, p.y)
  }

  def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxGenericCamera, intensity: Double) {
    paint(g, p.x, p.y)
  }

  /**
   * pokes out the 2d coordinates and stores them into the scratch polygon.
   */
  protected def copyIndexedPoints(x: Array[Int], y: Array[Int]) {
    for (n <- 0 to (nbrIndices - 1)) {
      val index = myIndices(n)
      ourScratchPoly.xpoints(n) = x(index)
      ourScratchPoly.ypoints(n) = y(index)
    }
    ourScratchPoly.npoints = nbrIndices
  }

  /**
   * returns the normal of this polygon.
   */
  def getNormal(a: FxArrayOf3DPoints): FxPoint3D = {
    val i0 = myIndices(0)
    val i1 = myIndices(1)
    val i2 = myIndices(nbrIndices - 1)

    val p0 = new FxPoint3D(a.x(i0), a.y(i0), a.z(i0))
    val v1 = p0.vectorTo(new FxPoint3D(a.x(i1), a.y(i1), a.z(i1)))
    val v2 = p0.vectorTo(new FxPoint3D(a.x(i2), a.y(i2), a.z(i2)))

    val norm = new FxPoint3D()
    norm.vectorProduct(v1, v2)
    norm.normalize(1)
    norm
  }

  def makeClone(): FxIndexingPolygon

  /**
   * paints a polygon. the 2d list of coordinates must be supplied
   */
  def paint(g: Graphics2D, x: Array[Int], y: Array[Int])

  def paintWithShading(g: Graphics2D, x: Array[Int], y: Array[Int], intensity: Double) {
    paint(g, x, y)
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

    // -- vector from vertex #1 to vertex #2
    val v1x = ourScratchPoly.xpoints(2) - p1x
    val v1y = ourScratchPoly.ypoints(2) - p1y

    // -- vector from vertex #1 to vertex #0
    val v2x = ourScratchPoly.xpoints(0) - p1x
    val v2y = ourScratchPoly.ypoints(0) - p1y

    // -- return the determinant of the vectors
    v1x * v2y - v2x * v1y
  }

}