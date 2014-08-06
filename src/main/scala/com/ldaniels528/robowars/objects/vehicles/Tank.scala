package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.vehicles.Tank._
import com.ldaniels528.robowars.objects.structures.GenericFragment
import com.ldaniels528.robowars.objects.weapons._

/**
 * Generic Tank
 * @author lawrence.daniels@gmail.com
 */
class Tank(world: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(world, FxPoint3D(p.x, p.y + SCALE.y, p.z), FxVelocityVector(Math.PI, 0, 0), INITIAL_HEALTH) {

  val turningRate: Double = 1.25d
  val pitchRate: Double = 0
  val acceleration: Double = 3d
  val brakingRate: Double = 10d
  val maxVelocity: Double = 20d
  val climbRate: Double = 0
  val decentRate: Double = 0
  val pitchClimbRateFactor: Double = 0

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

  // attach some weapons
  this += new MiniCannon(this, new FxPoint3D(0, p.y + SCALE.y, 0))
  this += new MissileLauncher(this, new FxPoint3D(0, p.y + SCALE.y, 0))
  selectWeapon(0)

  override def die() {
    super.die()
    (1 to FRAGMENTS_WHEN_DEAD) foreach { n =>
      new GenericFragment(world, FRAGMENT_SIZE, position,
        FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
    }
    new FesseTankRemains(world, this)
    ()
  }

}

/**
 * Tank Companion Object
 * @author lawrence.daniels@gmail.com
 */
object Tank {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/tank2.f3d")
  val SCALE = new FxPoint3D(1.50d, 0.75d, 2.00d)
  val INITIAL_HEALTH: Double = 5d

  val MAX_VELOCITY: Double = 20d
  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 25d
  val FRAGMENT_SPREAD: Double = 2d
  val FRAGMENTS_WHEN_DEAD: Int = 15
  val FRAGMENT_GENERATIONS: Int = 1

}
