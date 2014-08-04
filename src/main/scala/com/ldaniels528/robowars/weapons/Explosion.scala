package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d._

/**
 * Explosion
 * @author lawrence.daniels@gmail.com
 */
class Explosion(world: FxWorld, strength: Double)
  extends AbstractRound(world, null, new FxPoint3D(), FxVelocityVector(0, 0, 0), strength) {

  val maxVelocity = 30d

}
