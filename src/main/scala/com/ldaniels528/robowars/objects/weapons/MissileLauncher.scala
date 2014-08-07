package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a Missile Launcher Weapon
 * @author lawrence.daniels@gmail.com
 */
case class MissileLauncher(host: AbstractVehicle, relPos: FxPoint3D, ammo0: Int = 10)
  extends AbstractWeapon(host, relPos, loadingTime = 2d, ammo = ammo0) {

  override def fire(): Boolean = {
    val fired = super.fire()
    if (fired) {
      // play the audio sample
      audioPlayer ! MissileClip

      // launch a missile
      val p = theHost.position
      p.z += relOrigin.z
      new Missile(host.world, host, p, host.angle)
    }
    fired
  }

}
