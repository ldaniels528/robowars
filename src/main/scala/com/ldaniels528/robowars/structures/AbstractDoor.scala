package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.actors.AbstractVehicle
import com.ldaniels528.robowars.structures.AbstractDoor._

/**
 * Abstract Door
 * @author lawrence.daniels@gmail.com
 */
class AbstractDoor(theWorld: FxWorld,
                   pos: FxPoint3D,
                   agl: FxAngle3D,
                   speedUp: Double,
                   speedDown: Double,
                   waitTimeUp: Double,
                   height: Double)
  extends AbstractMovingStructure(theWorld, pos, agl, new FxVelocityVector(), new FxAngle3D()) {

  private val originalPos: Double = pos.y
  private var state: Int = DOOR_INITIAL_STATE
  private var counter: Double = 0

  override def interestedOfCollisionWith(obj: FxObject): Boolean = {
    obj match {
      case v: AbstractVehicle => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double): Boolean = {
    if (state == DOOR_INITIAL_STATE) {
      // Start move up
      FxWorld.debug(this, "open")
      state = DOOR_START_MOVING_UP
    }
    false
  }

  override def update(dt: Double) {
    super.update(dt)

    state match {
      case DOOR_INITIAL_STATE =>
      case DOOR_START_MOVING_UP => doorStartMovingUp(dt)
      case DOOR_MOVING_UP => doorMovingUp(dt)
      case DOOR_WAITING => doorWait(dt)
      case DOOR_MOVING_DOWN => doorMovingDown(dt)
      case _ =>
        FxWorld.debug(this, s"Door state ($state) is unrecognized")
    }
  }

  private def doorStartMovingUp(dt: Double) {
    val p = getdPosition()
    p.y = speedUp * dt
    setdPosition(p)
    state = DOOR_MOVING_UP
  }

  private def doorMovingUp(dt: Double) {
    val p = position
    if (p.y - height > originalPos) {
      val dp = getdPosition()
      dp.y = 0
      setdPosition(dp)
      state = DOOR_WAITING
      counter = 0
    }
  }

  private def doorWait(dt: Double) {
    counter = counter + dt
    if (counter > waitTimeUp) {
      val p = getdPosition()
      p.y = -speedDown * dt
      setdPosition(p)
      state = DOOR_MOVING_DOWN
    }
  }

  private def doorMovingDown(dt: Double) {
    val p = position
    if (p.y < originalPos) {
      val dp = getdPosition()
      dp.y = 0
      setdPosition(dp)
      state = DOOR_START_MOVING_UP
    }
  }

}

/**
 * Abstract Door
 * @author lawrence.daniels@gmail.com
 */
object AbstractDoor {
  val DOOR_INITIAL_STATE = 0
  val DOOR_START_MOVING_UP = 1
  val DOOR_MOVING_UP = 2
  val DOOR_WAITING = 3
  val DOOR_MOVING_DOWN = 4

}
