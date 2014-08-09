package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.structures.GenericPillar._

/**
 * Generic Pillar
 * @author lawrence.daniels@gmail.com
 */
case class GenericPillar(theWorld: FxWorld, pos: FxPoint3D, agl: FxAngle3D, scale: FxScale3D)
  extends AbstractStaticStructure(theWorld, FxPoint3D(pos.x, scale.h, pos.z), agl) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, scale)

}

/**
 * Generic Pillar (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object GenericPillar {
  val MODEL: FxPolyhedron = ContentManager.loadModel("/models/structures/pillar1.f3d")

}