package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a Machine-gun weapon
 * @author lawrence.daniels@gmail.com
 */
case class MachineGun(host: AbstractVehicle, relPos: FxPoint3D, ammo0: Int = 500)
  extends AbstractWeapon(host, relPos, loadingTime = 0.2d, ammo = ammo0) {

  override def fire(): Boolean = {
    val fired = super.fire()
    if (fired) {
      // play the audio sample
      audioPlayer ! MachineGunClip

      // get the world, position, and angle
      val (w, p, a) = (host.world, host.position, host.angle)

      // fire the round
      p += relOrigin
      MachineGunRound(w, host, p, a)

      // eject the shell casing
      MachineGunShell(w, p, a, host.getdPosition())
    }
    fired
  }

}