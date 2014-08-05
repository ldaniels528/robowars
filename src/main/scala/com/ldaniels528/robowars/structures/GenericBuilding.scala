package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericBuilding._

/**
 * Generic Building
 * @author lawrence.daniels@gmail.com
 */
class GenericBuilding(world: FxWorld,
                      x: Double, z: Double,
                      agl: FxAngle3D,
                      width: Double, breadth: Double, height: Double)
  extends AbstractStaticStructure(world, x, height, z, agl) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, new FxPoint3D(width, height, breadth))

  override def die() {
    super.die()
    val pos = position

    new GenericBuildingRuin(world, pos.x, pos.z, angle, width, breadth, height * .2)

    // destroy the building
    destruct(height)
  }
}

/**
 * Generic Building Companion Object
 * @author lawrence.daniels@gmail.com
 */
object GenericBuilding {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/build2.f3d")

  /**
   * Generic building with the default 0,0,0 angle.
   */
  def apply(world: FxWorld, x: Double, z: Double, w: Double, b: Double, h: Double): GenericBuilding = {
    new GenericBuilding(world, x, z, new FxAngle3D(0, 0, 0), w, b, h)
  }

  /**
   * Generic building with the default 0,0,0 angle.
   */
  def apply(world: FxWorld, pos: FxPoint3D, dims: FxDimensions3D): GenericBuilding = {
    new GenericBuilding(world, pos.x, pos.z, new FxAngle3D(0, 0, 0), dims.width, dims.breadth, dims.height)
  }

}