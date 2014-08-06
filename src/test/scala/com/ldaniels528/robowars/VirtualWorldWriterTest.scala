package com.ldaniels528.robowars

import org.junit.Test

/**
 * Created by ldaniels on 8/3/14.
 */
class VirtualWorldWriterTest {

  @Test
  def save() {
    val world = new VirtualWorldFull()
    world.update(0)
    VirtualWorldWriter.save(world)
  }

}
