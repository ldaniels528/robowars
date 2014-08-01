package org.combat.game.weapons

import MissileLauncher._
import org.combat.fxcore3d.FxPoint3D
import org.combat.game.ContentManager
import org.combat.game.actors.AbstractVehicle

/**
 * Missile Launcher
 * @author lawrence.daniels@gmail.com
 */
class MissileLauncher(host: AbstractVehicle, relPos: FxPoint3D)
  extends AbstractWeapon(host, relPos, LOADING_TIME, INITIAL_AMMO) {

  override def fire(): Boolean = {
    if (super.fire()) {
      // -- create a new missile
      val p = theHost.getPosition()
      p.plus(relOrigin)
      new MissileRound(theHost.getWorld(), theHost, p, theHost.getAngle())
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
