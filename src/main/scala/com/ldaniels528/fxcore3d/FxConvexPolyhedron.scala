package com.ldaniels528.fxcore3d

import java.awt.Graphics2D
import scala.beans.BeanProperty

import com.ldaniels528.fxcore3d.camera.FxGenericCamera;

/**
 * FxEngine Convex Polyhedron
 * @author lawrence.daniels@gmail.com
 */
class FxConvexPolyhedron(
  val vertices: FxArrayOf3DPoints,
  val myPolygons: Array[FxIndexingPolygon],
  val nbrOfPolygons: Int,
  normals: FxArrayOf3DPoints = null) extends FxPolyhedron {

  // the 3d coordinates for the model
  @BeanProperty var myPolygonNormals: FxArrayOf3DPoints = normals

  // create the normals if not defined
  if (myPolygonNormals == null) {
    makePolygonNormals()
  }

  private def makePolygonNormals() {
    myPolygonNormals = FxArrayOf3DPoints(nbrOfPolygons)
    for (n <- 0 to (nbrOfPolygons - 1)) {
      val norm = myPolygons(n).getNormal(vertices)
      myPolygonNormals.x(n) = norm.x
      myPolygonNormals.y(n) = norm.y
      myPolygonNormals.z(n) = norm.z
    }
  }

  override def calculateIntensities(light: FxPoint3D, intensities: Array[Double]) {
    val ptemp = new FxPoint3D()
    for (n <- 0 to (nbrOfPolygons - 1)) {
      ptemp.set(myPolygonNormals.x(n), myPolygonNormals.y(n), myPolygonNormals.z(n))
      intensities(n) = ptemp.dotProduct(light)
    }
  }

  /**
   * overrides FxPolyhedron.paint(..) the polygons don't need to be sorted.
   */
  def paint(g: Graphics2D, point2d: FxArrayOf2DPoints) {
    //  the polygons don't have to be sorted
    for (n <- 0 to (nbrOfPolygons - 1)) {
      myPolygons(n).paint(g, point2d.x, point2d.y);
    }
  }

  def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxGenericCamera) {
    for (n <- 0 to (nbrOfPolygons - 1)) {
      myPolygons(n).clipAndPaint(g, p, camera);
    }
  }

  def paintWithShading(g: Graphics2D, point2d: FxArrayOf2DPoints, intensities: Array[Double]) {
    for (n <- 0 to (nbrOfPolygons - 1)) {
      myPolygons(n).paintWithShading(g, point2d.x, point2d.y, intensities(n));
    }
  }

  def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxGenericCamera, intensities: Array[Double]) {
    for (n <- 0 to (nbrOfPolygons - 1)) {
      myPolygons(n).clipAndPaintWithShading(g, p, camera, intensities(n));
    }
  }

  def makeClone(): FxPolyhedron = {
    val polys = new Array[FxIndexingPolygon](nbrOfPolygons)
    for (n <- 0 to (nbrOfPolygons - 1)) {
      polys(n) = myPolygons(n).makeClone();
    }
    new FxConvexPolyhedron(vertices.makeClone(), polys, nbrOfPolygons, myPolygonNormals);
  }

  override def scalePoints(fx: Double, fy: Double, fz: Double) {
    for (n <- 0 to (vertices.npoints - 1)) {
      vertices.x(n) *= fx
      vertices.y(n) *= fy
      vertices.z(n) *= fz
    }
  }

}