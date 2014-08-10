package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Tower
 * @author lawrence.daniels@gmail.com
 */
case class Tower(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends DestroyableStructure(w, FxPoint3D(pos.x, scale.h, pos.z), agl, initialHealth = 60) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/tower1.f3d", scale)

}
