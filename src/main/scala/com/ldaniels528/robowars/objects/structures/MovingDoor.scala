package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.structures.MovingDoorStates._

/**
 * Represents a Moving Door
 * @author lawrence.daniels@gmail.com
 */
trait MovingDoor extends Structure {
  self : FxMovingObject =>

  protected var state: DoorStates = InitialState
  protected var counter: Double = 0

  def originalPos: Double

  override def position: FxPoint3D

  def speedUp: Double

  def speedDown: Double

  def waitTimeUp: Double

  def height: Double

  protected def doorStartMovingUp(dt: Double) {
    val p = dPosition
    p.y = speedUp * dt
    dPosition_=(p)
    state = MovingUp
  }

  protected def doorMovingUp(dt: Double) {
    val p = position
    if (p.y - height > originalPos) {
      val dp = dPosition
      dp.y = 0
      dPosition_=(dp)
      state = Waiting
      counter = 0
    }
  }

  protected def doorWait(dt: Double) {
    counter = counter + dt
    if (counter > waitTimeUp) {
      val p = dPosition
      p.y = -speedDown * dt
      dPosition_=(p)
      state = MovingDown
    }
  }

  protected def doorMovingDown(dt: Double) {
    val p = position
    if (p.y < originalPos) {
      val dp = dPosition
      dp.y = 0
      dPosition_=(dp)
      state = StartMovingUp
    }
  }

}
