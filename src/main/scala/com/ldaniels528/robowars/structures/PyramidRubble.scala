package com.ldaniels528.robowars.structures

import PyramidRubble._
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.ContentManager

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
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pyramidRubble.f3d")

}
