package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericTower._

/**
 * Generic Tower
 * @author lawrence.daniels@gmail.com
 */
case class GenericTower(w: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends AbstractStaticStructure(w, FxPoint3D(pos.x, scale.h, pos.z), agl, health = 50) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, scale)

  override def die() {
    super.die()

    // leave ruins behind
    new GenericTowerRuin(world, position, angle, scale.reducedHeight(0.2d))

    // destroy the building
    destruct(scale.h)
  }

}

/**
 * Generic Tower (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericTower {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/tower1.f3d")

}