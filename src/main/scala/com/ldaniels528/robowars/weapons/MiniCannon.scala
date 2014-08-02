package com.ldaniels528.robowars.weapons

import MiniCannon._
import com.ldaniels528.robowars.actors.AbstractVehicle
import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.ContentManager

/**
 * MiniCannon
 * @author lawrence.daniels@gmail.com
 */
class MiniCannon(host: AbstractVehicle, relPos: FxPoint3D)
  extends AbstractWeapon(host, relPos, loadingTime, defaultAmmo) {

  override def fire(): Boolean = {
    if (super.fire()) {
      val p = theHost.getPosition()
      p.plus(relOrigin)
      new MiniCannonRound(theHost.getWorld(), theHost, p, theHost.getAngle())
      true
    } else false
  }

}

/**
 * MiniCannon
 * @author lawrence.daniels@gmail.com
 */
object MiniCannon {
  val loadingTime: Double = 0.4d
  val defaultAmmo: Int = 250

}