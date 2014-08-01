package org.combat.game.events

import org.combat.fxcore3d.FxEvent
import org.combat.fxcore3d.FxPoint3D

/**
 * Represents a motion command/event
 * @author lawrence.daniels@gmail.com
 */
class EventMotionCommand(
  val time: Double,
  val id: Int,
  val command: Int,
  val dest: FxPoint3D,
  val precision: Double) extends FxEvent(time)

/**
 * EventMotionCommand Companion Object
 * @author lawrence.daniels@gmail.com
 */
object EventMotionCommand {
  val GOTO_POSITION = 1
  
}