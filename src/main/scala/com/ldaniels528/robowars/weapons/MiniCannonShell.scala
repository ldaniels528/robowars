package com.ldaniels528.robowars.weapons

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.polygon.{FxPolyhedron, FxPolyhedronInstance}
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.robowars.weapons.MiniCannonShell._

/**
 * MiniCannon Shell-Casing
 * @author lawrence.daniels@gmail.com
 */
class MiniCannonShell(world: FxWorld, origin: FxPoint3D, agl: FxAngle3D, vel: FxPoint3D)
  extends AbstractShell(world, origin, agl,
    FxVelocityVector(
      vel.x + speed * Math.sin(agl.y + Math.PI / 2),
      vel.y + speed,
      vel.z + speed * Math.cos(agl.y + Math.PI / 2)),
    new FxAngle3D(angleX, angleY, angleZ),
    randomSpread, randomRotation, lifeTime) {

  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron,
    ourScale))

}

/**
 * MiniCannon Shell-Casing (Companion Object)
 * @author lawrence.daniels@gmail.com
 */
object MiniCannonShell {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("/models/weapons/shell.f3d")
  val ourScale = new FxPoint3D(0.1d, 0.1d, 0.2d)
  val angleX: Double = 2d
  val angleY: Double = 2d
  val angleZ: Double = 2d
  val speed: Double = 3d
  val randomSpread: Double = 1d
  val randomRotation: Double = 2d
  val lifeTime: Double = 1.5d

}
