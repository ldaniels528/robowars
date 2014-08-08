package com.ldaniels528.fxcore3d.polygon

import java.awt.Graphics2D

import com.ldaniels528.fxcore3d.polygon.FxIndexingPolygon._
import com.ldaniels528.fxcore3d.{FxColor, FxProjectedPoints}

/**
 * A solid color polygon.
 */
class FxFilledPolygon(myIndices: Seq[Int], myColor: FxColor) extends FxIndexingPolygon(myIndices) {

  override def makeClone: FxIndexingPolygon = {
    val dst = new Array[Int](myIndices.length)
    System.arraycopy(myIndices, 0, dst, 0, dst.length)
    new FxFilledPolygon(dst, myColor.copy())
  }

  /**
   * Paint the polygon if it is CCW
   */
  override def paint(g: Graphics2D, p: FxProjectedPoints) {
    // copy the indexed coordinates from the 2d list to the scratch-pad
    copyIndexedPoints(p)
    render(g)
  }

  protected def render(g: Graphics2D) {
    // check orientation
    if (orientation() > 0) {
      g.setColor(myColor.color)
      g.fillPolygon(ourScratchPoly)
    }
  }

}

/**
 * FxFilledPolygon Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxFilledPolygon {

  def apply(myIndices: Seq[Int], color: FxColor): FxFilledPolygon = {
    new FxFilledPolygon(myIndices, color)
  }

}