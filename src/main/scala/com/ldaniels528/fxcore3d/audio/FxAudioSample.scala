package com.ldaniels528.fxcore3d.audio

import javax.sound.sampled.AudioFormat

/**
 * Encapsulates the audio information necessary for play back
 * @author lawrence.daniels@gmail.com
 */
case class FxAudioSample(format: AudioFormat, audioData: Array[Byte])