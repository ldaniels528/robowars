package com.ldaniels528.robowars.ai

import com.ldaniels528.fxcore3d.{FxEvent, FxPoint3D}
import com.ldaniels528.robowars.actors.AbstractActor
import com.ldaniels528.robowars.ai.MotionAI._
import com.ldaniels528.robowars.events.{Events, EventMotionCommand}

/**
 * Represents a non-aggressive autonomous artificial intelligence
 * @param host the host [[AbstractActor a c t o r]]
 */
class MotionAI(host: AbstractActor) extends AbstractAI(host) with Events {
  protected var destination: Option[FxPoint3D] = None
  protected var maxError: Double = 0
  protected var state: Int = STANDBY

  override def handleEvent(event: FxEvent): Boolean = {
    event match {
      case ev: EventMotionCommand =>
        state = ADJUSTING_HEIGHT
        destination = Some(ev.dest)
        maxError = ev.precision
        true
      case _ => super.handleEvent(event)
    }
  }

  override def update(dt: Double) {
    super.update(dt)

    destination foreach { dest =>
      if (state == ADJUSTING_HEIGHT) {
        val error1 = turnTowardsPoint(dest, dt)
        val error2 = climbTowardsPoint(dest, dt)
        if ((error1 < maxError) && (error2 < maxError)) {
          state = FLYING
        }
      } else if (state == FLYING) {
        turnTowardsPoint(dest, dt)
        moveTowardsPoint(dest, dt)
      }
    }
  }

  protected def climbTowardsPoint(dest: FxPoint3D, dt: Double): Double = {
    destination map { dest =>
      val error = dest.y - host.getPosition().y
      // -- check if I should climb or decent
      if (error > 0) {
        host.climb(Math.min(1, Math.abs(error / host.climbRate)), dt)
      } else {
        host.decent(Math.min(1, Math.abs(error / host.decentRate)),
          dt)
      }
      error
    } getOrElse 0
  }

  protected def turnTowardsPoint(dest: FxPoint3D, dt: Double): Double = {
    val error = getDeterminant(dest)
    if (error < 0) {
      host.turnRight(
        Math.min(1, Math.abs(error / host.turningRate)), dt)
    } else {
      host.turnLeft(
        Math.min(1, Math.abs(error / host.turningRate)), dt)
    }
    error
  }

  protected def moveTowardsPoint(ref: FxPoint3D, dt: Double): Double = {
    host.increaseVelocity(1, dt)
    val v = host.getPosition().vectorTo(ref)
    v.magnitude()
  }

  def gotoPosition(pos: FxPoint3D, error: Double) {
    addEvent( EventMotionCommand(theWorld.time, getUniqueId(), GOTO_POSITION, pos, error))
    ()
  }

  private def getDeterminant(dest: FxPoint3D): Double = {
    // -- make vector from host position to destination
    val v1 = host.getPosition().vectorTo(dest)

    // -- make vector for host's direction
    val v2 = new FxPoint3D(0, 0, -1)
    v2.rotateAboutYaxis(host.getAngle().y)

    // -- check if destination is in front of the host
    if (v1.dotProduct(v2) > 0) {
      // -- destination in front of the host
      v1.normalize(1)
      return v1.x * v2.z - v2.x * v1.z
    } else {
      // -- destination behind host
      val temp = v1.x * v2.z - v2.x * v1.z
      if (temp < 0) -1 else 1
    }
  }

  override def abortJob() {
    super.abortJob()
    this.state = STANDBY
  }

}

object MotionAI {
  val STANDBY = 1
  val ADJUSTING_HEIGHT = 2
  val ADJUSTING_ANGLE = 3
  val HOVERING = 4
  val FLYING = 5

}