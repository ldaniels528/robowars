package com.ldaniels528.robowars.audio

import org.junit.Test
import com.ldaniels528.robowars.ContentManager
import com.ldaniels528.fxcore3d.audio.FxAudioPlayer

class AudioTestScala extends FxAudioPlayer {
  var alive = true

  @Test
  def test() {
    val sample =
      loadAudioSample(ContentManager.getResource("sounds/wav/missile.wav"))

    playSample(sample)
  }

}
