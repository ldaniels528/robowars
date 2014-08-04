package com.ldaniels528.robowars

import java.awt.Color

import com.ldaniels528.robowars.actors.AbstractActor

/**
 * Represents a virtual world
 * @author lawrence.daniels@gmail.com
 */
case class VirtualWorld(Xmin: Double, Ymin: Double, size: Double, rows: Int, gravity: Double)
  extends AbstractVirtualWorld(Xmin, Ymin, size, rows) {

  var activePlayer: AbstractActor = _

  // environmental settings
  var skyColor = new Color(60, 60, 200)
  var groundColor = new Color(130, 130, 50)

}