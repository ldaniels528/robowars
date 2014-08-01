package org.combat.game.structures

import PyramidRubble._
import org.combat.fxcore3d._
import org.combat.game.ContentManager

/**
 * Remains of a destroyed Pyramid
 * @author ldaniels
 */
class PyramidRubble(world: FxWorld, x: Double, z: Double, agl: FxAngle3D, w: Double, b: Double, h: Double)
  extends AbstractStaticStructure(world, x, h, z, agl, Double.MaxValue) {

  // -- make a building
  usePolyhedronInstance(new FxPolyhedronInstance(MODEL, new FxPoint3D(w, h, b)));

}

/**
 * Generic Building Ruin (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object PyramidRubble {
  val MODEL: FxPolyhedron = ContentManager.loadModel("f3d/pyramidRubble.f3d")

}
