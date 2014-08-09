package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.structures.MainGate._

/**
 * Main Gate
 * @author lawrence.daniels@gmail.com
 */
class MainGate(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractDoor(world, new FxPoint3D(pos.x, pos.y + SCALE.h, pos.z), agl,
    speedUp = 10d, speedDown = 10d, waitTimeUp = 3d, height = 10d) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/main_gate.f3d", SCALE)

}

/**
 * Main Gate (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MainGate {
  val SCALE = new FxScale3D(8d, 5d, 2d)

}