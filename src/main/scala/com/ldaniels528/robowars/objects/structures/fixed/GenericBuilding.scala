package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Generic Building
 * @author lawrence.daniels@gmail.com
 */
case class GenericBuilding(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends DestroyableStructure(w, FxPoint3D(pos.x, scale.h, pos.z), agl, initialHealth = 60) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/building1.f3d", scale)

  override def die() {
    super.die()

    // leave ruins behind
    new GenericBuildingRuin(world, position, angle, scale.reducedHeight(0.2d))

    // destroy the building
    destruct(scale.h)
  }
}

/**
 * Generic Building Companion Object
 * @author lawrence.daniels@gmail.com
 */
object GenericBuilding {

  def apply(world: FxWorld, pos: FxPoint3D, scale: FxScale3D): GenericBuilding = {
    GenericBuilding(world, pos, FxAngle3D(), scale)
  }

}