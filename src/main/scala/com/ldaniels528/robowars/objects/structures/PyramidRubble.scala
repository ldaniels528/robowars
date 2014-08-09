package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.PyramidRubble._

/**
 * Remains of a destroyed Pyramid
 * @author ldaniels
 */
class PyramidRubble(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, size: FxScale3D)
  extends AbstractStaticStructure(world, FxPoint3D(pos.x, size.h, pos.z), agl, Double.MaxValue) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, size)

}

/**
 * Generic Building Ruin (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object PyramidRubble {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pyramidRubble.f3d")

}
