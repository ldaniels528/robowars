package com.ldaniels528.robowars.audio

import akka.actor._
import com.ldaniels528.fxcore3d.audio._
import com.ldaniels528.robowars.ContentManager

/**
 * RoboWars Audio Player
 * @author lawrence.daniels@gmail.com
 */
object AudioManager extends FxAudioPlayer {
  private val system = ActorSystem("AudioSystem")
  private val audioSampleCache = preloadAudioSamples()

  // create the audio play-back actor
  val audioPlayer = system.actorOf(Props[AudioPlaybackActor], name = "audioPlayer")

  /**
   * Preloads all audio-samples
   */
  private def preloadAudioSamples(): Map[AudioKey, FxAudioSample] = {
    val sampleTuples = Seq[(AudioKey, String)](
      (BuildingExplodeClip, "environmental/buildingExplode"),
      (CrashClip, "environmental/crash"),
      (ExplosionClip, "environmental/bigExplosion"),
      (ReloadClip, "weapons/loadclip"),
      (Ambient, "background/ambient"),
      (MachineGunClip, "weapons/machineGun"),
      (MissileClip, "weapons/missile"),
      (RewardClip, "items/reward"))
    Map(sampleTuples map {
      case (key, name) => (key, preloadAudioSample(s"/audio/$name.wav"))
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

  case object Ambient extends AudioKey

  case object CrashClip extends AudioKey

  case object ExplosionClip extends AudioKey

  case object BuildingExplodeClip extends AudioKey

  case object MachineGunClip extends AudioKey

  case object MissileClip extends AudioKey

  case object PlasmaClip extends AudioKey

  case object ReloadClip extends AudioKey

  case object RewardClip extends AudioKey

}