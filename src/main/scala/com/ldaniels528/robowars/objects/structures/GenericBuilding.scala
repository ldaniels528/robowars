package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericBuilding._

/**
 * Generic Building
 * @author lawrence.daniels@gmail.com
 */
case class GenericBuilding(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends AbstractStaticStructure(w, FxPoint3D(pos.x, scale.h, pos.z), agl, health = 50) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, scale)

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
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/building1.f3d")

  def apply(world: FxWorld, pos: FxPoint3D, scale: FxScale3D): GenericBuilding = {
    GenericBuilding(world, pos, FxAngle3D(), scale)
  }

}