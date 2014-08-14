package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.fxcore3d.{FxPoint3D, FxScale3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.objects.vehicles.Glider._
import com.ldaniels528.robowars.objects.weapons.{BombLauncher, MiniCannon, MissileLauncher}

/**
 * Glider Vehicle
 * @author lawrence.daniels@gmail.com
 */
case class Glider(w: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(w, FxPoint3D(p.x, SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0)) {

  val turningRate: Double = 0.6d
  val pitchRate: Double = 0.5d
  val acceleration: Double = 3d
  val brakingRate: Double = 3d
  val maxVelocity: Double = 100d
  val climbRate: Double = 3d
  val decentRate: Double = 4d
  val pitchClimbRateFactor: Double = 1d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/glider.f3d", SCALE)

  // attach some weapons
  this += new MiniCannon(this, new FxPoint3D(0, SCALE.h, 0))
  this += new MissileLauncher(this, new FxPoint3D(0, SCALE.h, 0))
  this += new BombLauncher(this, new FxPoint3D(0, SCALE.h, 0))

  override def update(dt: Double) {
    super.update(dt)

    // check collision with ground
    if ($position.y < SCALE.h) {
      $position.y = SCALE.h

      // some damage depending on the speed
      if(damageHealth($dPosition.velocity) < 0) die()

      // update the velocity
      $dPosition.setAngleAboutAxisX(0)
      $dPosition.setVelocity(0)
    }
    ()
  }

}

/**
 * Glider Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Glider {
  val SCALE = FxScale3D(8d, 1d, 4d)

}
