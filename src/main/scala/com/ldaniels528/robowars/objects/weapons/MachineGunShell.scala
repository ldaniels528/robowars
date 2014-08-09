package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.objects.weapons.MachineGunShell._
import com.ldaniels528.robowars.objects.weapons.AbstractShellCasing.computeVector

/**
 * Machine-gun shell-casing
 * @author lawrence.daniels@gmail.com
 */
case class MachineGunShell(w: FxWorld, p: FxPoint3D, a: FxAngle3D, vel: FxPoint3D)
  extends AbstractShellCasing(w, p, a, computeVector(vel, a), ANGLE, randomSpread = 1d, randomRotation = 2d, lifeTime = 1.5d) {

  // set the default polyhedron instance
  lazy val modelInstance = new FxPolyhedronInstance(MODEL, SCALE)

}

/**
 * Machine-gun shell-Casing (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MachineGunShell {
  private val MODEL: FxPolyhedron = ContentManager.loadModel("/models/weapons/shell.f3d")
  private val SCALE = new FxScale3D(0.05d, 0.05d, 0.05d)
  private val ANGLE = FxAngle3D(2d, 2d, 2d)

}
