package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericTower._

/**
 * Generic Tower
 * @author lawrence.daniels@gmail.com
 */
class GenericTower(world: FxWorld,
                   x: Double, z: Double,
                   agl: FxAngle3D,
                   width: Double, breadth: Double, height: Double)
  extends AbstractStaticStructure(world, x, height, z, FxAngle3D()) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, FxPoint3D(width, height, breadth))

  override def die() {
    super.die()
    val pos = position
    new GenericTowerRuin(world, pos.x, pos.z, angle, width, breadth, height * .2)

    // destroy the building
    destruct(height)
  }

}

/**
 * Generic Tower (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericTower {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/build2.f3d")
}