package com.ldaniels528.robowars.objects.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.FxModelInstance
import com.ldaniels528.robowars.objects.weapons.AbstractShellCasing.computeVector

/**
 * Mini-Cannon shell-casing
 * @author lawrence.daniels@gmail.com
 */
case class MiniCannonShell(w: FxWorld, p: FxPoint3D, a: FxAngle3D, vel: FxPoint3D)
  extends AbstractShellCasing(w, p, a, computeVector(vel, a), FxAngle3D(2d, 2d, 2d),
    randomSpread = 1d, randomRotation = 2d, lifeTime = 1.5d) {

  // set the default polyhedron instance
  lazy val modelInstance = FxModelInstance("/models/weapons/shell.f3d", FxScale3D(0.1d, 0.1d, 0.2d))

}

