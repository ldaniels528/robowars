package org.combat.game.weapons

import org.combat.fxcore3d._

/**
 * Explosion
 * @author lawrence.daniels@gmail.com
 */
class Explosion(world: FxWorld, strength: Double)
  extends AbstractRound(world, null, new FxPoint3D(), FxVelocityVector(0, 0, 0), 0, 0, 0, 0, 0, 0, 0, 1, strength)
