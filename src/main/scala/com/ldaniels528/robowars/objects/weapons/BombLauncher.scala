package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Bomb Launcher
 * @author lawrence.daniels@gmail.com
 */
class BombLauncher(host: AbstractVehicle, relPos: FxPoint3D)
  extends AbstractWeapon(host, relPos, loadingTime = 0.5d, ammo = 10) {

  override def fire(): Boolean = {
    val fired = super.fire()
    if (fired) {
      val p = theHost.position += relOrigin
      new Bomb(host.world, p, host.angle, host.dPosition, strength = 15)
    }
    fired
  }

}
