package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.AbstractMovingObject
import com.ldaniels528.robowars.objects.weapons.AbstractProjectile

/**
 * Abstract Moving Structure
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingStructure(world: FxWorld,
                                       pos: FxPoint3D,
                                       agl: FxAngle3D,
                                       dpos: FxVelocityVector,
                                       dagl: FxAngle3D)
  extends AbstractMovingObject(world, pos, agl, dpos, dagl) {

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case rnd: AbstractProjectile => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case rnd: AbstractProjectile =>
        if (damageHealth(rnd.impactDamage) < 0) {
          die()
        }
        true
      case _ => super.handleCollisionWith(obj, dt)
    }
  }

}
