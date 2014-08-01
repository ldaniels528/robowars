package org.combat.game.audio

import org.combat.game.util.ResourceUtil._
import java.io._
import scala.util.{ Try, Success, Failure }
import akka.actor._
import sun.audio._
import org.apache.commons.io.IOUtils
import org.combat.game.ContentManager
import scala.beans.BeanProperty
import org.combat.fxcore3d.audio._

/**
 * Combat Audio Player
 * @author lawrence.daniels@gmail.com
 */
object CombatAudioPlayer extends FxAudioPlayer {
  private val system = ActorSystem("AudioSystem")
  private val audioSampleCache = preloadAudioSamples()

  // create the audio play-back actor
  val audioPlayer = system.actorOf(Props[AudioPlaybackActor], name = "audioPlayer")

  /**
   * Preloads all audio-samples
   */
  private def preloadAudioSamples(): Map[AudioKey, FxAudioSample] = {
    val sampleTuples = Seq[(AudioKey, String)](
      (BuildingExplodeClip, "buildingExplode"),
      (CrashClip, "crash"),
      (ExplosionClip, "bigExplosion"),
      (ReloadClip, "loadclip"),
      (SpaceMusic, "spaceMusic"),
      (MachineGunClip, "machineGun"),
      (MissileClip, "missile"),
      (RewardClip, "reward"))
    Map(sampleTuples map {
      case (key, name) => (key, preloadAudioSample(s"sounds/wav/$name.wav"))
    }: _*)
  }

  private def preloadAudioSample(resource: String) = {
    println(s"Loading audio sample '$resource'...")
    loadAudioSample(ContentManager.getResource(resource))
  }

  /**
   * Audio-Clip Playback Actor
   * @author lawrence.daniels@gmail.com
   */
  class AudioPlaybackActor extends Actor {
    def receive = {
      case Init =>
        println("Initializing sound system")
      case audioKey: AudioKey =>
        audioSampleCache.get(audioKey) foreach { sample =>
          playSample(sample)
        }
      case x => super.unhandled(x)
    }
  }

  /**
   * Audio-Clip messages
   */
  trait AudioKey
  case object Init 
  case object CrashClip extends AudioKey
  case object ExplosionClip extends AudioKey
  case object BuildingExplodeClip extends AudioKey
  case object MachineGunClip extends AudioKey
  case object MissileClip extends AudioKey
  case object PlasmaClip extends AudioKey
  case object ReloadClip extends AudioKey
  case object RewardClip extends AudioKey
  case object SpaceMusic extends AudioKey

}