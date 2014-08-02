package com.ldaniels528.fxcore3d

/**
 * Represents a collision event
 * @author lawrence.daniels@gmail.com
 */
case class FxEventCollision(time: Double, theObject: FxObject, dt: Double) extends FxEvent