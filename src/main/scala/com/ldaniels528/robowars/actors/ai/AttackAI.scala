package com.ldaniels528.robowars.actors.ai

import com.ldaniels528.fxcore3d.FxObject
import com.ldaniels528.robowars.actors.AbstractActor

/**
 * Represents an aggressive artificial intelligence
 * @param host the host [[AbstractActor]]
 * @author lawrence.daniels@gmail.com
 */
class AttackAI(host: AbstractActor) extends MotionAI(host) {
  private var myTarget: Option[FxObject] = None

  def selectTarget(target: FxObject) {
    this.myTarget = Some(target)
  }

  override def update(dt: Double) {
    super.update(dt)
    for {
      target <- myTarget
      distanceToTarget = target.distanceToPoint(host.position)
    } {
      if (target.isAlive && Math.abs(turnTowardsPoint(target.position, dt)) < 0.2) {
        if (distanceToTarget <= 50) {
          host.fireSelectedWeapon()
        } else {
          moveTowardsPoint(target.position, dt)
        }
      }
    }
    ()
  }

}
