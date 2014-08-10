package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.weapons.AbstractProjectile
import com.ldaniels528.robowars.objects.{Damageable, Destructible}

/**
 * Represents a [[Structure]] that can be destroyed
 * @author lawrence.daniels@gmail.com
 */
abstract class DestructibleStructure(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, initialHealth: Double)
  extends FxObject(world, pos, agl)
  with Structure
  with Damageable
  with Destructible {

  def maxHealth: Double = initialHealth

  def scale: FxScale3D

  override def die() {
    // play the explosion clip
    audioPlayer ! BuildingExplodeClip

    // leave ruins behind
    new BuildingRuin(world, position, angle, scale.reducedHeight(0.2d))

    // create the explosion
    val height = scale.h
    explodeIntoFragments(
      fragments = height.toInt,
      size = height * 0.6,
      speed = height * 2d,
      spread = height,
      rotation = height * 0.2,
      generations = (height * 0.01d).toInt)

    // allow super-class to take action
    super.die()
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case r: AbstractProjectile =>
        if (damageHealth(r.impactDamage) <= 0) die() //else audioPlayer ! GunImpact
      case _ =>
    }
    super.handleCollisionWith(obj, dt)
  }

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case r: AbstractProjectile => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

}
