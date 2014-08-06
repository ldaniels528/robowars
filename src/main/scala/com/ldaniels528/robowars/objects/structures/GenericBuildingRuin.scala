package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericBuildingRuin._

/**
 * Generic Building Ruin
 * @author lawrence.daniels@gmail.com
 */
class GenericBuildingRuin(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends AbstractStaticStructure(world, FxPoint3D(pos.x, scale.h, pos.z), agl, Double.MaxValue) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, scale.toPoint)

}

/**
 * Generic Building Ruin (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericBuildingRuin {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/buildingRubble.f3d")

}
