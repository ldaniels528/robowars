package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.structures.Structure

/**
 * Generic Building Ruin
 * @author lawrence.daniels@gmail.com
 */
class BuildingRuin(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends FxObject(world, FxPoint3D(pos.x, scale.h, pos.z), agl)
  with Structure {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/buildingRuins.f3d", scale)

}
