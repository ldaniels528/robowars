package com.ldaniels528.robowars.objects.structures.moving

import com.ldaniels528.fxcore3d._

/**
 * Abstract Moving Structure
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractMovingStructure(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D, dpos: FxVelocityVector, dagl: FxAngle3D)
  extends FxMovingObject(world, pos, agl, dpos, dagl)