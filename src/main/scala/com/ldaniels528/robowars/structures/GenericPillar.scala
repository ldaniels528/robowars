package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.structures.GenericPillar._

/**
 * Generic Pillar
 * @author lawrence.daniels@gmail.com
 */
case class GenericPillar(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dim: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, dim.h, pos.z), agl) {

  // set the default polyhedron instance
  lazy val polyhedronInstance = new FxPolyhedronInstance(MODEL, dim.toPoint)

}

/**
 * Generic Pillar (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericPillar {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pillar1.f3d")

}