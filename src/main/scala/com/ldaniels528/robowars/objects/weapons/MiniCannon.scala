package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a MiniCannon Weapon
 * @author lawrence.daniels@gmail.com
 */
case class MiniCannon(host: AbstractVehicle, relPos: FxPoint3D)
  extends AbstractWeapon(host, relPos, loadingTime = 0.4d, ammo = 250) {

  override def fire(): Boolean = {
    val fired = super.fire()
    if (fired) {
      // play the audio sample
      audioPlayer ! MiniCannonClip

      // get the world, position, and angle
      val (w, p, a) = (host.world, host.position, host.angle)

      // fire the round
      p += relOrigin
      MiniCannonRound(w, host, p, a)

      // eject the shell casing
      MiniCannonShell(w, p, a, host.getdPosition())
    }
    fired
  }

}