package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.events.WeaponCommand
import com.ldaniels528.robowars.objects.vehicles.AntiTankCannon._
import com.ldaniels528.robowars.objects.structures.GenericFragment
import com.ldaniels528.robowars.objects.weapons.{AbstractProjectile, MiniCannon}

/**
 * Anti-Tank Cannon
 * @author lawrence.daniels@gmail.com
 */
class AntiTankCannon(world: FxWorld, p: FxPoint3D)
  extends AbstractVehicle(world, FxPoint3D(p.x, p.y + SCALE.y, p.z), FxVelocityVector(Math.PI, 0, 0), INITIAL_HEALTH) {

  val turningRate: Double = 4d
  val pitchRate: Double = 0
  val acceleration: Double = 0
  val brakingRate: Double = 0
  val maxVelocity: Double = 0
  val climbRate: Double = 0
  val decentRate: Double = 0
  val pitchClimbRateFactor: Double = 0

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, SCALE)

  // attach some weapons
  this += new MiniCannon(this, FxPoint3D())
  selectWeapon(0)

  override def die() {
    super.die()
    (1 to FRAGMENTS_WHEN_DEAD) foreach { n =>
      new GenericFragment(world, FRAGMENT_SIZE, position, FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case r: AbstractProjectile =>
        if (r.shooter == this) true
        else {
          die()
          false
        }
      case _ => super.handleCollisionWith(obj, dt)
    }
  }

  override protected def handleEvent(event: FxEvent): Boolean = {
    event match {
      case WeaponCommand(_, command, _) =>
        if (command == FIRE) selectedWeapon.fire
        true
      case _ => super.handleEvent(event)
    }
  }

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case r: AbstractProjectile => true
      case v: AbstractVehicle => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

}

/**
 * Anti-Tank Cannon (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object AntiTankCannon {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/actors/antitank1.f3d")
  val INITIAL_HEALTH: Double = 6d
  val SCALE = FxPoint3D(1d, 1d, 2d)

  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 25d
  val FRAGMENT_SPREAD: Double = 2d
  val FRAGMENTS_WHEN_DEAD: Int = 10
  val FRAGMENT_GENERATIONS: Int = 1

}
