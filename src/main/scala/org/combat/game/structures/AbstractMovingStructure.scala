package org.combat.game.structures

import org.combat.fxcore3d._
import org.combat.game.AbstractMovingObject
import org.combat.game.weapons.AbstractRound

/**
 * Abstract Moving Structure
 * @author lawrence.daniels@gmail.com
 */
class AbstractMovingStructure(
  theWorld: FxWorld,
  pos: FxPoint3D,
  agl: FxAngle3D,
  dpos: FxVelocityVector,
  dagl: FxAngle3D,
  myHealth: Double = Double.MaxValue)
  extends AbstractMovingObject(theWorld, pos, agl, dpos, dagl, myHealth) {

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case rnd: AbstractRound => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case rnd: AbstractRound =>
        if (damageHealth(rnd.getImpactDamage()) < 0) {
          die()
        }
        true
      case _ => super.handleCollisionWith(obj, dt)
    }
  }

}
