package com.ldaniels528.robowars.events

import com.ldaniels528.fxcore3d.FxEvent

/**
 * Represents a weapon event
 * @author lawrence.daniels@gmail.com
 */
case class EventWeaponCommand(time: Double, command: Int, arg: Int) extends FxEvent
