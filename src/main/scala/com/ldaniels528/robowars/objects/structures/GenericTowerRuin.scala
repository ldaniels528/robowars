package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericTowerRuin._

/**
 * Generic Tower Ruin
 * @author lawrence.daniels@gmail.com
 */
class GenericTowerRuin(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dim: FxScale3D)
  extends AbstractStaticStructure(world, FxPoint3D(pos.x, dim.h, pos.z), agl) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, dim.toPoint)

}

/**
 * Generic Tower Ruin (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericTowerRuin {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/buildingRubble.f3d")

}