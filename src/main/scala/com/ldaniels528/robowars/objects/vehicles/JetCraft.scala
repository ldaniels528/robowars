package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.fxcore3d.{FxPoint3D, FxScale3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.objects.vehicles.JetCraft._
import com.ldaniels528.robowars.objects.weapons.{BombLauncher, MiniCannon, MissileLauncher}

/**
 * Jet Fighter
 * @author ldaniels
 */
case class JetCraft(w: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(w, FxPoint3D(p.x, SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0)) {

  val turningRate: Double = 0.6d
  val pitchRate: Double = 0.5d
  val acceleration: Double = 3d
  val brakingRate: Double = 3d
  val maxVelocity: Double = 150d
  val climbRate: Double = 3d
  val decentRate: Double = 4d
  val pitchClimbRateFactor: Double = 1d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/bomber.f3d", SCALE)

  // -- add the weapons
  this += new MiniCannon(this, FxPoint3D(0, SCALE.h, 0))
  this += new MissileLauncher(this, FxPoint3D(0, SCALE.h, 0))
  this += new BombLauncher(this, FxPoint3D(0, SCALE.h, 0))

  override def update(dt: Double) {
    super.update(dt)

    // check collision with ground
    if ($position.y < SCALE.h) {
      $position.y = SCALE.h

      // update the velocity
      $dPosition.setAngleAboutAxisX(0)
      $dPosition.setVelocity(0)
    }
    ()
  }

}

/**
 * JetCraft Companion Object
 * @author lawrence.daniels@gmail.com
 */
object JetCraft {
  val SCALE = FxScale3D(8d, 1d, 4d)

}
