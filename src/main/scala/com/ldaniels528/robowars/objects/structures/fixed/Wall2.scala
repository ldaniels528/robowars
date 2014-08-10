package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.fxcore3d.{FxAngle3D, FxPoint3D, FxScale3D, FxWorld}

/**
 * Wall Type 2
 * @author lawrence.daniels@gmail.com
 */
class Wall2(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, scale.h, pos.z), agl) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/wall2.f3d", scale)

}

