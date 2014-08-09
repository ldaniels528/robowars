package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.events.{Events, SteeringCommand}
import com.ldaniels528.robowars.objects.AbstractMovingObject
import com.ldaniels528.robowars.objects.structures.{AbstractMovingStructure, AbstractStaticStructure, GenericFragment}
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import com.ldaniels528.robowars.objects.weapons.AbstractProjectile._

/**
 * Represents a projectile (i.e. bullet, missile, etc.)
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractProjectile(world: FxWorld, val shooter: AbstractVehicle, pos0: FxPoint3D, vector0: FxVelocityVector, val impactDamage: Double, lifeTime: Double = 4d)
  extends AbstractMovingObject(world, pos0, vector0.angle, vector0, FxAngle3D(), initialHealth = 0) with Events {
  protected var deadOfAge: Boolean = _
  protected var dieNextUpdate: Boolean = _

  override def die() {
    super.die()

    if (!deadOfAge) {
      (1 to FRAGMENTS_WHEN_DEAD) foreach { n =>
        new GenericFragment(
          world, FRAGMENT_SIZE, position,
          FRAGMENT_SPREAD, FRAGMENT_GENERATIONS, FRAGMENT_SPEED, 3)
      }
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double) = {
    if (obj != shooter) {
      die()
      false
    } else true
  }

  override def interestedOfCollisionWith(obj: FxObject) = {
    obj match {
      case p: AbstractProjectile => p.shooter != shooter
      case m: AbstractMovingStructure => true
      case s: AbstractStaticStructure => true
      case v: AbstractVehicle => shooter != v
      case _ => false
    }
  }

  def increaseVelocity(factor: Double, dt: Double) {
    this += SteeringCommand(world.time, INCREASE_VELOCITY, factor, dt)
  }

  override def update(dt: Double) {
    super.update(dt)

    // -- check if it is time to die
    if (dieNextUpdate) die()
    else {
      // -- if bullet hits the ground then die
      // -- next round so that a collision detection
      // -- can be done
      val p = position
      if (p.y < 0) {
        p.y = 0
        setPosition(p)
        dieNextUpdate = true
      }

      // -- if life is out
      if (age > lifeTime) {
        deadOfAge = true
        die()
      }
    }
  }

}

/**
 * Abstract Projectile Companion Object
 * @author lawrence.daniels@gmail.com
 */
object AbstractProjectile {
  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 7d
  val FRAGMENT_SPREAD: Double = 1d
  val FRAGMENTS_WHEN_DEAD: Int = 15
  val FRAGMENT_GENERATIONS: Int = 1

}

