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
  extends FxSceneCamera(world, viewAngle, viewDistance, theObject.getPosition(), theObject.getAngle(), gridSize, fadingFactor) {

  private var relativeAngle: FxAngle3D = relAgl.copy()
  private var relativePosition: FxPoint3D = relPos.copy()

  // initialize the camera
  relativeAngle.negate()

  def getRelativeAngle(): FxAngle3D = relativeAngle.copy()

  def setRelativeAngle(a: FxAngle3D): Unit = relativeAngle = a.copy()

  def getRelativePosition(): FxPoint3D = relativePosition.copy()

  def setRelativePosition(p: FxPoint3D): Unit = relativePosition = p.copy()

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
