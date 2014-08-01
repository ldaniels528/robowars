package org.combat.fxcore3d

/**
 * Represents a collision event
 * @author lawrence.daniels@gmail.com
 */
class FxEventCollision(
  myTime: Double,
  val theObject: FxObject,
  val dt: Double) extends FxEvent(myTime) 