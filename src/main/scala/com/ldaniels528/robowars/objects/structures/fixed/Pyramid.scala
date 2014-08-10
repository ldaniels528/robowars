package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance

/**
 * Represents a Pyramid Structure
 * @author lawrence.daniels@gmail.com
 */
case class Pyramid(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends DestroyableStructure(w, FxPoint3D(pos.x, 0, pos.z), agl, initialHealth = 60) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/structures/pyramid.f3d", scale)

  /**
   * Causes this virtual object to die
   */
  override def die() {
    super.die()

    // leave ruins behind
    new PyramidRuins(world, position, angle, scale.reducedHeight(0.2d))

    // destroy the pyramid
    destruct(scale.h)
  }

}
