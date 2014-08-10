package com.ldaniels528.robowars.objects.structures.fixed

import com.ldaniels528.fxcore3d.{FxObject, FxAngle3D, FxPoint3D, FxWorld}

/**
 * Abstract Static Structure
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractStaticStructure(world: FxWorld, pos: FxPoint3D, agl: FxAngle3D)
  extends FxObject(world, pos, agl)