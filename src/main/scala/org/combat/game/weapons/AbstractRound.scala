package org.combat.game.weapons

import org.combat.fxcore3d._
import org.combat.game.actors.AbstractVehicle
import scala.beans.BeanProperty

/**
 * Abstract Round
 * @author lawrence.daniels@gmail.com
 */
class AbstractRound(
  world: FxWorld,
  @BeanProperty val shooter: FxObject,
  pos: FxPoint3D,
  vector: FxVelocityVector,
  turningRate: Double,
  pitchRate: Double,
  acceleration: Double,
  brakingRate: Double,
  maxVelocity: Double,
  climbRate: Double,
  decentRate: Double,
  pitchClimbRateRelation: Double,
  @BeanProperty val impactDamage: Double)
  extends AbstractVehicle(world, pos, vector, turningRate, pitchRate, acceleration, brakingRate,
    maxVelocity, climbRate, decentRate, pitchClimbRateRelation, Double.MaxValue) {

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    if (obj != shooter) {
      die()
      false
    } else true
  }

}

