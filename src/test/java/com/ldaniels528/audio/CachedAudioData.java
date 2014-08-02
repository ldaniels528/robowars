package com.ldaniels528.audio;

import javax.sound.sampled.AudioFormat;

/**
 * Encapsulates the audio information necessary for play back
 */
public class CachedAudioData {
  private AudioFormat format;
  private byte[] audioData;

  public CachedAudioData( AudioFormat format, byte[] data ) {
    this.format = format;
    this.audioData = data;
  }

  public AudioFormat getFormat() {
    return format;
  }

  public byte[] getAudioData() {
    return audioData;
  }
}
