package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericTower._

/**
 * Generic Tower
 * @author lawrence.daniels@gmail.com
 */
case class GenericTower(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dim: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, dim.h, pos.z), agl, health = 50) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, dim.toPoint)

  override def die() {
    super.die()
    val pos = position
    new GenericTowerRuin(world, pos, angle, FxScale3D(dim.w, dim.h * 0.2d, dim.d))

    // destroy the building
    destruct(dim.h)
  }

}

/**
 * Generic Tower (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericTower {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/tower1.f3d")

}