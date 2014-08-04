package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.camera.FxCamera

/**
 * FxEngine Polyhedron Instance
 * @author lawrence.daniels@gmail.com
 */
class FxPolyhedronInstance(thePolyhedron: FxPolyhedron, myScale: FxPoint3D) {
  // define the transformed vertices
  protected val transformedVertices = Fx3DPointSeq(thePolyhedron.vertices.length)
  // define the matrix used for transformations
  protected val myTransformMatrix = new FxMatrix3D()
  // define position in WCS
  protected val myPosition = new FxPoint3D()
  // define the angle in WCS
  protected val myAngle = new FxAngle3D()

  // create the vertices to be used for storing transformations
  protected var verticesAreDirty: Boolean = true
  protected var matrixIsDirty: Boolean = true
  protected var intensitiesAreDirty: Boolean = true
  protected var normalsAreDirty: Boolean = true

  // --
  protected val myBoundingVolume = new FxBoundingVolume(this, myScale)
  protected val myLastKnownLight = new FxPoint3D()
  protected val myPolygonIntensities = new Array[Double](thePolyhedron.nbrOfPolygons)

  /**
   * set the position and angle for this polyhedron instance.
   */
  def setOrientation(pos: FxPoint3D, agl: FxAngle3D) {
    if (myPosition != pos || myAngle != agl) {
      matrixIsDirty = true
      verticesAreDirty = true
      normalsAreDirty = true
      intensitiesAreDirty = true
      myPosition.set(pos)
      myAngle.set(agl)
    }
  }

  def checkForCollisionWith(polyhedron: FxPolyhedronInstance): Boolean = {
    myBoundingVolume.checkForCollisionWith(polyhedron.myBoundingVolume)
  }

  def clipAndPaint(g: Graphics2D, camera: FxCamera) {
    updateVertices()
    val theirBuffer = camera.projectWithCheck(transformedVertices)
    if (theirBuffer.clipAndOp != 0) {
      // -- all vertices outside the view
      return 
    }
    if (theirBuffer.clipOrOp != 0) {
      // -- some vertices outside
      thePolyhedron.clipAndPaintWithShading(g, theirBuffer, camera, myPolygonIntensities)
    } else {
      thePolyhedron.paintWithShading(g, theirBuffer, myPolygonIntensities)
    }
  }

  /**
   * paint the polyhedron instance.
   */
  def paint(g: Graphics2D, camera: FxCamera) {
    updateVertices()
    thePolyhedron.paint(g, camera.project(transformedVertices))
  }

  def paintWithShading(g: Graphics2D, camera: FxCamera, light: FxPoint3D) {
    updateVertices()
    paint(g, camera)
  }

  def clipAndPaintWithShading(g: Graphics2D, camera: FxCamera, light: FxPoint3D) {
    updateIntensities(light)
    clipAndPaint(g, camera)
  }

  protected def updateIntensities(light: FxPoint3D) {
    if (intensitiesAreDirty || myLastKnownLight != light) {
      // -- polyhedron has changed orientation or light has moved
      // -- or both of the above => polygon intensities must be updated
      myLastKnownLight.set(light)
      thePolyhedron.calculateIntensities(light, myPolygonIntensities)
      intensitiesAreDirty = false
    }
  }

  private def updateVertices() {
    if (verticesAreDirty) {
      updateMatrix()
      myTransformMatrix.transform(thePolyhedron.vertices, transformedVertices)
      verticesAreDirty = false
    }
  }

  private def updateMatrix() {
    if (matrixIsDirty) {
      myTransformMatrix.setTransformMCStoWCS(myPosition, myAngle, myScale)
      matrixIsDirty = false
    }
  }

  def transformPoint(ps: FxPoint3D, pd: FxPoint3D) {
    updateMatrix()
    // -- transform the polyhedron model coordinates to world coordinates.
    myTransformMatrix.transformPoint(ps, pd)
  }

  def transformPoints(source: FxArrayOf3DPoints, dest: FxArrayOf3DPoints) {
    updateMatrix()
    myTransformMatrix.transform(source, dest)
  }

  def rotateNormals(source: FxArrayOf3DPoints, dest: FxArrayOf3DPoints) {
    updateMatrix()
    myTransformMatrix.rotate(source, dest)
  }

  def setScalingFactor(scale: FxPoint3D) {
    myScale.set(scale)
    matrixIsDirty = true
    verticesAreDirty = true
    normalsAreDirty = true
    intensitiesAreDirty = true
  }

  def getScalingFactor(): FxPoint3D = myScale.makeClone

  def getBoundingRadius(): Double = myBoundingVolume.boundingRadius

  def getPosition(): FxPoint3D = myPosition

}
