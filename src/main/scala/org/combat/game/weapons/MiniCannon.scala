package org.combat.game.weapons

import MiniCannon._
import org.combat.game.actors.AbstractVehicle
import org.combat.fxcore3d.FxPoint3D
import org.combat.game.ContentManager

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