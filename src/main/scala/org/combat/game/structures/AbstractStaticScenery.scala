package org.combat.game.structures

import org.combat.fxcore3d._

/**
 * Abstract Static Scenery
 * @author lawrence.daniels@gmail.com
 */
class AbstractStaticScenery(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractStaticObject(world, pos, agl, Double.MaxValue)