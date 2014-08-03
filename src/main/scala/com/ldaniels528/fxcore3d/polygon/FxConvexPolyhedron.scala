package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.{FxArrayOf2DPoints, FxArrayOf3DPoints, FxPoint3D, FxProjectedPoints}

/**
 * FxEngine Convex Polyhedron
 * @author lawrence.daniels@gmail.com
 */
case class FxConvexPolyhedron(vertices: FxArrayOf3DPoints,
                              myPolygons: Seq[FxIndexingPolygon],
                              myPolygonNormals: FxArrayOf3DPoints) extends FxPolyhedron {

  val nbrOfPolygons = myPolygons.length

  override def calculateIntensities(light: FxPoint3D, intensities: Array[Double]) {
    val p = new FxPoint3D()
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      p.set(myPolygonNormals.x(n), myPolygonNormals.y(n), myPolygonNormals.z(n))
      intensities(n) = p.dotProduct(light)
    }
  }

  override def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera) {
    myPolygons foreach (_.clipAndPaint(g, p, camera))
  }

  override def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera, intensities: Array[Double]) {
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      myPolygons(n).clipAndPaintWithShading(g, p, camera, intensities(n))
    }
  }

  override def makeClone(): FxPolyhedron = {
    new FxConvexPolyhedron(vertices.makeClone(), myPolygons map (_.makeClone), myPolygonNormals)
  }

  /**
   * overrides FxPolyhedron.paint(..) the polygons don't need to be sorted.
   */
  override def paint(g: Graphics2D, point2d: FxArrayOf2DPoints) {
    myPolygons foreach (_.paint(g, point2d.x, point2d.y))
  }

  override def paintWithShading(g: Graphics2D, points: FxArrayOf2DPoints, intensities: Array[Double]) {
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      myPolygons(n).paintWithShading(g, points.x, points.y, intensities(n))
    }
  }

  override def scalePoints(fx: Double, fy: Double, fz: Double) {
    (0 to (vertices.length - 1)) foreach { n =>
      vertices.x(n) *= fx
      vertices.y(n) *= fy
      vertices.z(n) *= fz
    }
  }

}