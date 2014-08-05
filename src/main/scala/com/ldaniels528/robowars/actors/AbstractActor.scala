package com.ldaniels528.robowars.actors

import AbstractActor._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors.ai.AbstractAI
import com.ldaniels528.robowars.structures.GenericFragment

/**
 * Represents an abstract actor
 * @param world the given [[FxWorld]]
 * @param pos the initial position of the actor
 * @param vector the actor's current [[FxVelocityVector]]
 * @param health the actor's initial health
 * @author lawrence.daniels@gmail.com
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

/**
 * Abstract Actor Companion Object
 * @author lawrence.daniels@gmail.com
 */
object AbstractActor {
  val FRAGMENT_SIZE: Double = 0.25d
  val FRAGMENT_SPEED: Double = 25d
  val FRAGMENT_SPREAD: Double = 2d
  val FRAGMENTS_WHEN_DEAD: Int = 10
  val FRAGMENT_GENERATIONS: Int = 1
}
