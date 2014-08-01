package org.combat.game.actors

import FesseTankRemains._

import org.combat.fxcore3d.FxAngle3D
import org.combat.fxcore3d.FxPoint3D
import org.combat.fxcore3d.FxPolyhedron
import org.combat.fxcore3d.FxPolyhedronInstance
import org.combat.fxcore3d.FxWorld
import org.combat.game.ContentManager
import org.combat.game.VirtualWorldImpl
import org.combat.game.structures.AbstractMovingScenery

/**
 * Remains of a Fesse Tank
 * @author lawrence.daniels@gmail.com
 */
class FesseTankRemains(world: FxWorld, deadTank: AbstractPlayer)
  extends AbstractMovingScenery(world, deadTank.getPosition(), deadTank.getAngle(), deadTank.getdPosition(), deadTank.getdAngle()) {

  usePolyhedronInstance(new FxPolyhedronInstance(ourDefaultPolyhedron, ourScale))

  // -- throw the remaining tank up in the air
  val dp = getdPosition()
  dp.y = FxWorld.rand(0, ourSpeedUp)
  setdPosition(dp)

  // -- set a random rotation on the remaining tank
  setdAngle(new FxAngle3D(
    FxWorld.rand(-ourRandRot, ourRandRot),
    FxWorld.rand(-ourRandRot, ourRandRot),
    FxWorld.rand(-ourRandRot, ourRandRot)))

  override def update(dt: Double) {
    super.update(dt)
    val p = getPosition()
    // -- check if hit the ground
    if (p.y < ourScale.y) {
      val dp = getdPosition()
      p.y = ourScale.y
      dp.x = 0
      dp.y = 0
      dp.z = 0
      setPosition(p)
      val a = getAngle()
      a.set(0, a.y, 0)
      // a.x = a.z = 0
      setAngle(a)
      setdAngle(new FxAngle3D())
      setdPosition(dp)
    } else if (p.y > ourScale.y) {
      val dp = getdPosition()
      dp.y += world.gravity * dt
      setdPosition(dp)
    }
  }
}

/**
 * FesseTankRemains Companion Object
 * @author lawrence.daniels@gmail.com
 */
object FesseTankRemains {
  val ourDefaultPolyhedron: FxPolyhedron = ContentManager.loadModel("f3d/fesseTankRemains.f3d")
  val ourScale = new FxPoint3D(1.5d, 0.23d, 2d)
  val ourSpeedUp: Double = 20d
  val ourRandRot: Double = 3d

}