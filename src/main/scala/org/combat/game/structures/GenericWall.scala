package org.combat.game.structures

import GenericWall._
import org.combat.fxcore3d._
import org.combat.game.ContentManager

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
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("f3d/wall1.f3d")

}
