package org.combat.fxcore3d.audio

import scala.beans.BeanProperty
import javax.sound.sampled.AudioFormat

/**
 * Encapsulates the audio information necessary for play back
 */
case class FxAudioSample(
  @BeanProperty format: AudioFormat,
  @BeanProperty audioData: Array[Byte])