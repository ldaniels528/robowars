package org.combat.game.structures

import org.combat.fxcore3d._
import org.combat.game.AbstractMovingObject

/**
 * Abstract Moving Scenery
 * @author lawrence.daniels@gmail.com
 */
class AbstractMovingScenery(
  theWorld: FxWorld,
  pos: FxPoint3D,
  agl: FxAngle3D,
  dPos: FxVelocityVector,
  dAgl: FxAngle3D)
  extends AbstractMovingObject(theWorld, pos, agl, dPos, dAgl) 
	