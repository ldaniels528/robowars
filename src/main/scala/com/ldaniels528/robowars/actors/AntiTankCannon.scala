package com.ldaniels528.robowars.actors

import AntiTankCannon._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.events.EventWeaponCommand
import com.ldaniels528.robowars.structures.GenericFragment
import com.ldaniels528.robowars.weapons.AbstractRound
import com.ldaniels528.robowars.weapons.MiniCannon

/**
 * Anti-Tank Cannon
 * @author lawrence.daniels@gmail.com
 */
class AntiTankCannon(world: FxWorld, p: FxPoint3D)
  extends AbstractPlayer(
    world,
    new FxPoint3D(p.x, p.y + SCALE.y, p.z),
    FxVelocityVector(Math.PI, 0, 0),
    TURNING_RATE, pitchRate = 0, acceleration = 0, brakingRate = 0, maxVelocity = 0,
    climbRate = 0, decentRate = 0, pitchClimbRateFactor = 0, INITIAL_HEALTH) {

  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, SCALE))
  addWeapon(new MiniCannon(this, new FxPoint3D()))
  selectWeapon(0)

  override def die() {
    super.die()
    for (n <- 1 to FRAGMENTS_WHEN_DEAD) {
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
      case wc: EventWeaponCommand =>
        if (wc.command == EventWeaponCommand.FIRE) selectedWeapon.fire
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
  val SCALE = new FxPoint3D(1d, 1d, 2d)
  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 25d
  val FRAGMENT_SPREAD: Double = 2d
  val FRAGMENTS_WHEN_DEAD: Int = 10
  val FRAGMENT_GENERATIONS: Int = 1

}
