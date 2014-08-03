package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors.ai.AbstractAI

/**
 * Represents an abstract actor
 * @param world
 * @param pos
 * @param vector
 * @param turningRate
 * @param pitchRate
 * @param acceleration
 * @param brakingRate
 * @param maxVelocity
 * @param climbRate
 * @param decentRate
 * @param pitchClimbRateFactor
 * @param health
 */
abstract class AbstractActor(world: FxWorld,
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

  var brain: Option[AbstractAI] = None

  override def die() {
    import com.ldaniels528.robowars.audio.AudioManager._

    // play the explosion clip
    audioPlayer ! BigExplosionClip

    // allow super-class to take action
    super.die()
  }

  override def update(dt: Double) {
    super.update(dt)
    brain.foreach(_.update(dt))
  }

}
