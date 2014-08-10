package com.ldaniels528.robowars.objects

import com.ldaniels528.fxcore3d.{FxPoint3D, FxWorld}
import com.ldaniels528.robowars.objects.structures.moving.Fragment

/**
 * <h2>Destructible</h2>
 * This trait is implemented by instances within the virtual world that can be exploded into fragments
 * @author lawrence.daniels@gmail.com
 */
trait Destructible {
  self: {def world: FxWorld; def position: FxPoint3D} =>

  def explodeIntoFragments(fragments: Int, size: Double = 0.25, speed: Double = 25, spread: Double = 2,
                           rotation: Double = 3, generations: Int = 1) {
    // display the fragments
    val pos = position
    (1 to fragments) foreach { n =>
      new Fragment(world, size, pos, spread, generations, speed, rotation)
    }
  }

}
