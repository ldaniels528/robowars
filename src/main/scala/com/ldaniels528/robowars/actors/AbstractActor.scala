package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors.ai.AbstractAI

/**
 * Represents an abstract actor
 * @param world
 * @param pos
 * @param vector
 */
abstract class AbstractActor(world: FxWorld, pos: FxPoint3D, vector: FxVelocityVector, health: Double)
  extends AbstractVehicle(world, pos, vector, health) {

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
