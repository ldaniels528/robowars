package com.ldaniels528.fxcore3d

import com.ldaniels528.fxcore3d.camera.FxGenericCamera
import scala.beans.BeanProperty
import java.awt.Graphics2D

/**
 * A polyhedron class that is made out of a list of vertices
 * and a list of indexing polygons.
 */
trait FxPolyhedron {

  def vertices: FxArrayOf3DPoints
  
  def nbrOfPolygons: Int
  
  def calculateIntensities(light: FxPoint3D, intensities: Array[Double])

  def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxGenericCamera)

  def clipAndPaintWithShading(g: Graphics2D, p: FxProjectedPoints, camera: FxGenericCamera, intensities: Array[Double])

  def makeClone(): FxPolyhedron

  /**
   * paint the polyhedron using the supplied 2D coordinates.
   */
  def paint(g: Graphics2D, point2d: FxArrayOf2DPoints)

  def paintWithShading(g: Graphics2D, point2d: FxArrayOf2DPoints, intensities: Array[Double])

  def scalePoints(fx: Double, fy: Double, fz: Double)

}

