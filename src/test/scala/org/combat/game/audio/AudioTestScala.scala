package org.combat.game.audio

import org.junit.Test
import org.combat.game.ContentManager
import org.combat.fxcore3d.audio.FxAudioPlayer

class AudioTestScala extends FxAudioPlayer {
  var alive = true

  @Test
  def test() {
    val sample =
      loadAudioSample(ContentManager.getResource("sounds/wav/missile.wav"))

    playSample(sample)
  }

}
