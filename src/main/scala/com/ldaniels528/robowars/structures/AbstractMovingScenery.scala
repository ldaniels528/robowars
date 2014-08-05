package com.ldaniels528.robowars.structures

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.AbstractMovingObject

/**
 * Abstract Moving Scenery
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingScenery(world: FxWorld,
                                     pos: FxPoint3D,
                                     agl: FxAngle3D,
                                     dPos: FxVelocityVector,
                                     dAgl: FxAngle3D)
  extends AbstractMovingObject(world, pos, agl, dPos, dAgl)
	