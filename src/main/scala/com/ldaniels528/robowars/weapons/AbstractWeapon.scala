package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors.AbstractVehicle

import scala.beans.BeanProperty

/**
 * Abstract Weapon
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractWeapon(
  val theHost: AbstractVehicle,
  val relOrigin: FxPoint3D,
  val loadingTime: Double,
  @BeanProperty var ammo: Int) {

  protected var lastFire: Double = _

  /**
   * Returns the name of the weapon
   * @return the name of the weapon
   */
  def name: String = getClass.getSimpleName()

  /**
   * Increases the ammo by the given delta
   */
  def addAmmo(delta: Int) {
    ammo += delta
  }

  /**
   * Fires the weapon
   */
  def fire(): Boolean = {
    import com.ldaniels528.robowars.audio.AudioManager._

    if (lastFire > 0 || ammo <= 0) false
    else {
      // play the audio sample
      audioPlayer ! MachineGunClip

      // reduce the ammo & reset load time
      ammo -= 1
      lastFire = loadingTime
      true
    }
  }

  /**
   * Updates the entity
   */
  def update(dt: Double) {
    lastFire -= dt
    if (lastFire < 0) lastFire = 0
  }

}