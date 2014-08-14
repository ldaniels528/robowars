package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.fxcore3d.{FxAngle3D, FxMovingObject, FxScale3D, FxWorld}
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.vehicles.VehicleRuins._

/**
 * Remains of a Vehicle
 * @author lawrence.daniels@gmail.com
 */
class VehicleRuins(world: FxWorld, deadTank: AbstractVehicle)
  extends FxMovingObject(world, deadTank.position, deadTank.angle, deadTank.dPosition, deadTank.dAngle) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/vehicleRuins.f3d", SCALE)

  // throw the remaining tank up in the air
  $dPosition.y = FxWorld.rand(0, SPEED_UP)

  // set a random rotation on the remaining tank
  dAngle = FxWorld.random3DAngle(ROTATION)

  override def update(dt: Double) {
    super.update(dt)

    // -- check if hit the ground
    if ($position.y < SCALE.h) {
      $position.y = SCALE.h
      $dPosition.reset
      audioPlayer ! CrashClip

      $angle.set(0, $angle.y, 0)
      dAngle = FxAngle3D()
    }

    // apply the effects of gravity
    else if ($position.y > SCALE.h) applyGravity(dt)
  }
}

/**
 * VehicleRuins Companion Object
 * @author lawrence.daniels@gmail.com
 */
object VehicleRuins {
  val SCALE = new FxScale3D(1.5d, 0.23d, 2d)
  val SPEED_UP: Double = 20d
  val ROTATION: Double = 3d

}