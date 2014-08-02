package com.ldaniels528.robowars.actors

import scala.beans.BeanProperty

import com.ldaniels528.robowars.ai.AbstractAI
import com.ldaniels528.fxcore3d._

class AbstractPlayer(
  world: FxWorld,
  pos: FxPoint3D,
  vector: FxVelocityVector,
  val turningRate: Double,
  val pitchRate: Double = 0,
  val acceleration: Double,
  val brakingRate: Double,
  val maxVelocity: Double,
  val climbRate: Double = 0,
  val decentRate: Double = 0,
  val pitchClimbRateFactor: Double = 0,
  health: Double)
  extends AbstractVehicle(world, pos, vector, turningRate, pitchRate, acceleration, brakingRate,
    maxVelocity, climbRate, decentRate, pitchClimbRateFactor, health) {

  @BeanProperty protected var brain: AbstractAI = _

  override def update(dt: Double) {
    super.update(dt)
    if (brain != null) {
      brain.update(dt);
    }
  }

}
