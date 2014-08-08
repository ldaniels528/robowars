package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.MainGate._

/**
 * Main Gate
 * @author lawrence.daniels@gmail.com
 */
class MainGate(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractDoor(world, new FxPoint3D(pos.x, pos.y + SCALE.y, pos.z), agl, SPEED_UP, speedDown, waitTimeUp, maxHeight) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * Main Gate (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MainGate {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/main_gate.f3d")
  val SCALE = new FxPoint3D(8d, 5d, 2d)

  val SPEED_UP: Double = 10d
  val speedDown: Double = 10d
  val waitTimeUp: Double = 3d
  val maxHeight: Double = 10d


}