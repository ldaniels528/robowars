package com.ldaniels528.robowars.objects.structures

import com.ldaniels528.fxcore3d._

/**
 * Abstract Static Scenery
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractStaticScenery(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractStaticObject(world, pos, agl, Double.MaxValue)