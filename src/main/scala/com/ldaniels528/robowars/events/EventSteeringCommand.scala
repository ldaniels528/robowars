package com.ldaniels528.robowars.events

import com.ldaniels528.fxcore3d.FxEvent

/**
 * Represents a vehicle steering command/event
 * @author lawrence.daniels@gmail.com
 */
case class EventSteeringCommand(myTime: Double, command: Int, factor: Double, dt: Double) extends FxEvent
