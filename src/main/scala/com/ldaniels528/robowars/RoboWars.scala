package com.ldaniels528.robowars

import java.awt._
import java.awt.event.{KeyEvent, KeyListener}

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.camera._
import com.ldaniels528.robowars.RoboWars._
import com.ldaniels528.robowars.actors.AbstractActor
import com.ldaniels528.robowars.events.{Events, WeaponCommand}

/**
 * RoboWars Application Main
 * @author lawrence.daniels@gmail.com
 */
class RoboWars() extends FxFrame("RoboWars") with Events {
  var world: FxWorld = _
  var camera: FxTrackerCamera = _
  val key = new Array[Boolean](100)
  var alive: Boolean = true
  var fps: Double = 0.0

  // the off-screen buffer variables
  var buffer: Image = _
  var offScreen: Graphics2D = _
  var theScreen: Graphics2D = _
  var screenDim: Dimension = _

  // setup the key listener
  super.addKeyListener(new KeyListener {
    override def keyTyped(event: KeyEvent) = ()

    override def keyReleased(event: KeyEvent) = keyboardEvent(event, false)

    override def keyPressed(event: KeyEvent) = keyboardEvent(event, true)
  })

  def init() {
    // get the dimensions of the content pane
    val contentPane = super.getContentPane()
    val dim = contentPane.getSize()

    // create the image buffer and graphics context
    buffer = createImage(dim.width, dim.height)
    offScreen = buffer.getGraphics().asInstanceOf[Graphics2D]
    offScreen.setFont(FONT12)
    theScreen = contentPane.getGraphics().asInstanceOf[Graphics2D]
    screenDim = dim

    // load the audio samples
    import com.ldaniels528.robowars.audio.AudioManager._
    audioPlayer ! Init

    // load the world
    world = VirtualWorldReader.load("/worlds/world_0001.xml")
    camera = createCamera(world)
  }

  def run() {
    var lastUpdate: Long = System.currentTimeMillis()
    var frames: Int = 0
    var timeMillis: Long = 0

    while (alive) {
      // determine the elapsed time between frames
      val currentTime = System.currentTimeMillis()
      val dtMillis = currentTime - lastUpdate
      lastUpdate = currentTime

      // compute the delta time
      val dt = Math.min(0.2, dtMillis.toDouble / 1000d)

      // handle keyboard events
      handleKeyboardEvents(dt)

      // update the world
      world.update(dt)

      // update the display
      renderScene(dt, screenDim)

      // calculate frame rate once a second
      frames += 1
      timeMillis += dtMillis
      if (timeMillis >= 1000) {
        fps = (1000d * frames.toDouble / timeMillis.toDouble)
        timeMillis = 0
        frames = 0
      }
    }
  }

  private def renderScene(dt: Double, dim: Dimension): Boolean = {
    val player = world.activePlayer
    val p = player.position

    // update the camera
    camera.update(dt)

    // render the scene off-screen
    offScreen.clearRect(0, 0, dim.width, dim.height)
    camera.setScreenSize(dim.width, dim.height)
    camera.paint(offScreen)

    // render the cycle time
    offScreen.setColor(new Color(0xFF, 0x80, 0x00))
    offScreen.drawString(f"pos = $p,  dt = $dt%.3f", dim.width - 300, dim.height - 25)

    // render the heads-up display
    renderHUD(player)

    // render the frame rate off-screen
    renderFrameRate(dim)

    // is the player dead?
    if (!player.isAlive) {
      offScreen.setColor(Color.RED)
      offScreen.setFont(FONT32)
      offScreen.drawString("You are Dead", dim.width / 2 - (dim.width / 4), dim.height / 2)
      offScreen.setFont(FONT12)
    }

    // paint the screen
    theScreen.drawImage(buffer, 0, 0, this)
  }

  private def renderHUD(player: AbstractActor) {
    val XPOS = 5
    val YPOS = 25
    val BAR_WIDTH = 300
    val BAR_HEIGHT = 10
    val healthPct = player.health / 5d
    val healthBar = (BAR_WIDTH.toDouble * healthPct).toInt

    // cache the selected weapon
    val weapon = player.selectedWeapon

    // display the weapon
    offScreen.setColor(Color.WHITE)
    offScreen.drawString(s"${weapon.name} x ${weapon.ammo}", 5, 15)

    // fill the health meter
    offScreen.setColor(healthPct match {
      case n if n >= 0.75 => Color.GREEN
      case n if n >= 0.25 => Color.YELLOW
      case _ => Color.RED
    })
    offScreen.fillRect(XPOS, YPOS, healthBar, BAR_HEIGHT)

    // draw the outline
    offScreen.setColor(Color.GRAY)
    offScreen.drawRect(XPOS, YPOS, BAR_WIDTH, BAR_HEIGHT)
  }

