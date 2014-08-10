package com.ldaniels528.robowars

import java.awt._
import java.awt.event._

import com.ldaniels528.fxcore3d._
import com.ldaniels528.fxcore3d.camera._
import com.ldaniels528.robowars.RoboWars._
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.events.Events._
import com.ldaniels528.robowars.events.WeaponCommand
import com.ldaniels528.robowars.objects.Damageable
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

/**
 * RoboWars Application Main
 * @author lawrence.daniels@gmail.com
 */
class RoboWars(windowed: Boolean, noMusic: Boolean) extends FxFrame("RoboWars", windowed) {
  var world: FxWorld = _
  var camera: FxTrackerCamera = _
  val key = new Array[Boolean](20)
  var alive: Boolean = true
  var fps: Double = 0.0

  // the off-screen buffer variables
  lazy val controlKeysImage = ContentManager.loadImage("/images/control_keys.png")
  var buffer: Image = _
  var offScreen: Graphics2D = _
  var theScreen: Graphics2D = _
  var screenDim: Dimension = _

  // setup the key listener
  super.addKeyListener(new KeyListener {
    override def keyTyped(event: KeyEvent) = ()

    override def keyReleased(event: KeyEvent) = keyboardEvent(event, pressed = false)

    override def keyPressed(event: KeyEvent) = keyboardEvent(event, pressed = true)
  })

  // setup the window listener
  super.addComponentListener(new ComponentListener {
    override def componentShown(e: ComponentEvent): Unit = ()

    override def componentHidden(e: ComponentEvent): Unit = ()

    override def componentMoved(e: ComponentEvent): Unit = ()

    override def componentResized(e: ComponentEvent): Unit = {
      val dim = e.getComponent.getSize
      System.out.println(s"screenSize = $dim, comp = ${e.getComponent.getClass.getName}")

      // create the image buffer and graphics context
      buffer = createImage(dim.width, dim.height)
      offScreen = buffer.getGraphics.asInstanceOf[Graphics2D]
      offScreen.setFont(FONT12)
      screenDim = dim
    }
  })

  /**
   * Initializes the application
   */
  def init() {
    // get the dimensions of the content pane
    val contentPane = super.getContentPane
    val dim = contentPane.getSize

    // create the image buffer and graphics context
    buffer = createImage(dim.width, dim.height)
    offScreen = buffer.getGraphics.asInstanceOf[Graphics2D]
    offScreen.setFont(FONT12)
    theScreen = contentPane.getGraphics.asInstanceOf[Graphics2D]
    screenDim = dim

    // load the world
    world = VirtualWorldReader.load("/worlds/world_0001.xml")
    camera = createCamera(world)

    // start the player moving forward
    world.activePlayer.increaseVelocity(40, .2)
  }

