package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a generic weapon
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractWeapon(val theHost: AbstractVehicle, val relOrigin: FxPoint3D, val loadingTime: Double, var ammo: Int) {
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

    if (lastFire > 0 || ammo <= 0) false
    else {
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