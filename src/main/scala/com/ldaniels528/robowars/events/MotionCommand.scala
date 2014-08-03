package com.ldaniels528.robowars.events

import com.ldaniels528.fxcore3d.{FxEvent, FxPoint3D}

/**
 * Represents a motion event
 * @author lawrence.daniels@gmail.com
 */
case class MotionCommand(time: Double, id: Int, command: Int, dest: FxPoint3D, precision: Double) extends FxEvent