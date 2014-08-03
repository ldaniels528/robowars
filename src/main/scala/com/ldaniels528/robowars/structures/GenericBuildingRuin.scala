package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericBuildingRuin._;

/**
 * Generic Building Ruin
 * @author lawrence.daniels@gmail.com
 */
class GenericBuildingRuin(world: FxWorld, x: Double, z: Double, agl: FxAngle3D, w: Double, b: Double, h: Double)
  extends AbstractStaticStructure(world, x, h, z, agl, Double.MaxValue) {

  // -- make a building
  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron, new FxPoint3D(w, h, b)));

}

/**
 * Generic Building Ruin (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericBuildingRuin {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("/models/structures/build2Rubble.f3d")

}
