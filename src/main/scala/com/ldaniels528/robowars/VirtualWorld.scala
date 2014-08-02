package com.ldaniels528.robowars

import java.awt.Color
import scala.beans.BeanProperty

import com.ldaniels528.fxcore3d.FxWorld
import com.ldaniels528.robowars.actors.FesseTank
import com.ldaniels528.robowars.actors.AbstractActor

/**
 * Represents a virtual world
 * @author lawrence.daniels@gmail.com
 */
case class VirtualWorld(Xmin: Double, Ymin: Double, size: Double, rows: Int, gravity: Double)
  extends AbstractVirtualWorld(Xmin, Ymin, size, rows) {

  @BeanProperty var activePlayer: AbstractActor = _

  // environmental settings
  var skyColor = new Color(60, 60, 200)
  var groundColor = new Color(130, 130, 50)

}