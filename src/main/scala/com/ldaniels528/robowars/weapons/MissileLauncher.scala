package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.actors.AbstractVehicle
import com.ldaniels528.robowars.weapons.MissileLauncher._

/**
 * Missile Launcher
 * @author lawrence.daniels@gmail.com
 */
class MissileLauncher(host: AbstractVehicle, relPos: FxPoint3D)
  extends AbstractWeapon(host, relPos, LOADING_TIME, INITIAL_AMMO) {

  override def fire(): Boolean = {
    if (super.fire()) {
      // -- create a new missile
      val p = theHost.position
      p += relOrigin
      new MissileRound(theHost.world, theHost, p, theHost.angle)
      true
    } else false
  }

}

/**
 * Missile Launcher (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MissileLauncher {
  val LOADING_TIME: Double = 2d
  val INITIAL_AMMO: Int = 10

}
