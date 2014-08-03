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

  override def nbrOfPolygons = myPolygons.length

  override def calculateIntensities(light: FxPoint3D, intensities: Array[Double]) {
    val p = new FxPoint3D()
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      p.set(myPolygonNormals.x(n), myPolygonNormals.y(n), myPolygonNormals.z(n))
      intensities(n) = p.dotProduct(light)
    }
  }

  override def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera) {
    for (n <- 0 to (nbrOfPolygons - 1)) {
      myPolygons(n).clipAndPaint(g, p, camera)
    }
  }

  override def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera, intensities: Array[Double]) {
    for (n <- 0 to (nbrOfPolygons - 1)) {
      myPolygons(n).clipAndPaintWithShading(g, p, camera, intensities(n))
    }
  }

  override def makeClone(): FxPolyhedron = {
    val polys = new Array[FxIndexingPolygon](nbrOfPolygons)
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      polys(n) = myPolygons(n).makeClone()
    }
    new FxConvexPolyhedron(vertices.makeClone(), polys, myPolygonNormals)
  }

  /**
   * overrides FxPolyhedron.paint(..) the polygons don't need to be sorted.
   */
  override def paint(g: Graphics2D, point2d: FxArrayOf2DPoints) {
    //  the polygons don't have to be sorted
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      myPolygons(n).paint(g, point2d.x, point2d.y)
    }
  }

  override def paintWithShading(g: Graphics2D, points: FxArrayOf2DPoints, intensities: Array[Double]) {
    (0 to (nbrOfPolygons - 1)) foreach { n =>
      myPolygons(n).paintWithShading(g, points.x, points.y, intensities(n))
    }
  }

  override def scalePoints(fx: Double, fy: Double, fz: Double) {
    (0 to (vertices.npoints - 1)) foreach { n =>
      vertices.x(n) *= fx
      vertices.y(n) *= fy
      vertices.z(n) *= fz
    }
  }

}