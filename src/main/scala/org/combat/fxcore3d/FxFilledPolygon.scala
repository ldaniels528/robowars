package org.combat.fxcore3d

import java.awt.Graphics2D
import FxIndexingPolygon._

/**
 * A solid color polygon.
 */
class FxFilledPolygon(myIndices: Array[Int], nbrIndices: Int, myColor: FxColor)
  extends FxIndexingPolygon(myIndices, nbrIndices) {

  override def makeClone(): FxIndexingPolygon = {
    val dst = new Array[Int](nbrIndices)
    System.arraycopy(myIndices, 0, dst, 0, dst.length)
    new FxFilledPolygon(dst, nbrIndices, myColor.makeClone())
  }

  /**
   * Paint the polygon if it is CCW
   */
  override def paint(g: Graphics2D, x: Array[Int], y: Array[Int]) {
    // -- copy the indexed coordinates from the 2d list to
    // -- the scratch-pad
    copyIndexedPoints(x, y);
    render(g);
  }

  protected def render(g: Graphics2D) {
    // -- check orientation
    if (orientation() > 0) {
      g.setColor(myColor.getColor());
      g.fillPolygon(ourScratchPoly);
    }
  }

}

/**
 * FxFilledPolygon Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxFilledPolygon {
  import java.io._

  def apply(myIndices: Array[Int], nbrIndices: Int, color: FxColor): FxFilledPolygon = {
    new FxFilledPolygon(myIndices, nbrIndices, color)
  }

}