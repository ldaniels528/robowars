package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.objects.structures.MovingDoorStates._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Represents a Moving Door
 * @author lawrence.daniels@gmail.com
 */
abstract class MovingDoor(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends FxMovingObject(world, pos, agl, FxVelocityVector(), FxAngle3D())
  with Structure {

  private val originalPos: Double = pos.y
  private var state: DoorStates = InitialState
  var counter: Double = 0

  def speedUp: Double

  def speedDown: Double

  def waitTimeUp: Double

  def height: Double

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case v: AbstractVehicle => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    if (state == InitialState) {
      state = StartMovingUp
    }
    false
  }

  override def update(dt: Double) {
    super.update(dt)

    state match {
      case InitialState =>
      case StartMovingUp => doorStartMovingUp(dt)
      case MovingUp => doorMovingUp(dt)
      case Waiting => doorWait(dt)
      case MovingDown => doorMovingDown(dt)
      case _ =>
    }
  }

  private def doorStartMovingUp(dt: Double) {
    val p = getdPosition()
    p.y = speedUp * dt
    setdPosition(p)
    state = MovingUp
  }

  private def doorMovingUp(dt: Double) {
    val p = position
    if (p.y - height > originalPos) {
      val dp = getdPosition()
      dp.y = 0
      setdPosition(dp)
      state = Waiting
      counter = 0
    }
  }

  private def doorWait(dt: Double) {
    counter = counter + dt
    if (counter > waitTimeUp) {
      val p = getdPosition()
      p.y = -speedDown * dt
      setdPosition(p)
      state = MovingDown
    }
  }

  private def doorMovingDown(dt: Double) {
    val p = position
    if (p.y < originalPos) {
      val dp = getdPosition()
      dp.y = 0
      setdPosition(dp)
      state = StartMovingUp
    }
  }

}
