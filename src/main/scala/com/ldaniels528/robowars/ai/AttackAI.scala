package com.ldaniels528.robowars.ai

import AttackAI._
import com.ldaniels528.fxcore3d.FxObject
import com.ldaniels528.robowars.actors.AbstractPlayer

class AttackAI(host: AbstractPlayer) extends MotionAI(host) {
  private var myTarget: Option[FxObject] = None
  state = STANDBY

  def selectTarget(target: FxObject) {
    this.myTarget = Some(target)
  }

  def attackTarget() {
    state = ATTACKING
  }

  protected def distanceToTarget(): Double = {
    myTarget map (_.distanceToPoint(host.getPosition())) getOrElse -1
  }

  override def update(dt: Double) {
    super.update(dt)
    if (state == ATTACKING) {
      myTarget foreach { target =>
        if (Math.abs(turnTowardsPoint(target.getPosition(), dt)) < 0.2) {
          if (distanceToTarget() < 30) {
            host.fireSelectedWeapon()
          } else {
            moveTowardsPoint(target.getPosition(), dt)
          }
        }
      }
    }
    ()
  }

  override def abortJob() {
    super.abortJob()
    this.state = STANDBY
  }

}

object AttackAI {
  val STANDBY = 1
  val ATTACKING = 2

}