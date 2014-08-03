package com.ldaniels528.fxcore3d.audio

import java.io._
import javax.sound.sampled._

/**
 * FxEngine Audio Player
 * @author ldaniels
 */
trait FxAudioPlayer {

  /**
   * Creates a cached audio data object from the underlying sound data found
   * within the given input stream.
   * @param is the given {@link InputStream input stream}
   * @return a {@link FxAudioSample cached audio data object}
   * @throws IOException
   * @throws UnsupportedAudioFileException
   */
  def loadAudioSample(is: InputStream): FxAudioSample = {
    // create a buffered stream
    val bis = new BufferedInputStream(is)

    // create an audio input stream
    val audioInputStream = AudioSystem.getAudioInputStream(bis)

    // get the audio format
    val audioFormat = audioInputStream.getFormat()

    // Create a buffer for moving data from the audio stream to the line.
    val bufferSize = (audioFormat.getSampleRate() * audioFormat.getFrameSize()).toInt
    val buffer = new Array[Byte](bufferSize)

    // create memory stream
    val baos = new ByteArrayOutputStream(bufferSize)

    // copy contents to the memory stream
    var count = 0
    do {
      count = audioInputStream.read(buffer, 0, buffer.length)
      if (count > 0) {
        baos.write(buffer, 0, count)
      }
    } while (count != -1)

    new FxAudioSample(audioFormat, baos.toByteArray())
  }

  /**
   * Plays the audio represented by the given cached audcio data object
   * @param cachedAudioData the given {@link FxAudioSample cached audcio data object}
   */
  def playSample(sample: FxAudioSample) {
    val format = sample.getFormat()
    val data = sample.getAudioData()

    // Open a data line to play our type of sampled audio. Use SourceDataLine
    // for play and TargetDataLine for record.
    val info = new DataLine.Info(classOf[SourceDataLine], format)
    if (!AudioSystem.isLineSupported(info)) {
      System.out.println("AudioPlayer.playAudioStream does not handle this type of audio.")
      return
    }

    // Create a SourceDataLine for play back (throws LineUnavailableException).
    val dataLine = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]

    // The line acquires system resources (throws LineAvailableException).
    dataLine.open(format)

    // Allows the line to move data in and out to a port.
    dataLine.start()

    dataLine.write(data, 0, data.length)

    // Continues data line I/O until its buffer is drained.
    dataLine.drain()

    // Closes the data line, freeing any resources such as the audio device.
    dataLine.close()
  }

  /**
   * Plays the audio represented by the given cached audcio data object
   * @param cachedAudioData the given {@link CachedAudioData cached audcio data object}
   */
  def playContinuousSample(sample: FxAudioSample, isAlive: => Boolean) {
    val format = sample.getFormat();
    val data = sample.getAudioData();

    // Open a data line to play our type of sampled audio. Use SourceDataLine
    // for play and TargetDataLine for record.
    val info = new DataLine.Info(classOf[SourceDataLine], format)
    if (!AudioSystem.isLineSupported(info)) {
      System.out.println("AudioPlayer.playAudioStream does not handle this type of audio.");
      return ;
    }

    // Create a SourceDataLine for play back (throws LineUnavailableException).
    val dataLine = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]

    // The line acquires system resources (throws LineAvailableException).
    dataLine.open(format);

    while (isAlive) {
      // Allows the line to move data in and out to a port.
      dataLine.start();

      dataLine.write(data, 0, data.length);

      // Continues data line I/O until its buffer is drained.
      dataLine.drain();
    }

    // Closes the data line, freeing any resources such as the audio device.
    dataLine.close();
  }

}