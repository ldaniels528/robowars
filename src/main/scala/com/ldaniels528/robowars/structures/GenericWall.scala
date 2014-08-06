package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericWall._

/**
 * Generic Wall
 * @author lawrence.daniels@gmail.com
 *
 *         x: Double, z: Double,
 *         w: Double, b: Double, h: Double
 */
class GenericWall(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dim:FxSize3D)
  extends AbstractStaticStructure(world, pos.x, dim.h, pos.z, agl) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, dim.toPoint) // FxPoint3D(w, h, b)

}

/**
 * Generic Wall (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericWall {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/wall1.f3d")

}
