package org.combat.fxcore3d.camera

import FxGenericCamera._

import org.combat.fxcore3d._
import org.combat.fxcore3d.matrix._

/**
 * FxEngine generic camera
 * @author lawrence.daniels@gmail.com
 */
class FxGenericCamera(viewAngle: Double) {
  // -- the screen distance
  protected var screendist: Double = _

  // -- screen origin, width and height
  protected var x0: Int = 0
  protected var y0: Int = 0
  protected var myWidth = 100
  protected var myHeight = 100

  // -- construct the matrix
  protected lazy val myWCStoVCSmatrix = new FxMatrix3D()
  protected lazy val myPosition = new FxPoint3D()
  protected lazy val myAngle = new FxAngle3D()
  protected var matrixIsDirty: Boolean = true
  var Zclip: Double = 0

  // -- the view angle
  calculateParameters()

  def setScreenSize(width: Int, height: Int) {
    if (myWidth != width || myHeight != height) {
      myWidth = width
      myHeight = height
      calculateParameters()
    }
  }

  private def calculateParameters() {
    // calculate the screen origin
    x0 = myWidth >> 1
    y0 = myHeight >> 1

    // calculate the screen distance
    screendist = -(x0 / (Math.tan(viewAngle / 2))).toDouble
    Zclip = -3
  }

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

  def update(dt: Double) {}

  /**
   * projects an array of 3d points to the temporary 2d buffer
   */
  def project(p3d: FxArrayOf3DPoints): FxArrayOf2DPoints = {
    doTransform(p3d)
    doProjection()
    our2dBuffer
  }

  protected def doTransform(p3d: FxArrayOf3DPoints) {
    updateMatrix()
    myWCStoVCSmatrix.transform(p3d, our3dBuffer)
    our3dBuffer.npoints = p3d.npoints
  }

  protected def doProjection() {
    // -- project the VCS coordinates to SCS storing the results
    // -- in a buffer
    for (n <- 0 to (our3dBuffer.npoints - 1)) {
      val z = our3dBuffer.z(n)
      our2dBuffer.x(n) = (screendist * our3dBuffer.x(n) / z).toInt + x0
      our2dBuffer.y(n) = -(screendist * our3dBuffer.y(n) / z).toInt + y0
    }

    our2dBuffer.npoints = our3dBuffer.npoints
    // -- lend the buffer to the caller.
  }

  protected def doTheChecks() {
    // -- initiate the AND and OR operation
    our2dBuffer.clipAndOp = Int.MaxValue
    our2dBuffer.clipOrOp = 0

    for (n <- 0 to (our2dBuffer.npoints - 1)) {
      our2dBuffer.clipFlags(n) = 0
      if (our2dBuffer.x(n) > myWidth) {
        our2dBuffer.clipFlags(n) |= CLIP_RIGHT
      } else if (our2dBuffer.x(n) < 0) {
        our2dBuffer.clipFlags(n) |= CLIP_LEFT
      }
      if (our2dBuffer.y(n) > myHeight) {
        our2dBuffer.clipFlags(n) |= CLIP_BOTTOM
      } else if (our2dBuffer.y(n) < 0) {
        our2dBuffer.clipFlags(n) |= CLIP_TOP
      }

      our2dBuffer.z(n) = our3dBuffer.z(n)
      if (our2dBuffer.z(n) > Zclip) {
        our2dBuffer.clipFlags(n) |= CLIP_Z
      }
      our2dBuffer.clipOrOp |= our2dBuffer.clipFlags(n)
      our2dBuffer.clipAndOp &= our2dBuffer.clipFlags(n)
    }
  }

  /**
   * Transforms and projects the points to the screen. It also stores the z
   * coordinate and clipping info in case the polygons need clipping.
   */
  def projectWithCheck(p3d: FxArrayOf3DPoints): FxProjectedPoints = {
    doTransform(p3d)
    doProjection()
    doTheChecks()
    our2dBuffer
  }

  /**
   * updates the matrix
   */
  protected def updateMatrix() {
    if (matrixIsDirty) {
      myWCStoVCSmatrix.makeWCStoVCStransform(myPosition, myAngle)
      matrixIsDirty = false
    }
  }

  def clipZandStore(
    xs1: Int, ys1: Int, zs1: Double,
    xs2: Int, ys2: Int, zs2: Double,
    xstore: Array[Int],
    ystore: Array[Int], p: Int) {
    // -- transform points from SCS to VCS
    val zscaled1: Double = zs1 / screendist
    var xv1 = zscaled1 * (xs1 - x0)
    var yv1 = zscaled1 * (ys1 - y0)

    val zscaled2 = zs2 / screendist
    var xv2 = zscaled2 * (xs2 - x0)
    var yv2 = zscaled2 * (ys2 - y0)

    val t = (Zclip - zs1) / (zs2 - zs1)
    xv1 = xv1 + t * (xv2 - xv1)
    yv1 = yv1 + t * (yv2 - yv1)

    // -- project the points
    xstore(p) = (screendist * xv1 / Zclip).toInt + x0
    ystore(p) = (screendist * yv1 / Zclip).toInt + y0
  }

}

/**
 * FxGenericCamera Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxGenericCamera {
  val CLIP_TOP = 1
  val CLIP_BOTTOM = 2
  val CLIP_RIGHT = 4
  val CLIP_LEFT = 8
  val CLIP_Z = 16

  // -- a temporary buffer used for projection
  var our2dBuffer = FxProjectedPoints(250)

  // -- a temporary buffer used for WCS to VCS transform
  var our3dBuffer = FxArrayOf3DPoints(250)

}
