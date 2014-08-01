package org.combat.game.structures

import GenericBuildingRuin._
import org.combat.fxcore3d._
import org.combat.game.ContentManager;

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
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("f3d/build2re.f3d")

}
