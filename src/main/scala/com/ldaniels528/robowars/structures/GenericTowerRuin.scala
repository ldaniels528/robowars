package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericTowerRuin._

/**
 * Generic Tower Ruin
 * @author lawrence.daniels@gmail.com
 */
class GenericTowerRuin(world: FxWorld, x: Double, z: Double, agl: FxAngle3D, w: Double, b: Double, h: Double)
  extends AbstractStaticStructure(world, x, h, z, agl, Double.MaxValue) {

  // -- make a building
  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, new FxPoint3D(w, h, b)));

}

/**
 * Generic Tower Ruin (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericTowerRuin {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/build2Rubble.f3d")

}