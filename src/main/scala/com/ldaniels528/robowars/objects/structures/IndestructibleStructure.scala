package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d.{FxAngle3D, FxObject, FxPoint3D, FxWorld}
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.weapons.AbstractProjectile

/**
 * Represents a [[Structure]] that cannot be destroyed
 * @author lawrence.daniels@gmail.com
 */
abstract class IndestructibleStructure(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends FxObject(world, pos, agl)
  with Structure {

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case r: AbstractProjectile =>
        audioPlayer ! GunImpact
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