  /**
   * Main application loop
   */
  def run() {
    var lastUpdate: Long = System.currentTimeMillis()
    var frames: Int = 0
    var timeMillis: Long = 0

    // initialize the audio player
    audioPlayer ! GetReady
    if (!noMusic) audioPlayer ! Ambient

    while (alive) {
      // determine the elapsed time between frames
      val currentTime = System.currentTimeMillis()
      val dtMillis = currentTime - lastUpdate
      lastUpdate = currentTime

      // compute the delta time
      val dt = dtMillis.toDouble / 1000d

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
        fps = 1000d * frames.toDouble / timeMillis.toDouble
        timeMillis = 0
        frames = 0
      }
    }
  }

  /**
   * Render the scene
   * @param dt the given frame time in seconds
   * @param dim the screen dimensions
   */
  private def renderScene(dt: Double, dim: Dimension) {
    // get the active player
    val player = world.activePlayer

    // update the camera
    camera.update(dt)

    // render the scene off-screen
    offScreen.clearRect(0, 0, dim.width, dim.height)
    camera.setScreenSize(dim.width, dim.height)
    camera.paint(offScreen)

    // render the heads-up display
    renderHUD(player, dt)

    // render the frame rate off-screen
    renderFrameRate(dim)

    // render any notices
    renderNotices(player)

    // paint the screen
    theScreen.drawImage(buffer, 0, 0, this)
    ()
  }

  private def renderNotices(player: AbstractVehicle) {
    if (world.time < 3d) {
      // show cursor key instructions
      val cursorCenterX = (super.getWidth - controlKeysImage.getWidth) / 2
      val textLeftX = cursorCenterX - 150
      offScreen.setFont(FONT50)

      // cursor key image & text
      offScreen.setColor(Color.LIGHT_GRAY)
      offScreen.drawImage(controlKeysImage, cursorCenterX, 50, this)
      offScreen.drawString("Left/Right to Navigate", textLeftX, 340)
      offScreen.drawString("CTRL/ALT to Accel/Decel", textLeftX, 385)
      offScreen.drawString("Shift to Fire weapon", textLeftX, 430)
      offScreen.setFont(FONT12)
    }

    // is the player dead?
    if (!player.isAlive) {
      offScreen.setColor(Color.RED)
      offScreen.setFont(FONT64)
      offScreen.drawString("Game Over", this.getWidth / 2 - (this.getWidth / 4), this.getHeight / 2)
      offScreen.setFont(FONT12)
    }
  }

  private def renderHUD(player: AbstractVehicle, dt: Double) {
    val X_POS = 5
    val Y_POS = 25
    val BAR_WIDTH = 300
    val BAR_HEIGHT = 10

    // cache the selected weapon
    val weapon = player.selectedWeapon

    // display the weapon
    offScreen.setColor(Color.WHITE)
    offScreen.drawString(s"${weapon.name} x ${weapon.ammo}", 5, 15)

    // fill the health meter
    player match {
      case d: Damageable =>
        val healthPct = d.health / d.maxHealth
        val healthBar = (BAR_WIDTH.toDouble * healthPct).toInt

        // set the appropriate color for the health level
        offScreen.setColor({
          if (healthPct >= 0.75) Color.GREEN
          else if (healthPct >= 0.25) Color.YELLOW
          else Color.RED
        })
        offScreen.fillRect(X_POS, Y_POS, healthBar, BAR_HEIGHT)

        // draw the outline
        offScreen.setColor(Color.GRAY)
        offScreen.drawRect(X_POS, Y_POS, BAR_WIDTH, BAR_HEIGHT)
      case _ =>
    }

    // render the cycle time
    val p = player.position
    offScreen.setColor(ORANGE_COLOR)
    offScreen.drawString(f"pos = $p,  dt = $dt%.3f", this.getWidth - 300, this.getHeight - 25)
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

    key(INCREASE_VELOCITY) = event.isControlDown
    key(DECREASE_VELOCITY) = event.isAltDown
    key(FIRE) = event.isShiftDown

    event.getKeyCode match {
      case VK_LEFT => key(TURN_LEFT) = pressed
      case VK_RIGHT => key(TURN_RIGHT) = pressed
      case VK_G => key(BRAKE) = pressed
      case VK_Y => key(CLIMB) = pressed
      case VK_I => key(DECENT) = pressed
      case VK_UP => key(PITCH_DOWN) = pressed
      case VK_DOWN => key(PITCH_UP) = pressed
      case VK_Z =>
        if (pressed) world.activePlayer.switchWeapons(); ()
      case VK_1 => key(MACHINE_GUN) = pressed
      case VK_2 => key(MINI_CANNON) = pressed
      case VK_3 => key(MISSILE) = pressed
      case VK_4 => key(BOMB) = pressed
      case _ =>
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
    if (key(MACHINE_GUN)) player += WeaponCommand(world.time, SELECT, MACHINE_GUN)
    if (key(MINI_CANNON)) player += WeaponCommand(world.time, SELECT, MINI_CANNON)
    if (key(MISSILE)) player += WeaponCommand(world.time, SELECT, MISSILE)
    if (key(BOMB)) player += WeaponCommand(world.time, SELECT, BOMB)
  }

  private def createCamera(world: FxWorld): FxTrackerCamera = {
    // get the active player    
    val player = world.activePlayer

    // create the light source
    val light = new FxPoint3D(0, 0, -1)
    light.rotateAboutAxisX(-Math.PI / 4)
    light.rotateAboutAxisY(-Math.PI / 4)
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
  // font definitions
  private val FONT12 = new Font(Font.MONOSPACED, Font.BOLD, 12)
  private val FONT50 = new Font(Font.MONOSPACED, Font.BOLD, 50)
  private val FONT64 = new Font(Font.MONOSPACED, Font.BOLD, 64)

  // color definitions
  private val ORANGE_COLOR = new Color(0xFF, 0x80, 0x00)

  /**
   * Main application entry-point
   * @param args the given command line arguments
   */
  def main(args: Array[String]) {
    // check for command line arguments
    val noMusic = args.contains("no_music")
    val windowed = args.contains("windowed")

    // start the application
    val app = new RoboWars(windowed, noMusic)
    app.init()
    app.run()
  }

}