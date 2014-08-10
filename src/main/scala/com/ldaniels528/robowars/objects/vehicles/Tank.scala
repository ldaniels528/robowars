package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.vehicles.Tank._
import com.ldaniels528.robowars.objects.weapons._

/**
 * Generic Tank
 * @author lawrence.daniels@gmail.com
 */
class Tank(world: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(world, FxPoint3D(p.x, p.y + SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0)) {

  val turningRate: Double = 1.25d
  val pitchRate: Double = 0
  val acceleration: Double = 3d
  val brakingRate: Double = 10d
  val maxVelocity: Double = 20d
  val climbRate: Double = 0
  val decentRate: Double = 0
  val pitchClimbRateFactor: Double = 0

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/actors/tank2.f3d", SCALE)

  // attach some weapons
  this += new MiniCannon(this, new FxPoint3D(0, p.y + SCALE.h, 0))
  this += new MissileLauncher(this, new FxPoint3D(0, p.y + SCALE.h, 0))

}

/**
 * Tank Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Tank {
  val SCALE = new FxScale3D(1.50d, 0.75d, 2.00d)

}
