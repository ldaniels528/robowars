package com.ldaniels528.robowars.structures

import GenericPillar._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedronInstance, FxPolyhedron}
import com.ldaniels528.robowars.ContentManager

/**
 * Generic Pillar
 * @author lawrence.daniels@gmail.com
 */
class GenericPillar(
  world: FxWorld,
  x: Double, z: Double,
  agl: FxAngle3D,
  w: Double, b: Double, h: Double)
  extends AbstractStaticStructure(world, x, h, z, agl) {

  // -- make a building
  usePolyhedronInstance(new FxPolyhedronInstance(DEFAULT_MODEL, new FxPoint3D(w, h, b)));

}

/**
 * Generic Pillar (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericPillar {
  val DEFAULT_MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pillar1.f3d")

}