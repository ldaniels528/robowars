package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.robowars.ContentManager

/**
 * FxEngine Model Instance
 * @author lawrence.daniels@gmail.com
 */
case class FxModelInstance(thePolyhedron: FxPolyhedron, scale: FxScale3D) {
  protected val polygonIntensities = new Array[Double](thePolyhedron.nbrOfPolygons)
  protected val transformedVertices = new FxArrayOf3DPoints(thePolyhedron.vertices.length)
  protected val transformMatrix = new FxMatrix3D()
  protected val boundingVolume = new FxBoundingVolume(this, scale)
  protected val myPosition = new FxPoint3D()
  protected val myAngle = new FxAngle3D()
  protected val myScale = scale.toPoint

  // create the vertices to be used for storing transformations
  protected var verticesAreDirty: Boolean = true
  protected var matrixIsDirty: Boolean = true
  protected var intensitiesAreDirty: Boolean = true
  protected var normalsAreDirty: Boolean = true
  protected val myLastKnownLight = new FxPoint3D()

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

  def checkForCollisionWith(polyhedron: FxModelInstance): Boolean = {
    boundingVolume.checkForCollisionWith(polyhedron.boundingVolume)
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
      thePolyhedron.clipAndPaintWithShading(g, theirBuffer, camera, polygonIntensities)
    } else {
      thePolyhedron.paintWithShading(g, theirBuffer, polygonIntensities)
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
      thePolyhedron.calculateIntensities(light, polygonIntensities)
      intensitiesAreDirty = false
    }
  }

  private def updateVertices() {
    if (verticesAreDirty) {
      updateMatrix()
      transformMatrix.transform(thePolyhedron.vertices, transformedVertices)
      verticesAreDirty = false
    }
  }

  private def updateMatrix() {
    if (matrixIsDirty) {
      transformMatrix.setTransformMCStoWCS(myPosition, myAngle, myScale)
      matrixIsDirty = false
    }
  }

  def transformPoint(ps: FxPoint3D, pd: FxPoint3D): FxPoint3D = {
    updateMatrix()
    // -- transform the polyhedron model coordinates to world coordinates.
    transformMatrix.transformPoint(ps, pd)
    pd
  }

  def transformPoints(source: FxArrayOf3DPoints, dest: FxArrayOf3DPoints) {
    updateMatrix()
    transformMatrix.transform(source, dest)
  }

  def rotateNormals(source: FxArrayOf3DPoints, dest: FxArrayOf3DPoints) {
    updateMatrix()
    transformMatrix.rotate(source, dest)
  }

  def setScalingFactor(scale: FxPoint3D) {
    myScale.set(scale)
    matrixIsDirty = true
    verticesAreDirty = true
    normalsAreDirty = true
    intensitiesAreDirty = true
  }

  def scalingFactor: FxPoint3D = myScale.makeClone

  def boundingRadius: Double = boundingVolume.boundingRadius

  def position: FxPoint3D = myPosition

}

/**
 * FxEngine Model Instance Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxModelInstance {

  def apply(modelPath:String, scale: FxScale3D): FxModelInstance = {
    new FxModelInstance(ContentManager.loadModel(modelPath), scale)
  }
}