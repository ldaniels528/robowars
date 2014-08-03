package com.ldaniels528.fxcore3d.polygon

import java.awt._

import com.ldaniels528.fxcore3d.FxProjectedPoints
import com.ldaniels528.fxcore3d.camera.FxCamera

/**
 * FxEngine Wire Polygon
 * @author lawrence.daniels@gmail.com
 */
class FxWirePolygon(myIndices: Seq[Int]) extends FxIndexingPolygon(myIndices) {

  override def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, cam: FxCamera) {
    paint(g, p.x, p.y)
  }

  override def makeClone(): FxIndexingPolygon = {
    val dest = new Array[Int](myIndices.length)
    System.arraycopy(myIndices, 0, dest, 0, myIndices.length)
    return new FxWirePolygon(dest)
  }

  override def paint(g: Graphics2D, x: Array[Int], y: Array[Int]) {
    copyIndexedPoints(x, y)
    g.setColor(Color.black)
    g.drawPolygon(FxIndexingPolygon.ourScratchPoly)
  }

}

/**
 * FxWirePolygon Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxWirePolygon {

  def apply(myIndices: Seq[Int]): FxWirePolygon = {
    new FxWirePolygon(myIndices)
  }

}