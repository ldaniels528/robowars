package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxModelInstance, FxPolyhedron}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericWall._

/**
 * Generic Wall
 * @author lawrence.daniels@gmail.com
 */
case class GenericWall(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dim: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, dim.h, pos.z), agl) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxModelInstance(MODEL, dim)

}

/**
 * Generic Wall (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericWall {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/wall1.f3d")

}
