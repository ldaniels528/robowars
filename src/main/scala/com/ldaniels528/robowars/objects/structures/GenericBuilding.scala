package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericBuilding._

/**
 * Generic Building
 * @author lawrence.daniels@gmail.com
 */
case class GenericBuilding(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dim: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, dim.h, pos.z), agl, health = 50) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, dim.toPoint)

  override def die() {
    super.die()
    val pos = position

    new GenericBuildingRuin(world, pos, angle, FxScale3D(dim.w, dim.h * .2, dim.d))

    // destroy the building
    destruct(dim.h)
  }
}

/**
 * Generic Building Companion Object
 * @author lawrence.daniels@gmail.com
 */
object GenericBuilding {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/building1.f3d")

  def apply(world: FxWorld, pos: FxPoint3D, dim: FxScale3D): GenericBuilding = {
    GenericBuilding(world, pos, FxAngle3D(), dim)
  }

}