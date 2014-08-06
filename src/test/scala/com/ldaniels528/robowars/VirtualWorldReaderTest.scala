package com.ldaniels528.robowars

import org.junit.Test
import java.io.File

class VirtualWorldReaderTest {

  @Test
  def load() {
    val world = VirtualWorldReader.load("worlds/world_0004.xml")
    println(world)
  }

}