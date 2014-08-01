package org.combat.audio;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.combat.game.ContentManager;
import org.junit.Test;

/**
 * Audio Test Suite 
 * @author ldaniels
 */
public class AudioTest {
	private boolean alive = true;
	
	@Test
	public void test() throws IOException, UnsupportedAudioFileException, InterruptedException {
		final CachedAudioData sample = 
			cacheAudioInputStream(ContentManager.getResource("sounds/wav/missile.wav"));
		
		playAudio(sample);
	}

  /**
   * Creates a cached audio data object from the underlying sound data found 
   * within the given input stream.
   * @param is the given {@link InputStream input stream}
   * @return a {@link CachedAudioData cached audio data object}
   * @throws IOException
   * @throws UnsupportedAudioFileException
   */ 
  private CachedAudioData cacheAudioInputStream( final InputStream is )
    throws IOException, UnsupportedAudioFileException {
    // create a buffered stream
    BufferedInputStream bis = new BufferedInputStream( is );

    // create an audio input stream
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( bis );

    // get the audio format
    AudioFormat audioFormat = audioInputStream.getFormat();

    // Create a buffer for moving data from the audio stream to the line.
    int bufferSize = ( int ) audioFormat.getSampleRate() * audioFormat.getFrameSize();
    byte[] buffer = new byte[bufferSize];

    // create memory stream
    ByteArrayOutputStream baos = new ByteArrayOutputStream( bufferSize );

    // copy contents to the memory stream
    int count = 0;
    while( ( count = audioInputStream.read( buffer, 0, buffer.length ) ) != -1 ) {
      baos.write( buffer, 0, count );
    }
    return new CachedAudioData( audioFormat, baos.toByteArray() );
  }

  /**
   * Plays the audio represented by the given cached audcio data object
   * @param cachedAudioData the given {@link CachedAudioData cached audcio data object}
   */
  private void playAudio( final CachedAudioData cachedAudioData ) {
      AudioFormat format = cachedAudioData.getFormat();
      byte[] data = cachedAudioData.getAudioData();

      // Open a data line to play our type of sampled audio. Use SourceDataLine
      // for play and TargetDataLine for record.
      DataLine.Info info = new DataLine.Info( SourceDataLine.class, format );
      if( !AudioSystem.isLineSupported( info ) ) {
        System.out.println( "AudioPlayer.playAudioStream does not handle this type of audio." );
        return;
      }

      try {
        // Create a SourceDataLine for play back (throws LineUnavailableException).
        SourceDataLine dataLine = ( SourceDataLine ) AudioSystem.getLine( info );

        // The line acquires system resources (throws LineAvailableException).
        dataLine.open( format );

        // Allows the line to move data in and out to a port.
        dataLine.start();

        dataLine.write( data, 0, data.length );

        // Continues data line I/O until its buffer is drained.
        dataLine.drain();

        // Closes the data line, freeing any resources such as the audio device.
        dataLine.close();
      }
      catch( LineUnavailableException e ) {
        e.printStackTrace();
      }
    }

  /**
   * Plays the audio represented by the given cached audcio data object
   * @param cachedAudioData the given {@link CachedAudioData cached audcio data object}
   */
  private void playBackgroundAudio( CachedAudioData cachedAudioData ) {
      AudioFormat format = cachedAudioData.getFormat();
      byte[] data = cachedAudioData.getAudioData();

      // Open a data line to play our type of sampled audio. Use SourceDataLine
      // for play and TargetDataLine for record.
      DataLine.Info info = new DataLine.Info( SourceDataLine.class, format );
      if( !AudioSystem.isLineSupported( info ) ) {
        System.out.println( "AudioPlayer.playAudioStream does not handle this type of audio." );
        return;
      }

      try {
        // Create a SourceDataLine for play back (throws LineUnavailableException).
        SourceDataLine dataLine = ( SourceDataLine ) AudioSystem.getLine( info );

        // The line acquires system resources (throws LineAvailableException).
        dataLine.open( format );

        while( alive ) {
          // Allows the line to move data in and out to a port.
          dataLine.start();

          dataLine.write( data, 0, data.length );

          // Continues data line I/O until its buffer is drained.
          dataLine.drain();
        }

        // Closes the data line, freeing any resources such as the audio device.
        dataLine.close();
      }
      catch( LineUnavailableException e ) {
        e.printStackTrace();
      }
    }
	  
}
