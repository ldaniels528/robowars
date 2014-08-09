package com.ldaniels528.robowars

import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a virtual world
 * @author lawrence.daniels@gmail.com
 */
case class VirtualWorld(Xmin: Double, Ymin: Double, size: Double, rows: Int, gravity: Double)
  extends AbstractVirtualWorld(Xmin, Ymin, size, rows) {

  var activePlayer: AbstractVehicle = _

}