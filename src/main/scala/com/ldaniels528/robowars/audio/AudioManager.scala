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
  val audioPlayers = (1 to 10) map (n => system.actorOf(Props[AudioPlaybackActor], name = s"audioPlayer$n"))
  var ticker = 0

  // allows background audio playback to be turned on/off
  var playbackOn = true

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
      // background samples
      Ambient -> "background/ambient",
      ErokiaElementary -> "background/erokia_elementary",

      // audio clips
      BigExplosionClip -> "environmental/bigExplosion",
      BuildingExplodeClip -> "environmental/buildingExplode",
      CrashClip -> "environmental/crash",
      ExplosionClip -> "environmental/bigExplosion",
      GameOver -> "alerts/gameOver",
      GunImpact -> "environmental/gun_impact",
      GetReady -> "alerts/getReady",
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
    loadAudioSample(ContentManager.getResource(resource))
  }

  /**
   * Audio-Clip Playback Actor
   * @author lawrence.daniels@gmail.com
   */
  class AudioPlaybackActor() extends Actor {

    import scala.concurrent.ExecutionContext.Implicits._
    import scala.concurrent.Future

    def receive = {
      case audioKey: ContinuousAudioKey =>
        audioSampleCache.get(audioKey) foreach { sample =>
          Future {
            playContinuousSample(sample, isAlive = playbackOn)
          }
        }
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

  trait ContinuousAudioKey extends AudioKey

  case object Ambient extends ContinuousAudioKey

  case object ErokiaElementary extends ContinuousAudioKey

  case object BuildingExplodeClip extends AudioKey

  case object BigExplosionClip extends AudioKey

  case object CrashClip extends AudioKey

  case object ExplosionClip extends AudioKey

  case object GameOver extends AudioKey

  case object GetReady extends AudioKey

  case object GunImpact extends AudioKey

  case object MachineGunClip extends AudioKey

  case object MiniCannonClip extends AudioKey

  case object MissileClip extends AudioKey

  case object PlasmaClip extends AudioKey

  case object ReloadClip extends AudioKey

  case object RewardClip extends AudioKey

}