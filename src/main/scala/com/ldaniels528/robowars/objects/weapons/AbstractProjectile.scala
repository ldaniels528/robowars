package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.events.EventCommands._
import com.ldaniels528.robowars.events.SteeringCommand
import com.ldaniels528.robowars.objects.Destructible
import com.ldaniels528.robowars.objects.structures.Structure
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a projectile (i.e. bullet, missile, etc.)
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractProjectile(world: FxWorld, val shooter: AbstractVehicle, pos0: FxPoint3D, vector0: FxVelocityVector, val impactDamage: Double, lifeTime: Double = 4d)
  extends FxMovingObject(world, pos0, vector0.angle, vector0, FxAngle3D())
  with Destructible {

  protected var deadOfAge: Boolean = _
  protected var dieNextUpdate: Boolean = _

  override def die() {
    if (!deadOfAge) {
      // play the audio clip
      deathClip.foreach(audioPlayer ! _)

      // spread the fragments
      explodeIntoFragments(
        fragments = (2.5 * impactDamage).toInt,
        size = 0.25,
        speed = 7,
        spread = 1,
        rotation = 3)
    }
    super.die()
  }

  def deathClip: Option[AudioKey] = None

  override def handleCollisionWith(obj: FxObject, dt: Double) = {
    if (obj != shooter) {
      die()
      false
    } else true
  }

  override def interestedOfCollisionWith(obj: FxObject) = {
    obj match {
      case p: AbstractProjectile => p.shooter != shooter
      case s: Structure => true
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
      if ($position.y < 0) {
        $position.y = 0
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
