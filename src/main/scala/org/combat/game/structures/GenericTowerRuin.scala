package org.combat.game.structures

import GenericTowerRuin._
import org.combat.game.ContentManager
import org.combat.fxcore3d._

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
  val MODEL: FxPolyhedron = ContentManager.loadModel("f3d/build2re.f3d")

}