  private def renderFrameRate(dim: Dimension) {
    offScreen.setColor(fps match {
      case n if n >= 60 => Color.GREEN
      case n if n >= 30 => Color.YELLOW
      case _ => Color.RED
    })
    offScreen.drawString("%.1f".format(fps), dim.width - 50, 15)
  }

  private def keyboardEvent(event: KeyEvent, pressed: Boolean) {
    import java.awt.event.KeyEvent._

    key(INCREASE_VELOCITY) = event.isControlDown()
    key(DECREASE_VELOCITY) = event.isAltDown()
    key(FIRE) = event.isShiftDown()

    event.getKeyCode() match {
      case VK_LEFT => key(TURN_LEFT) = pressed
      case VK_RIGHT => key(TURN_RIGHT) = pressed
      case VK_G => key(BRAKE) = pressed
      case VK_Y => key(CLIMB) = pressed
      case VK_I => key(DECENT) = pressed
      case VK_UP => key(PITCH_DOWN) = pressed
      case VK_DOWN => key(PITCH_UP) = pressed
      case VK_Z =>
        if (pressed) world.activePlayer.switchWeapons(); ()
      case VK_0 =>
        val p = world.activePlayer.position
        println("Position  = (%.1f, %.1f, %.1f)".format(p.x, p.y, p.z))
      case VK_1 => key(MINICANNON) = pressed
      case VK_2 => key(MISSILE) = pressed
      case VK_3 => key(BOMB) = pressed
      case _ =>
      //println("KeyEvent: code [%d] char [%c]".format(event.getKeyCode(), event.getKeyChar()))
    }
  }

  private def handleKeyboardEvents(dt: Double) {
    // get the active player
    val player = world.activePlayer

    // control events
    if (key(TURN_LEFT)) player.turnLeft(1, 0.02)
    if (key(TURN_RIGHT)) player.turnRight(1, 0.02)
    if (key(DECREASE_VELOCITY)) player.decreaseVelocity(1, 0.1)
    if (key(INCREASE_VELOCITY)) player.increaseVelocity(1, 0.1)
    if (key(BRAKE)) player.brake(1, 0.1)
    if (key(CLIMB)) player.climb(1, 0.1)
    if (key(DECENT)) player.decent(1, 0.1)
    if (key(PITCH_UP)) player.pitchUp(1, 0.1)
    if (key(PITCH_DOWN)) player.pitchDown(1, 0.1)

    // weapon events
    if (key(FIRE)) player.fireSelectedWeapon()
    if (key(MINICANNON)) player += WeaponCommand(world.time, SELECT, MINICANNON)
    if (key(MISSILE)) player += WeaponCommand(world.time, SELECT, MISSILE)
    if (key(BOMB)) player += WeaponCommand(world.time, SELECT, BOMB)
  }

  private def createCamera(world: FxWorld): FxTrackerCamera = {
    // get the active player    
    val player = world.activePlayer

    // create the light source
    val light = new FxPoint3D(0, 0, -1)
    light.rotateAboutXaxis(-Math.PI / 4)
    light.rotateAboutYaxis(-Math.PI / 4)
    light.normalize(1)

    // create the camera
    val cam = new FxTrackerCamera(world, 1.2, 1000, 20, 2.0, player, FxAngle3D(), FxPoint3D(0, 4, 4))
    cam.setLightSource(light)
    cam
  }

}

/**
 * RoboWars Application Main
 * @author lawrence.daniels@gmail.com
 */
object RoboWars {
  private val FONT12 = new Font(Font.MONOSPACED, Font.BOLD, 12)
  private val FONT32 = new Font(Font.MONOSPACED, Font.BOLD, 50)

  /**
   * Main application entry-point
   * @param args the given command line arguments
   */
  def main(args: Array[String]) {
    val app = new RoboWars()
    app.init()
    app.run()
  }

}