package com.ldaniels528.fxcore3d.camera

import java.awt.{Color, Graphics2D}

import com.ldaniels528.fxcore3d.camera.FxGenericCamera._
import com.ldaniels528.fxcore3d.{FxAngle3D, FxArrayOf3DPoints, FxPoint3D, FxProjectedPoints, FxWorld}

/**
 * FxEngine Scene Camera renders the scene with skyline and terrain
 * @author lawrence.daniels@gmail.com
 */
class FxSceneCamera(world: FxWorld,
                    viewAngle: Double,
                    viewDistance: Double,
                    pos: FxPoint3D,
                    agl: FxAngle3D,
                    gridSize: Double)
  extends FxGenericCamera(world, viewAngle, viewDistance, pos, agl) {

  // capture the camera's start time
  private val startTime = System.currentTimeMillis()

  // predefine the sky and ground colors
  private def mySkyColor = {
    //  new Color(50, 50, 200)
    val elapsedSecs = ((System.currentTimeMillis() - startTime) / 1000L).toInt
    val shade = 60 - (elapsedSecs % 60)
    new Color(shade, shade, 200)
  }

  // make the default ground
  private val myGroundColor = new Color(130, 130, 50)
  private val pts = ((2 * viewDistance / gridSize) + 1).toInt
  private val nbrPointsInGround: Int = pts * pts
  private val groundWCS = createTerrain(nbrPointsInGround)

  if (our3dBuffer.npoints < nbrPointsInGround) {
    our3dBuffer = FxArrayOf3DPoints(nbrPointsInGround)
    our2dBuffer = FxProjectedPoints(nbrPointsInGround)
  }

  private def createTerrain(nbrPointsInGround: Int): FxArrayOf3DPoints = {
    val terrain = FxArrayOf3DPoints(nbrPointsInGround)
    var n = 0
    var x = -viewDistance
    while (x <= viewDistance) {
      var z = -viewDistance
      while (z <= viewDistance) {
        terrain.x(n) = x
        terrain.y(n) = 0
        terrain.z(n) = z
        n += 1
        z += gridSize
      }
      x += gridSize
    }
    terrain
  }

  override def paint(g: Graphics2D) {
    paintHorizon(g)
    paintGround(g)
    super.paint(g)
  }

  protected def paintHorizon(g: Graphics2D) {
    val p = FxPoint3D(0, myPosition.y, -viewDistance * 5)
    p.rotateAboutXaxis(-myAngle.x)

    val screenY = ((screenDistance * p.y / p.z) + y0).toInt
    if (screenY < 0) {
      g.setColor(if (p.z < 0) myGroundColor else mySkyColor)
      g.fillRect(0, 0, x0 << 1, y0 << 1)
    } else if (screenY > 2 * y0) {
      g.setColor(if (p.z > 0) myGroundColor else mySkyColor)
      g.fillRect(0, 0, x0 << 1, y0 << 1)
    } else {
      // figure out whether the sky is above the ground or vice-versa
      val (topColor, bottomColor) = if (p.z < 0) (mySkyColor, myGroundColor) else (myGroundColor, mySkyColor)
      g.setColor(topColor)
      g.fillRect(0, 0, x0 << 1, screenY)
      g.setColor(bottomColor)
      g.fillRect(0, screenY, x0 << 1, y0 << 1)
    }
  }

  protected def paintGround(g: Graphics2D) {
    val centerX = (myPosition.x / gridSize).toInt
    val centerZ = (myPosition.z / gridSize).toInt

    // update the matrix
    updateMatrix()

    // transform the ground points from WCS to VCS
    matrixWCStoVCS.transformWithTranslation(
      groundWCS,
      our3dBuffer,
      new FxPoint3D(centerX * gridSize, 0, centerZ * gridSize))

    // set the number of points in the cache buffer
    our3dBuffer.npoints = groundWCS.npoints

    doProjection()
    doTheChecks()

    g.setColor(new Color(0, 0, 0))
    (0 to (our2dBuffer.length - 1)) foreach { n =>
      if (our2dBuffer.clipFlags(n) == 0) {
        g.drawLine(our2dBuffer.x(n), our2dBuffer.y(n), our2dBuffer.x(n), our2dBuffer.y(n))
      }
    }
  }

}