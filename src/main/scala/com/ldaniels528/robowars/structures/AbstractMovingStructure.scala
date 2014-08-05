package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.AbstractMovingObject
import com.ldaniels528.robowars.weapons.AbstractRound

/**
 * Abstract Moving Structure
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingStructure(theWorld: FxWorld,
                                       pos: FxPoint3D,
                                       agl: FxAngle3D,
                                       dpos: FxVelocityVector,
                                       dagl: FxAngle3D)
  extends AbstractMovingObject(theWorld, pos, agl, dpos, dagl) {

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case rnd: AbstractRound => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case rnd: AbstractRound =>
        if (damageHealth(rnd.impactDamage) < 0) {
          die()
        }
        true
      case _ => super.handleCollisionWith(obj, dt)
    }
  }

}
