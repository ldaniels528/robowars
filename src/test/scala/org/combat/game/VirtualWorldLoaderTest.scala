package org.combat.game

import org.junit.Test
import java.io.File

class VirtualWorldLoaderTest {

  @Test
  def load() {
    val world = VirtualWorldLoader.load("worlds/world_0001.xml")
    println(world)
  }

}