package org.combat.game.structures

import GenericPillar._
import org.combat.fxcore3d._
import org.combat.game.ContentManager

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
  val DEFAULT_MODEL: FxPolyhedron = ContentManager.loadModel("f3d/pillar1.f3d")

}