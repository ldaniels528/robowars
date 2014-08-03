package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._

/**
 * Abstract Static Object
 * @author lawrence.daniels@gmail.com
 */
class AbstractStaticObject(world: FxWorld,
                           pos: FxPoint3D,
                           agl: FxAngle3D,
                           var health: Double = Double.MaxValue)
  extends FxObject(world, pos, agl) {

  def damageHealth(delta: Double): Double = {
    health -= delta
    health
  }

}
