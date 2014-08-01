package org.combat.fxcore3d.camera

import org.combat.fxcore3d.FxAngle3D
import org.combat.fxcore3d.FxObject
import org.combat.fxcore3d.FxPoint3D
import org.combat.fxcore3d.FxWorld

/**
 * FxEngine Tracker Camera
 * @author lawrence.daniels@gmail.com
 */
class FxTrackerCamera(
  world: FxWorld,
  viewAngle: Double,
  viewDistance: Double,
  gridSize: Double,
  fadingFactor: Double,
  speedFactor: Double,
  theObject: FxObject,
  relAgl: FxAngle3D,
  relPos: FxPoint3D)
  extends FxMagicCamera(world, viewAngle, viewDistance, theObject.getPosition(), theObject.getAngle(), gridSize, fadingFactor) {

  private var relagl: FxAngle3D = relAgl.makeClone()
  private var relpos: FxPoint3D = relPos.makeClone()

  // initialize the camera
  init()

  def init(): Unit = relagl.negate()

  def setRelativePosition(p: FxPoint3D): Unit = relpos = p.makeClone()

  def setRelativeAngle(p: FxAngle3D): Unit = relagl = p.makeClone()

  def getRelativePosition(): FxPoint3D = relpos.makeClone()

  def getRelativeAngle(): FxAngle3D = relagl.makeClone()

  override def update(dt: Double) {
    val thePointToTrack = theObject.getWorldCoordForRelativePoint(relpos)

    val v = myPosition.vectorTo(thePointToTrack)
    v.times(speedFactor * dt)
    v.plus(myPosition)

    val b = theObject.getAngle()
    b.plus(relagl)

    val a = angleBetween(myAngle, b)
    a.times(speedFactor * dt)
    a.plus(myAngle)

    setOrientation(v, a)
  }

  private def angleBetween(p1: FxAngle3D, p2: FxAngle3D): FxAngle3D = {
    val x = p2.x - p1.x
    val y = p2.y - p1.y
    val z = p2.z - p1.z
    new FxAngle3D(x, y, z)
  }

}
