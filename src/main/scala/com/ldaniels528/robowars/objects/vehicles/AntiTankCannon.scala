package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.vehicles.AntiTankCannon._
import com.ldaniels528.robowars.objects.weapons.MissileLauncher

/**
 * Anti-Tank Cannon
 * @author lawrence.daniels@gmail.com
 */
case class AntiTankCannon(w: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(w, FxPoint3D(p.x, SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0), initialHealth = 10d) {

  val turningRate: Double = 4d
  val pitchRate: Double = 0
  val acceleration: Double = 0
  val brakingRate: Double = 0
  val maxVelocity: Double = 0
  val climbRate: Double = 0
  val decentRate: Double = 0
  val pitchClimbRateFactor: Double = 0

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/vehicles/antitank1.f3d", SCALE)

  // attach some weapons
  this += MissileLauncher(this, FxPoint3D(0, p.y + SCALE.h, 0), ammo0 = Int.MaxValue)

}

/**
 * Anti-Tank Cannon (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object AntiTankCannon {
  val SCALE = FxScale3D(1d, 1d, 2d)

}
