package com.ldaniels528.fxcore3d.audio

import javax.sound.sampled.AudioFormat

import scala.beans.BeanProperty

/**
 * Encapsulates the audio information necessary for play back
 */
case class FxAudioSample(
  @BeanProperty format: AudioFormat,
  @BeanProperty audioData: Array[Byte])