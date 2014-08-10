package com.ldaniels528.robowars.objects.vehicles

import com.ldaniels528.fxcore3d._
import com.ldaniels528.robowars.audio.AudioManager._
import com.ldaniels528.robowars.events.EventCommands._
import com.ldaniels528.robowars.events.{SteeringCommand, WeaponCommand}
import com.ldaniels528.robowars.objects.ai.AbstractAI
import com.ldaniels528.robowars.objects.items.RewardItem
import com.ldaniels528.robowars.objects.structures.Structure
import com.ldaniels528.robowars.objects.weapons.{AbstractProjectile, AbstractWeapon}
import com.ldaniels528.robowars.objects.{Damageable, Destructible}

/**
 * Represents an abstract moving vehicle
 * @param world the virtual world
 * @param pos the vehicle's position in space
 * @param vector the vehicle's current vector
 * @param initialHealth the vehicle's initial health
 * @author lawrence.daniels@gmail.com
 */
abstract class AbstractVehicle(world: FxWorld, pos: FxPoint3D, vector: FxVelocityVector, initialHealth: Double = 5d)
  extends FxMovingObject(world, pos, vector.angle, vector, FxAngle3D())
  with Damageable
  with Destructible {

  private var weaponIndex = 0
  private val weapons = collection.mutable.Buffer[AbstractWeapon]()
  private var myLastPos: FxPoint3D = _
  private var myLastAgl: FxAngle3D = _
  var cpu: Option[AbstractAI] = None

  def maxHealth: Double = initialHealth

  def turningRate: Double

  def pitchRate: Double

  def acceleration: Double

  def brakingRate: Double

  def maxVelocity: Double

  def climbRate: Double

  def decentRate: Double

  def pitchClimbRateFactor: Double

  override def die() {
    // leave the carcass behind
    new VehicleRuins(world, this)

    // display the fragments
    explodeIntoFragments(fragments = 15, size = 0.25, speed = 25, spread = 2d)

    // play the explosion clip
    if (!this.isInstanceOf[AbstractProjectile]) {
      audioPlayer ! (if (this == world.activePlayer) GameOver else BigExplosionClip)
    }

    // allow super-class to take action
    super.die()
  }

  override def handleCollisionWith(obj: FxObject, dt: Double) = {
    obj match {
      case i: RewardItem =>
        if (i.isAlive) {
          audioPlayer ! RewardClip
          this.damageHealth(-maxHealth)
          i.die()
        }
        true
      case s: Structure =>
        oldStates()
        true
      case p: AbstractProjectile =>
        // check if the round comes from this actor
        if (p.shooter == this) true
        else {
          if (damageHealth(p.impactDamage) < 0) die() else audioPlayer ! CrashClip
          false
        }
      case v: AbstractVehicle =>
        oldStates()
        true
      case _ => super.handleCollisionWith(obj, dt)
    }
  }

  override def interestedOfCollisionWith(obj: FxObject) = {
    obj match {
      case i: RewardItem => true
      case r: AbstractProjectile => true
      case s: Structure => true
      case v: AbstractVehicle => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  private def oldStates() {
    setPosition(myLastPos)
    setAngle(myLastAgl)
    dPos.setVelocity(0)
  }

  def +=(weapon: AbstractWeapon) = weapons += weapon

  def -=(weapon: AbstractWeapon) = weapons += weapon

  def selectedWeapon: AbstractWeapon = weapons(weaponIndex)

  def switchWeapons(): AbstractWeapon = {
    if (weaponIndex + 1 < weapons.size) weaponIndex += 1 else weaponIndex = 0
    weapons(weaponIndex)
  }

  def selectWeapon(weaponIndex: Int) {
    this.weaponIndex = if (weaponIndex < weapons.length) weaponIndex else weapons.length - 1
  }

  override def update(dt: Double) {
    myLastPos = position
    myLastAgl = angle

    // -- synchronize this object's angle with the
    // -- angle in the velocity vector
    setAngle({
      val v = getdPosition()
      val a = angle
      v.synchronizeAngle(a)
      a
    })

    // update the CPU
    cpu.foreach(_.update(dt))

    // update the weapons
    weapons.foreach(_.update(dt))

    // allow the super-class to update
    super.update(dt)
  }

  def fireSelectedWeapon() = this += WeaponCommand(world.time, FIRE, 0)

  def turnLeft(factor: Double, dt: Double) = this += SteeringCommand(world.time, TURN_LEFT, factor, dt)

  def turnRight(factor: Double, dt: Double) = this += SteeringCommand(world.time, TURN_RIGHT, factor, dt)

  def increaseVelocity(factor: Double, dt: Double) = this += SteeringCommand(world.time, INCREASE_VELOCITY, factor, dt)

  def decreaseVelocity(factor: Double, dt: Double) = this += SteeringCommand(world.time, DECREASE_VELOCITY, factor, dt)

  def brake(factor: Double, dt: Double) = this += SteeringCommand(world.time, BRAKE, factor, dt)

  def climb(factor: Double, dt: Double) = this += SteeringCommand(world.time, CLIMB, factor, dt)

  def decent(factor: Double, dt: Double) = this += SteeringCommand(world.time, DECENT, factor, dt)

  def pitchUp(factor: Double, dt: Double) = this += SteeringCommand(world.time, PITCH_UP, factor, dt)

  def pitchDown(factor: Double, dt: Double) = this += SteeringCommand(world.time, PITCH_DOWN, -factor, dt)

  override protected def handleEvent(event: FxEvent) = {
    event match {
      case SteeringCommand(_, command, factor, dt) =>
        command match {
          case TURN_LEFT => handleTurnLeft(factor, dt)
          case TURN_RIGHT => handleTurnRight(factor, dt)
          case INCREASE_VELOCITY => handleIncreaseVelocity(factor, dt)
          case DECREASE_VELOCITY => handleDecreaseVelocity(factor, dt)
          case BRAKE => handleBrake(factor, dt)
          case CLIMB => handleClimb(factor, dt)
          case DECENT => handleDecent(factor, dt)
          case PITCH_DOWN => handlePitch(factor, dt)
          case PITCH_UP => handlePitch(factor, dt)
          case code =>
            throw new IllegalArgumentException(s"Unhandled steering command ($code)")
        }
        true

      case WeaponCommand(_, command, arg) =>
        command match {
          case SELECT => selectWeapon(arg - WEAPON_CODE)
          case FIRE => selectedWeapon.fire()
          case code =>
            throw new IllegalArgumentException(s"Unhandled weapon command ($code)")
        }
        true

      case _ => super.handleEvent(event)
    }
  }

  protected def handleTurnLeft(factor: Double, dt: Double) {
    val v = getdPosition()
    v.increaseAngleAboutAxisY(turningRate * factor * dt)
    setdPosition(v)
  }

  protected def handleTurnRight(factor: Double, dt: Double) {
    val v = getdPosition()
    v.increaseAngleAboutAxisY(-turningRate * factor * dt)
    setdPosition(v)
  }

  protected def handleIncreaseVelocity(factor: Double, dt: Double) {
    val v = getdPosition()
    if (v.velocity < maxVelocity) {
      v.increaseVelocity(acceleration * factor * dt)
      setdPosition(v)
    }
  }

  protected def handleDecreaseVelocity(factor: Double, dt: Double) {
    val v = getdPosition()
    if (v.velocity > -maxVelocity) {
      v.increaseVelocity(-acceleration * factor * dt)
      setdPosition(v)
    }
  }

  protected def handleBrake(factor: Double, dt: Double) {
    val v = getdPosition()
    if (v.velocity > 0) v.increaseVelocity(-brakingRate * factor * dt)
    else if (v.velocity < 0) v.setVelocity(0)
    setdPosition(v)
  }

  protected def handlePitch(factor: Double, dt: Double) {
    val v = getdPosition()
    v.increasePitch(-pitchRate * factor * dt)
    setdPosition(v)
  }

  protected def handleClimb(factor: Double, dt: Double) {
    val p = position
    p.y += climbRate * dt * factor
    setPosition(p)
  }

  protected def handleDecent(factor: Double, dt: Double) {
    val p = position
    p.y -= climbRate * dt * factor
    setPosition(p)
  }

}
