package com.ldaniels528.robowars.structures

import GenericWall._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedronInstance, FxPolyhedron}
import com.ldaniels528.robowars.ContentManager

/**
 * Generic Wall
 * @author lawrence.daniels@gmail.com
 */
class GenericWall(
  world: FxWorld,
  x: Double, z: Double,
  agl: FxAngle3D,
  w: Double, b: Double, h: Double)
  extends AbstractStaticStructure(world, x, h, z, agl) {

  // -- make a building
  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron, new FxPoint3D(w, h, b)))

}

/**
 * Generic Wall (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericWall {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("/models/structures/wall1.f3d")

}
