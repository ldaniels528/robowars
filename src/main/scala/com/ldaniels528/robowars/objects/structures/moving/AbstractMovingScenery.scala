package com.ldaniels528.robowars.objects.structures.moving

import com.ldaniels528.fxcore3d._

/**
 * Abstract Moving Scenery
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingScenery(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dPos: FxVelocityVector, dAgl: FxAngle3D)
  extends FxMovingObject(world, pos, agl, dPos, dAgl)
	