package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericWall._

/**
 * Generic Wall
 * @author lawrence.daniels@gmail.com
 */
class GenericWall(world: FxWorld,
                  x: Double, z: Double,
                  agl: FxAngle3D,
                  w: Double, b: Double, h: Double)
  extends AbstractStaticStructure(world, x, h, z, agl) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, FxPoint3D(w, h, b))

}

/**
 * Generic Wall (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericWall {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/wall1.f3d")

}
