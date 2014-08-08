package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.camera.FxCamera
import com.ldaniels528.fxcore3d.polygon.FxClippingFilledPolygon._
import com.ldaniels528.fxcore3d.polygon.FxIndexingPolygon._
import com.ldaniels528.fxcore3d.{FxColor, FxProjectedPoints}

/**
 * FxEngine Clipping Filled Polygon
 * @author lawrence.daniels@gmail.com
 */
class FxClippingFilledPolygon(myIndices: Seq[Int], myColor: FxColor) extends FxFilledPolygon(myIndices, myColor) {

  override def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, camera: FxCamera) {
    // -- gather information about the clipping needed for
    var clipFlagsAndOp: Int = p(myIndices.head).clipFlags
    var clipFlagsOrOp: Int = clipFlagsAndOp

    myIndices.tail foreach { index =>
      val flag = p(index).clipFlags
      clipFlagsOrOp |= flag
      clipFlagsAndOp &= flag
    }

    // -- check if all points of the polygon are
    // -- out of view.
    if (clipFlagsAndOp != 0) {
      // -- all points are on one side of the
      // -- clipping volume meaning that the
      // -- entire polygon is out of view.
      return
    }

    // -- check if any clipping is needed
    if (clipFlagsOrOp == 0) {
      // -- no clipping needed, do the standard fill
      paint(g, p)
      return
    }

    // -- clipping is needed
    if ((clipFlagsOrOp & FxCamera.CLIP_Z) != 0) {
      // -- polygon points are behind the camera
      // -- z-clipping needed.
      val pLeft = clipZ(p, xt, yt, camera)
      if (pLeft == 0) {
        // -- no points left after clipping.
        return
      } else {
        // -- polygon successfully clipped
        copyPoints(xt, yt, pLeft)
        super.render(g)
      }
    } else paint(g, p)
  }

  protected def copyPoints(x: Array[Int], y: Array[Int], length: Int) {
    System.arraycopy(x, 0, ourScratchPoly.xpoints, 0, length)
    System.arraycopy(y, 0, ourScratchPoly.ypoints, 0, length)
    ourScratchPoly.npoints = length
  }

  protected def clipZ(p: FxProjectedPoints, xt: Array[Int], yt: Array[Int], cam: FxCamera): Int = {
    val p0 = 0
    var pts = 0
    var i0 = myIndices(p0)

    var inside = p(i0).z < cam.zClip
    if (inside) {
      val pi0 = p(i0)
      xt(0) = pi0.x
      yt(0) = pi0.y
      pts += 1
    }

    myIndices.tail foreach { i1 =>
      // is the point in front of the camera?
      if (p(i1).z <= cam.zClip) {
        // was the last point was "outside"?
        if (!inside) {
          val pi0 = p(i0)
          val pi1 = p(i1)
          cam.clipAndStoreZ(pi0.x, pi0.y, pi0.z, pi1.x, pi1.y, pi1.z, xt, yt, pts)
          pts += 1
        }

        // -- point inside, store it in the array
        inside = true
        val pi1 = p(i1)
        xt(pts) = pi1.x
        yt(pts) = pi1.y
        pts += 1

      } else {
        // point behind camera:
        // the last point was "inside" view volume?
        if (inside) {
          val pi0 = p(i0)
          val pi1 = p(i1)
          cam.clipAndStoreZ(pi0.x, pi0.y, pi0.z, pi1.x, pi1.y, pi1.z, xt, yt, pts)
          pts += 1
        }
        inside = false
      }
      i0 = i1
    }
    pts
  }

  override def makeClone: FxIndexingPolygon = {
    val newIndices = new Array[Int](myIndices.length)
    System.arraycopy(myIndices, 0, newIndices, 0, myIndices.length)
    new FxClippingFilledPolygon(newIndices, myColor.makeClone)
  }

}

/**
 * FxEngine Clipping Filled Polygon Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxClippingFilledPolygon {

  val xt = new Array[Int](100)
  val yt = new Array[Int](100)

  def apply(myIndices: Seq[Int], color: FxColor) = new FxClippingFilledPolygon(myIndices, color)

}