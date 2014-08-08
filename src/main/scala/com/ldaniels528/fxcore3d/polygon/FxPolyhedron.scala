package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.{FxArrayOf3DPoints, FxPoint3D, FxProjectedPoints}

/**
 * A polyhedron class that is made out of a list of vertices
 * and a list of indexing polygons.
 */
trait FxPolyhedron {

  def vertices: FxArrayOf3DPoints

  def nbrOfPolygons: Int

  def calculateIntensities(light: FxPoint3D, intensities: Array[Double])

  def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera)

  def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera, intensities: Array[Double])

  def makeClone: FxPolyhedron

  /**
   * paint the polyhedron using the supplied 2D coordinates.
   */
  def paint(g: Graphics2D, point2d: FxProjectedPoints)

  def paintWithShading(g: Graphics2D, point2d: FxProjectedPoints, intensities: Array[Double])

  def scalePoints(fx: Double, fy: Double, fz: Double)

}

