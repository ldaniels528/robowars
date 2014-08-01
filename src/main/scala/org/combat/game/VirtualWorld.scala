package org.combat.game

import java.awt.Color
import scala.beans.BeanProperty

import org.combat.fxcore3d.FxWorld
import org.combat.game.actors.FesseTank
import org.combat.game.actors.AbstractPlayer

/**
 * Represents a virtual world
 * @author lawrence.daniels@gmail.com
 */
case class VirtualWorld(Xmin: Double, Ymin: Double, size: Double, rows: Int, gravity: Double)
  extends AbstractVirtualWorld(Xmin, Ymin, size, rows) {

  @BeanProperty var activePlayer: AbstractPlayer = _

  // environmental settings
  var skyColor = new Color(60, 60, 200)
  var groundColor = new Color(130, 130, 50)

}