package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.FxPoint3D
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import com.ldaniels528.robowars.objects.weapons.BombBay._

/**
 * Bomb Bay
 * @author lawrence.daniels@gmail.com
 */
class BombBay(host: AbstractVehicle, relPos: FxPoint3D)
  extends AbstractWeapon(host, relPos, LOADING_TIME, INITIAL_AMMO) {

  override def fire(): Boolean = {
    if (super.fire()) {
      val p = theHost.position
      p += relOrigin
      new GenericBomb(theHost.world, theHost.position, theHost.angle, theHost.getdPosition(), 15)
      true
    }
    false
  }

}

/**
 * BombBay Companion Object
 * @author lawrence.daniels@gmail.com
 */
object BombBay {
  val LOADING_TIME: Double = 0.5d
  val INITIAL_AMMO: Int = 10

}