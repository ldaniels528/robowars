package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.{FxArrayOf3DPoints, FxPoint3D, FxProjectedPoints}

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
    myPolygonNormals.points.foreach { poly =>
      p.set(poly.x, poly.y, poly.z)
      intensities(poly.index) = p.dotProduct(light)
    }
  }

  override def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera) {
    myPolygons foreach (_.clipAndPaint(g, p, camera))
  }

  override def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera, intensities: Array[Double]) {
    var n = 0
    myPolygons foreach { poly =>
      poly.clipAndPaintWithShading(g, p, camera, intensities(n))
      n += 1
    }
  }

  override def makeClone: FxPolyhedron = {
    new FxConvexPolyhedron(vertices.makeClone, myPolygons map (_.makeClone), myPolygonNormals)
  }

  /**
   * overrides FxPolyhedron.paint(..) the polygons don't need to be sorted.
   */
  override def paint(g: Graphics2D, point2d: FxProjectedPoints) {
    myPolygons foreach (_.paint(g, point2d))
  }

  override def paintWithShading(g: Graphics2D, points: FxProjectedPoints, intensities: Array[Double]) {
    var n = 0
    myPolygons foreach { poly =>
      poly.paintWithShading(g, points, intensities(n))
      n += 1
    }
  }

  override def scalePoints(fx: Double, fy: Double, fz: Double) {
    vertices.points foreach { vertex =>
      vertex.x *= fx
      vertex.y *= fy
      vertex.z *= fz
    }
  }

}