package com.ldaniels528.robowars.audio

import akka.actor._
import com.ldaniels528.fxcore3d.audio._
import com.ldaniels528.robowars.ContentManager

/**
 * Audio Manager
 * @author lawrence.daniels@gmail.com
 */
object AudioManager extends FxAudioPlayer {
  private val system = ActorSystem("AudioManager")
  private val audioSampleCache = loadSamples()

  // create the audio play-back actors
  val audioPlayers = (1 to 8) map (n => system.actorOf(Props[AudioPlaybackActor], name = s"audioPlayer$n"))
  var ticker = 0

  /**
   * Returns a reference to an actor for the audio play-back pool
   * @return an actor reference
   */
  def audioPlayer: ActorRef = {
    ticker += 1
    audioPlayers(ticker % audioPlayers.length)
  }

  /**
   * Loads all audio-samples
   */
  private def loadSamples(): Map[AudioKey, FxAudioSample] = {
    val samples = Seq[(AudioKey, String)](
      BigExplosionClip -> "environmental/bigExplosion",
      BuildingExplodeClip -> "environmental/buildingExplode",
      CrashClip -> "environmental/crash",
      ExplosionClip -> "environmental/bigExplosion",
      GameOver -> "alerts/gameOver",
      ReloadClip -> "weapons/loadClip",
      Ambient -> "background/ambient",
      MachineGunClip -> "weapons/machineGun",
      MiniCannonClip -> "weapons/44",
      MissileClip -> "weapons/missile",
      RewardClip -> "items/reward")
    Map(samples map {
      case (key, name) => (key, loadSample(s"/audio/$name.wav"))
    }: _*)
  }

  private def loadSample(resource: String) = {
    println(s"Loading audio sample '$resource'...")
    loadAudioSample(ContentManager.getResource(resource))
  }

  /**
   * Audio-Clip Playback Actor
   * @author lawrence.daniels@gmail.com
   */
  class AudioPlaybackActor extends Actor {
    def receive = {
      case InitAudio =>
        println("Initializing sound system...")
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
  case object InitAudio
  case object Ambient extends AudioKey
  case object BuildingExplodeClip extends AudioKey
  case object BigExplosionClip extends AudioKey
  case object CrashClip extends AudioKey
  case object ExplosionClip extends AudioKey
  case object GameOver extends AudioKey
  case object MachineGunClip extends AudioKey
  case object MiniCannonClip extends AudioKey
  case object MissileClip extends AudioKey
  case object PlasmaClip extends AudioKey
  case object ReloadClip extends AudioKey
  case object RewardClip extends AudioKey

}