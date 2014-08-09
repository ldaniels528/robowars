package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxModelInstance}
import com.ldaniels528.fxcore3d.{FxScale3D, FxPoint3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.Rover._
import com.ldaniels528.robowars.objects.weapons.{MachineGun, MiniCannon, MissileLauncher}

/**
 * Rover Vehicle
 * @author lawrence.daniels@gmail.com
 */
case class Rover(w: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(w, FxPoint3D(p.x, p.y + SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0), health = 5d) {

  val turningRate: Double = 1.25d
  val pitchRate: Double = 0
  val acceleration: Double = 3d
  val brakingRate: Double = 10d
  val maxVelocity: Double = 20d
  val climbRate: Double = 0
  val decentRate: Double = 0
  val pitchClimbRateFactor: Double = 0

  // set the default polyhedron instance
  lazy val modelInstance = new FxModelInstance(MODEL, SCALE)

  // attach some weapons
  this += MachineGun(this, FxPoint3D(0, p.y + SCALE.h, 0))
  this += MiniCannon(this, FxPoint3D(0, SCALE.h, 0))
  this += MissileLauncher(this, FxPoint3D(0, SCALE.h, 0))

}

/**
 * Rover Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Rover {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/fesseTank.f3d")
  val SCALE = FxScale3D(1.50d, 0.75d, 2.00d)

}
