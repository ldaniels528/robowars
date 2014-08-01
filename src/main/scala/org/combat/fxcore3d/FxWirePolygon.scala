package org.combat.fxcore3d

import java.awt._
import java.io._

import FxIndexingPolygon._
import org.combat.fxcore3d.camera.FxGenericCamera

/**
 * FxEngine Wire Polygon
 * @author lawrence.daniels@gmail.com
 */
class FxWirePolygon(myIndices: Array[Int], nbrIndices: Int)
  extends FxIndexingPolygon(myIndices, nbrIndices) {

  override def clipAndPaint(g: Graphics2D, p: FxProjectedPoints, cam: FxGenericCamera) {
    paint(g, p.x, p.y)
  }

  override def makeClone(): FxIndexingPolygon = {
    val dest = new Array[Int](nbrIndices)
    System.arraycopy(myIndices, 0, dest, 0, nbrIndices)
    return new FxWirePolygon(dest, nbrIndices)
  }

  override def paint(g: Graphics2D, x: Array[Int], y: Array[Int]) {
    copyIndexedPoints(x, y)
    g.setColor(Color.black)
    g.drawPolygon(ourScratchPoly)
  }
  
}

/**
 * FxWirePolygon Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FxWirePolygon {

  def apply(myIndices: Array[Int], nbrIndices: Int): FxWirePolygon = {
    new FxWirePolygon(myIndices, nbrIndices)
  }

}