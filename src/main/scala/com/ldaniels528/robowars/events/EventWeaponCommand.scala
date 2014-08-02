package com.ldaniels528.robowars.events

import com.ldaniels528.fxcore3d.FxEvent

/**
 * Represents an actor weapon command/event
 * @author lawrence.daniels@gmail.com
 */
class EventWeaponCommand(
  val time: Double,
  val command: Int,
  val arg: Int) extends FxEvent(time)

/**
 * EventWeaponCommand Companion Object
 * @author lawrence.daniels@gmail.com
 */
object EventWeaponCommand {
  val FIRE = 19
  val SELECT = 18
  val MINICANNON = 20
  val MISSILE = 21
  val BOMB = 22
}
