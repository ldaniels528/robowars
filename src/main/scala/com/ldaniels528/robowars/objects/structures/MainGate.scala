package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.structures.MainGate._
import com.ldaniels528.robowars.objects.structures.MovingDoorStates._
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * Main Gate
 * @author lawrence.daniels@gmail.com
 */
case class MainGate(w: FxWorld, pos0: FxPoint3D, agl0: FxAngle3D)
  extends FxMovingObject(w, new FxPoint3D(pos0.x, pos0.y + SCALE.h, pos0.z), agl0, FxVelocityVector(), FxAngle3D())
  with MovingDoor {

  val originalPos: Double = pos0.y
  val speedUp = 10d
  val speedDown = 10d
  val waitTimeUp = 3d
  val height = 10d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/mainGate.f3d", SCALE)

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

}

/**
 * Main Gate (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MainGate {
  val SCALE = new FxScale3D(8d, 5d, 2d)

}