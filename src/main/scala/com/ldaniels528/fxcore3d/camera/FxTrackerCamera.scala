package com.ldaniels528.fxcore3d.camera

import com.ldaniels528.fxcore3d.{FxAngle3D, FxObject, FxPoint3D, FxWorld}

/**
 * FxEngine Tracker Camera
 * @author lawrence.daniels@gmail.com
 */
class FxTrackerCamera(world: FxWorld,
                      viewAngle: Double,
                      viewDistance: Double,
                      gridSize: Double,
                      fadingFactor: Double,
                      speedFactor: Double,
                      theObject: FxObject,
                      relAgl: FxAngle3D,
                      relPos: FxPoint3D)
  extends FxMagicCamera(world, viewAngle, viewDistance, theObject.getPosition(), theObject.getAngle(), gridSize, fadingFactor) {

  private var relativeAngle: FxAngle3D = relAgl.makeClone()
  private var relativePosition: FxPoint3D = relPos.makeClone()

  // initialize the camera
  init()

  def init(): Unit = relativeAngle.negate()

  def setRelativePosition(p: FxPoint3D): Unit = relativePosition = p.makeClone()

  def setRelativeAngle(p: FxAngle3D): Unit = relativeAngle = p.makeClone()

  def getRelativePosition(): FxPoint3D = relativePosition.makeClone()

  def getRelativeAngle(): FxAngle3D = relativeAngle.makeClone()

  override def update(dt: Double) {
    val thePointToTrack = theObject.getWorldCoordForRelativePoint(relativePosition)

    // create the vector
    val v = myPosition.vectorTo(thePointToTrack)
    v *= (speedFactor * dt)
    v += myPosition

    // create the angle
    val b = theObject.getAngle()
    b += relativeAngle

    // determine the angle between the object and myself
    val a = angleBetween(myAngle, b)
    a *= (speedFactor * dt)
    a += myAngle

    // set my new orientation
    setOrientation(v, a)
  }

  private def angleBetween(p1: FxAngle3D, p2: FxAngle3D) = FxAngle3D(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z)

}
