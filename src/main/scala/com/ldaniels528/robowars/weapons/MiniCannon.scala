package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.actors.AbstractVehicle
import com.ldaniels528.robowars.weapons.MiniCannon._

/**
 * MiniCannon
 * @author lawrence.daniels@gmail.com
 */
case class MiniCannon(host: AbstractVehicle, relPos: FxPoint3D) extends AbstractWeapon(host, relPos, LOADING_TIME, INITIAL_AMMO) {

  override def fire(): Boolean = {
    if (super.fire()) {
      val p = theHost.getPosition()
      p += relOrigin
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
  val LOADING_TIME = 0.4d
  val INITIAL_AMMO = 250

}