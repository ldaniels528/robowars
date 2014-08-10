package com.ldaniels528.robowars.objects.structures.moving

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.structures.moving.MainGate._

/**
 * Main Gate
 * @author lawrence.daniels@gmail.com
 */
case class MainGate(w: FxWorld, pos0: FxPoint3D, agl0: FxAngle3D)
  extends AbstractMovingDoor(w, new FxPoint3D(pos0.x, pos0.y + SCALE.h, pos0.z), agl0) {

  def speedUp = 10d

  def speedDown = 10d

  def waitTimeUp = 3d

  def height = 10d

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/mainGate.f3d", SCALE)

}

/**
 * Main Gate (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MainGate {
  val SCALE = new FxScale3D(8d, 5d, 2d)

}