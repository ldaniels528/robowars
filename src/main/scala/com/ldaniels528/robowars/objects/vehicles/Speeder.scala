package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxModelInstance}
import com.ldaniels528.fxcore3d.{FxPoint3D, FxScale3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.Speeder._
import com.ldaniels528.robowars.objects.weapons.{MachineGun, MiniCannon}

/**
 * Speeder Vehicle
 * @author lawrence.daniels@gmail.com
 */
case class Speeder(w: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(w, FxPoint3D(p.x, SCALE.h, p.z), FxVelocityVector(Math.PI, 0, 0)) {

  val turningRate: Double = 1.25d
  val pitchRate: Double = 0
  val acceleration: Double = 3d
  val brakingRate: Double = 20d
  val maxVelocity: Double = 60d
  val climbRate: Double = 0
  val decentRate: Double = 0
  val pitchClimbRateFactor: Double = 0

  // set the default polyhedron instance
  lazy val modelInstance = new FxModelInstance(MODEL, SCALE)

  // attach some weapons
  this += MachineGun(this, FxPoint3D(0, p.y + SCALE.h, 0))
  this += MiniCannon(this, FxPoint3D(0, SCALE.h, 0))

}

/**
 * Speeder Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Speeder {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/vehicles/speeder.f3d")
  val SCALE = FxScale3D(w = 1.50d, h = 0.75d, d = 2.00d)

}
