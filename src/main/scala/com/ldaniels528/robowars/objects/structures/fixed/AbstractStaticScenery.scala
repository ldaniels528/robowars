package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d.{FxPoint3D, FxAngle3D, FxWorld}

/**
 * Abstract Static Scenery
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractStaticScenery(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends AbstractStaticObject(world, pos, agl)