package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Remains of a destroyed Pyramid
 * @author ldaniels
 */
class PyramidRubble(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, size: FxScale3D)
  extends AbstractStaticStructure(world, FxPoint3D(pos.x, size.h, pos.z), agl, Double.MaxValue) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/pyramidRubble.f3d", size)

}

