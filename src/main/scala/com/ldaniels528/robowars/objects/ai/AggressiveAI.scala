package com.ldaniels528.robowars.objects.ai

import com.ldaniels528.fxcore3d.FxObject
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents an aggressive artificial intelligence
 * @param host the host [[AbstractVehicle]]
 * @author lawrence.daniels@gmail.com
 */
class AggressiveAI(host: AbstractVehicle) extends PassiveAI(host) {
  private var myTarget: Option[FxObject] = None

  def selectTarget(target: FxObject) {
    this.myTarget = Some(target)
  }

  override def update(dt: Double) {
    super.update(dt)

    // if current weapon is empty, switch
    if(host.selectedWeapon.ammo == 0) {
      host.switchWeapons()
    }

    // pursue the target
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
