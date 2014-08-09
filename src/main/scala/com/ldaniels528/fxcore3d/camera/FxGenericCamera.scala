package com.ldaniels528.fxcore3d.camera

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.camera.FxCamera._

/**
 * FxEngine Generic Camera
 * @author lawrence.daniels@gmail.com
 */
abstract class FxGenericCamera(world: FxWorld, viewAngle: Double, viewDistance: Double, pos: FxPoint3D, agl: FxAngle3D)
  extends FxCamera {
  // a temporary buffer used for projection
  protected var my2dBuffer = FxProjectedPoints(25)

  // a temporary buffer used for WCS to VCS transform
  protected var my3dBuffer = FxArrayOf3DPoints(25)

  // the screen distance
  protected var screenDistance: Double = _

  // screen origin, width and height
  protected var x0: Int = 0
  protected var y0: Int = 0
  protected var myWidth = 100
  protected var myHeight = 100

  // construct the matrix
  protected lazy val matrixWCStoVCS = new FxMatrix3D()
  protected lazy val myPosition = FxPoint3D()
  protected lazy val myAngle = FxAngle3D()
  private var matrixIsDirty: Boolean = true
  var zClip: Double = 0

  // the view angle
  calculateParameters()

  // create the light vector
  private var light = FxPoint3D(-1, 0, 0)
  light.rotateAboutAxisX(Math.PI / 5)
  light.rotateAboutAxisY(Math.PI / 3)
  light.normalize(1)

  // set the camera's orientation
  setOrientation(pos, agl)

  private def calculateParameters() {
    // calculate the screen origin
    x0 = myWidth >> 1
    y0 = myHeight >> 1

    // calculate the screen distance
    screenDistance = -(x0 / Math.tan(viewAngle / 2d))
    zClip = -3
  }

  protected def doProjection() {
    // project the VCS coordinates to SCS storing the results in a buffer
    my3dBuffer.points foreach { buf3d =>
      val z = buf3d.z
      val buf2d = my2dBuffer(buf3d.index)
      buf2d.x = (screenDistance * buf3d.x / z).toInt + x0
      buf2d.y = -(screenDistance * buf3d.y / z).toInt + y0
    }

    // limit the 2D buffer
    my2dBuffer.length = my3dBuffer.length
  }

  protected def doTheChecks() {
    // initiate the AND and OR operation
    my2dBuffer.clipAndOp = Int.MaxValue
    my2dBuffer.clipOrOp = 0

    my2dBuffer.points foreach { p =>
      p.clipFlags = 0
      // clip X: left/right
      if (p.x > myWidth) p.clipFlags |= CLIP_RIGHT
      else if (p.x < 0) p.clipFlags |= CLIP_LEFT

      // clip Y: top/bottom
      if (p.y > myHeight) p.clipFlags |= CLIP_BOTTOM
      else if (p.y < 0) p.clipFlags |= CLIP_TOP

      // clip Z: front/back
      p.z = my3dBuffer(p.index).z
      if (p.z > zClip) p.clipFlags |= CLIP_Z

      // update the master flags
      my2dBuffer.clipOrOp |= p.clipFlags
      my2dBuffer.clipAndOp &= p.clipFlags
    }
  }

  protected def doTransform(p3d: FxArrayOf3DPoints) {
    updateMatrix()
    matrixWCStoVCS.transform(p3d, my3dBuffer)
    my3dBuffer.setLength( p3d.length )
  }

  def paint(g: Graphics2D) {
    // filter for only the objects within our bounding sphere
    val visibleObjects = world.getAllObjectsInSphere(myPosition, viewDistance, sphereIsVisible)

    // sort the objects from the closest to farthest Z-axis
    val sortedObjects = visibleObjects.sortBy(-_.distanceToPoint(myPosition))

    // paint the objects
    sortedObjects.foreach(_.paintWithShading(g, this, light))
  }

  /**
   * projects an array of 3d points to the temporary 2d buffer
   */
  override def project(p3d: FxArrayOf3DPoints): FxProjectedPoints = {
    doTransform(p3d)
    doProjection()
    my2dBuffer
  }

  /**
   * Transforms and projects the points to the screen. It also stores the z
   * coordinate and clipping info in case the polygons need clipping.
   */
  override def projectWithCheck(p3d: FxArrayOf3DPoints): FxProjectedPoints = {
    doTransform(p3d)
    doProjection()
    doTheChecks()
    my2dBuffer
  }

  /**
   * Sets the light source
   */
  def setLightSource(vector: FxPoint3D): Unit = light = vector.copy()

  /**
   * sets the position and angle of the camera.
   */
  def setOrientation(pos: FxPoint3D, agl: FxAngle3D) {
    if (myPosition != pos) {
      myPosition.set(pos)
      matrixIsDirty = true
    }

    if (myAngle != agl) {
      myAngle.set(agl)
      matrixIsDirty = true
    }
  }

  def setScreenSize(width: Int, height: Int) {
    if (myWidth != width || myHeight != height) {
      myWidth = width
      myHeight = height
      calculateParameters()
    }
  }

  /**
   * updates the matrix
   */
  protected def updateMatrix() {
    if (matrixIsDirty) {
      matrixWCStoVCS.setTransformWCStoVCS(myPosition, myAngle)
      matrixIsDirty = false
    }
  }

  def clipAndStoreZ(xs1: Int, ys1: Int, zs1: Double,
                    xs2: Int, ys2: Int, zs2: Double,
                    xStore: Array[Int],
                    yStore: Array[Int], p: Int) {
    // transform points from SCS to VCS
    val scaledZ1: Double = zs1 / screenDistance
    var xv1 = scaledZ1 * (xs1 - x0)
    var yv1 = scaledZ1 * (ys1 - y0)

    val scaledZ2: Double = zs2 / screenDistance
    val xv2 = scaledZ2 * (xs2 - x0)
    val yv2 = scaledZ2 * (ys2 - y0)

    val t = (zClip - zs1) / (zs2 - zs1)
    xv1 = xv1 + t * (xv2 - xv1)
    yv1 = yv1 + t * (yv2 - yv1)

    // project the points
    xStore(p) = (screenDistance * xv1 / zClip).toInt + x0
    yStore(p) = (screenDistance * yv1 / zClip).toInt + y0
  }

  private def sphereIsVisible(pos: FxPoint3D, radius: Double): Boolean = {
    updateMatrix()
    val p = matrixWCStoVCS.transformPoint(pos, FxPoint3D())
    (p.z - radius) <= zClip
  }

}