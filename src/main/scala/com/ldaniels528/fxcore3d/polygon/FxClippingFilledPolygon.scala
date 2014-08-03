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
    var clipFlagsAndOp: Int = p.clipFlags(myIndices(0))
    var clipFlagsOrOp: Int = clipFlagsAndOp

    (1 to (myIndices.length - 1)) foreach { n =>
      val temp = p.clipFlags(myIndices(n))
      clipFlagsOrOp |= temp
      clipFlagsAndOp &= temp
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
      paint(g, p.x, p.y)
      return
    }

    // -- clipping is needed
    if ((clipFlagsOrOp & FxCamera.CLIP_Z) != 0) {
      // -- polygon points are behind the camera
      // -- z-clipping needed.
      val pleft = clipZ(p, xt, yt, camera)
      if (pleft == 0) {
        // -- no points left after clipping.
        return
      } else {
        // -- polygon successfully clipped
        copyPoints(xt, yt, pleft)
        super.render(g)
      }
    } else {
      paint(g, p.x, p.y)
    }
  }

  protected def copyPoints(x: Array[Int], y: Array[Int], npts: Int) {
    for (n <- 0 to (npts - 1)) {
      ourScratchPoly.xpoints(n) = x(n)
      ourScratchPoly.ypoints(n) = y(n)
    }
    ourScratchPoly.npoints = npts
  }

  protected def clipZ(p: FxProjectedPoints, xt: Array[Int], yt: Array[Int], cam: FxCamera): Int = {
    val p0 = 0
    var pts = 0
    var i0 = myIndices(p0)

    var inside = p.z(i0) < cam.zClip
    if (inside) {
      xt(0) = p.x(i0)
      yt(0) = p.y(i0)
      pts += 1
    }

    for (n <- 1 to (myIndices.length - 1)) {
      val p1 = if (n < myIndices.length) n else 0
      val i1 = myIndices(p1)

      if (p.z(i1) < cam.zClip) {
        // -- point in front of the camera
        if (!inside) {
          // -- last point was "outside"
          cam.clipAndStoreZ(p.x(i0), p.y(i0), p.z(i0), p.x(i1), p.y(i1), p.z(i1), xt, yt, pts)
          pts += 1
        }
        // -- point inside, store it in the array
        inside = true
        xt(pts) = p.x(i1)
        yt(pts) = p.y(i1)
        pts += 1
      } else {
        // -- point behind camera
        if (inside) {
          // -- the last point was "inside" view volume
          cam.clipAndStoreZ(p.x(i0), p.y(i0), p.z(i0), p.x(i1),
            p.y(i1), p.z(i1), xt, yt, pts)
          pts += 1
        }
        inside = false
      }
      i0 = i1
    }
    pts
  }

  override def makeClone(): FxIndexingPolygon = {
    val dst = new Array[Int](myIndices.length)
    System.arraycopy(myIndices, 0, dst, 0, myIndices.length)
    new FxClippingFilledPolygon(dst, myColor.copy())
  }

}

/**
 * FxEngine Clipping Filled Polygon Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxClippingFilledPolygon {

  val xt = new Array[Int](100)
  val yt = new Array[Int](100)

  def apply(myIndices: Seq[Int], color: FxColor): FxClippingFilledPolygon = {
    new FxClippingFilledPolygon(myIndices, color)
  }

}