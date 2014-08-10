package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Generic Tower
 * @author lawrence.daniels@gmail.com
 */
case class GenericTower(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends DestroyableStructure(w, FxPoint3D(pos.x, scale.h, pos.z), agl, initialHealth = 60) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/tower1.f3d", scale)

  override def die() {
    super.die()

    // leave ruins behind
    new GenericTowerRuin(world, position, angle, scale.reducedHeight(0.2d))

    // destroy the building
    destruct(scale.h)
  }

}
