package com.ldaniels528.fxcore3d.camera

import com.ldaniels528.fxcore3d.{FxAngle3D, FxObject, FxPoint3D, FxWorld}

/**
 * FxEngine Tracker Camera
 * @author lawrence.daniels@gmail.com
 */
class FxTrackerCamera(world: FxWorld, viewAngle: Double, viewDistance: Double, gridSize: Double, speedFactor: Double,
                      host: FxObject, relAgl: FxAngle3D, relPos: FxPoint3D)
  extends FxSceneCamera(world, viewAngle, viewDistance, host.position, host.angle, gridSize) {

  private val relativeAngle: FxAngle3D = relAgl
  private val relativePosition: FxPoint3D = relPos

  // initialize the camera
  relativeAngle.negate()

  /**
   * Returns the world coordinate for a position relative an object.
   */
  private def getWorldCoordinateOfRelativePoint(obj: FxObject, relPos: FxPoint3D): FxPoint3D = {
    obj.modelInstance.transformPoint(relPos, FxPoint3D())
  }

  override def update(dt: Double) {
    // compute the point to track
    val thePointToTrack = getWorldCoordinateOfRelativePoint(host, relativePosition)

    // create the vector
    val v = myPosition.vectorTo(thePointToTrack) *= (speedFactor * dt) += myPosition

    // create the angle
    val b = host.angle += relativeAngle

    // determine the angle between the object and myself
    val a = angleBetween(myAngle, b) *= (speedFactor * dt) += myAngle

    // set my new orientation
    setOrientation(v, a)
  }

  /**
   * Computes the angle between the two angles
   * @param a1 the first of two angles
   * @param a2 the second of two angles
   * @return the bisecting angle
   */
  private def angleBetween(a1: FxAngle3D, a2: FxAngle3D) = FxAngle3D(a2.x - a1.x, a2.y - a1.y, a2.z - a1.z)

}
