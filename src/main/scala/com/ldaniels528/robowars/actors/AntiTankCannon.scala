package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.actors.AntiTankCannon._
import com.ldaniels528.robowars.events.WeaponCommand
import com.ldaniels528.robowars.structures.GenericFragment
import com.ldaniels528.robowars.weapons.{AbstractRound, MiniCannon}

/**
 * Anti-Tank Cannon
 * @author lawrence.daniels@gmail.com
 */
class AntiTankCannon(world: FxWorld, p: FxPoint3D)
  extends AbstractActor(
    world,
    FxPoint3D(p.x, p.y + SCALE.y, p.z),
    FxVelocityVector(Math.PI, 0, 0),
    TURNING_RATE, pitchRate = 0, acceleration = 0, brakingRate = 0, maxVelocity = 0,
    climbRate = 0, decentRate = 0, pitchClimbRateFactor = 0, INITIAL_HEALTH) {

  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, SCALE))
  this += new MiniCannon(this,  FxPoint3D())
  selectWeapon(0)

  override def die() {
    super.die()
    (1 to FRAGMENTS_WHEN_DEAD) foreach { n =>
      new GenericFragment(world, FRAGMENT_SIZE, getPosition(), FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case r: AbstractRound =>
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
      case r: AbstractRound => true
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
  val TURNING_RATE: Double = 4d
  val SCALE = FxPoint3D(1d, 1d, 2d)
  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 25d
  val FRAGMENT_SPREAD: Double = 2d
  val FRAGMENTS_WHEN_DEAD: Int = 10
  val FRAGMENT_GENERATIONS: Int = 1

}
