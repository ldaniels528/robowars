package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.{FxPoint3D, FxScale3D}
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a MiniCannon Weapon
 * @author lawrence.daniels@gmail.com
 */
case class MiniCannon(host: AbstractVehicle, relPos: FxPoint3D, ammo0: Int = 250)
  extends AbstractWeapon(host, relPos, loadingTime = 0.4d, ammo = ammo0) {

  val shellCasingScale = FxScale3D(0.1d, 0.1d, 0.2d)

  override def fire(): Boolean = {
    val fired = super.fire()
    if (fired) {
      // play the audio sample
      audioPlayer ! MiniCannonClip

      // get the world instance
      val world = host.world

      // fire the round
      val p = host.position += relOrigin
      MiniCannonRound(world, host, host.position, host.angle)

      // eject the shell casing
      new ShellCasing(world, host.position, host.angle, host.dPosition, shellCasingScale)
    }
    fired
  }

}