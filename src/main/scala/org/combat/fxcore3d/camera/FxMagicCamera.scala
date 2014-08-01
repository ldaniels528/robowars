package org.combat.fxcore3d.camera

import java.awt.Color
import java.awt.Graphics2D

import org.combat.fxcore3d.FxAngle3D
import org.combat.fxcore3d.FxArrayOf3DPoints
import org.combat.fxcore3d.FxPoint3D
import org.combat.fxcore3d.FxProjectedPoints
import org.combat.fxcore3d.FxWorld

/**
 * FxEngine Magic Camera
 * @author lawrence.daniels@gmail.com
 */
class FxMagicCamera(
  world: FxWorld,
  viewAngle: Double,
  viewDistance: Double,
  pos: FxPoint3D,
  agl: FxAngle3D,
  gridSize: Double,
  fadingFactor: Double)
  extends FxCamera(world, viewAngle, viewDistance, pos, agl) {

  import FxGenericCamera.{ our2dBuffer, our3dBuffer }

  // predefine the sky and ground colors
  private val startTime = System.currentTimeMillis()
  private def mySkyColor = { //  new Color(50, 50, 200) 
    val elapsedSecs = ((System.currentTimeMillis() - startTime) / 1000L).toInt
    val shade = 60 - (elapsedSecs % 60)
    new Color(shade, shade, 200) 
  }
  private val myGroundColor = new Color(130, 130, 50)

  // make the default ground
  private val pts = ((2 * viewDistance / gridSize) + 1).toInt
  private val nbrPointsInGround: Int = pts * pts
  private val Gwcs = FxArrayOf3DPoints(nbrPointsInGround)

  var n = 0
  var x = -viewDistance
  while (x <= viewDistance) {
    var z = -viewDistance
    while (z <= viewDistance) {
      Gwcs.x(n) = x
      Gwcs.y(n) = 0
      Gwcs.z(n) = z
      n += 1
      z += gridSize
    }
    x += gridSize
  }

  if (our3dBuffer.npoints < nbrPointsInGround) {
    our3dBuffer = FxArrayOf3DPoints(nbrPointsInGround)
    our2dBuffer = FxProjectedPoints(nbrPointsInGround)
  }

  override def paint(g: Graphics2D) {
    fakeHorizon(g)
    fakeGround(g)
    super.paint(g)
  }

  protected def fakeHorizon(g: Graphics2D) {
    val p = new FxPoint3D(0, myPosition.y, -viewDistance * 5)
    p.rotateAboutXaxis(-myAngle.x)

    val scry = ((screendist * p.y / p.z) + y0).toInt
    if (scry < 0) {
      g.setColor(if (p.z < 0) myGroundColor else mySkyColor)
      g.fillRect(0, 0, x0 << 1, y0 << 1)
    } else if (scry > 2 * y0) {
      g.setColor(if (p.z > 0) myGroundColor else mySkyColor)
      g.fillRect(0, 0, x0 << 1, y0 << 1)
    } else {
      if (p.z < 0) {
        g.setColor(mySkyColor)
        g.fillRect(0, 0, x0 << 1, scry)
        g.setColor(myGroundColor)
        g.fillRect(0, scry, x0 << 1, y0 << 1)
      } else {
        g.setColor(myGroundColor)
        g.fillRect(0, 0, x0 << 1, scry)
        g.setColor(mySkyColor)
        g.fillRect(0, scry, x0 << 1, y0 << 1)
      }
    }
  }

  protected def fakeGround(g: Graphics2D) {
    val Xcen = (myPosition.x / gridSize).toInt
    val Zcen = (myPosition.z / gridSize).toInt

    updateMatrix()
    myWCStoVCSmatrix.transformWithTranslation(
      Gwcs,
      our3dBuffer,
      new FxPoint3D(Xcen * gridSize, 0, Zcen * gridSize))
    our3dBuffer.npoints = Gwcs.npoints

    doProjection()
    doTheChecks()

    g.setColor(new Color(0, 0, 0))
    for (i <- 0 to (our2dBuffer.npoints - 1)) {
      if (our2dBuffer.clipFlags(i) == 0) {
        g.drawLine(our2dBuffer.x(i), our2dBuffer.y(i), our2dBuffer.x(i), our2dBuffer.y(i))
      }
    }
  }

}