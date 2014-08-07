package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.structures.AbstractStaticStructure._
import com.ldaniels528.robowars.objects.weapons._

/**
 * Abstract Static Structure
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractStaticStructure(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, health: Double = Double.MaxValue)
  extends AbstractStaticObject(world, pos, agl, health) {

  override def die() {
    import com.ldaniels528.robowars.audio.AudioManager._

    // play the explosion clip
    audioPlayer ! BuildingExplodeClip

    // allow super-class to take action
    super.die()
  }

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case r: AbstractProjectile => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case r: AbstractProjectile =>
        if (damageHealth(r.impactDamage) <= 0) die()
      case _ =>
    }
    super.handleCollisionWith(obj, dt)
  }

  protected def destruct(height: Double) {
    val fragCount = (REL_FRAG_WHEN_DEAD * height).toInt
    (1 to fragCount) foreach { n =>
      new GenericFragment(world, REL_FRAG_SIZE * height,
        position, REL_FRAG_SPREAD * height,
        (height * REL_FRAG_GENERATIONS).toInt, height * REL_FRAG_SPEED, height * REL_FRAG_ROTATION)
    }
  }

  protected def destruct(height: Double, carcass: (FxWorld, Double, Double, FxAngle3D, Double, Double, Double) => FxObject) {
    destruct(height)
    // TODO
  }

}

/**
 * Abstract Static Structure Companion Object
 * @author lawrence.daniels@gmail.com
 */
object AbstractStaticStructure {
  val REL_FRAG_SIZE: Double = 0.6d
  val REL_FRAG_SPEED: Double = 2d
  val REL_FRAG_SPREAD: Double = 1d
  val REL_FRAG_WHEN_DEAD: Double = 1d
  val REL_FRAG_GENERATIONS: Double = 0.01d
  val REL_FRAG_ROTATION: Double = 0.2d

}