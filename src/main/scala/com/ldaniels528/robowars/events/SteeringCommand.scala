package com.ldaniels528.robowars.events

import com.ldaniels528.fxcore3d.FxEvent

/**
 * Represents a vehicle steering event
 * @author lawrence.daniels@gmail.com
 */
case class SteeringCommand(time: Double, command: Int, factor: Double, dt: Double) extends FxEvent
