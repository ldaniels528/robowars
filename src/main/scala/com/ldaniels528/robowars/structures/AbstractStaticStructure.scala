package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.weapons._

/**
 * Abstract Static Structure
 * @author lawrence.daniels@gmail.com
 */
class AbstractStaticStructure(world: FxWorld, x: Double, y: Double, z: Double, agl: FxAngle3D, health: Double = Double.MaxValue)
  extends AbstractStaticObject(world, new FxPoint3D(x, y, z), agl, health) {

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case r: AbstractRound => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    obj match {
      case r: AbstractRound => 
        if (damageHealth(r.getImpactDamage()) <= 0) die()
      case _ => 
    }
    super.handleCollisionWith(obj, dt)
  }

}
