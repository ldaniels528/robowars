package com.ldaniels528.robowars.actors

import com.ldaniels528.fxcore3d.{FxAngle3D, FxEvent, FxObject, FxPoint3D, FxVelocityVector, FxWorld}
import com.ldaniels528.robowars.AbstractMovingObject
import com.ldaniels528.robowars.events.{Events, SteeringCommand, WeaponCommand}
import com.ldaniels528.robowars.structures.{AbstractMovingStructure, AbstractStaticStructure}
import com.ldaniels528.robowars.weapons.{AbstractRound, AbstractWeapon}

/**
 * Represents an abstract moving vehicle
 * @param world
 * @param pos
 * @param vector
 * @param turningRate
 * @param pitchRate
 * @param acceleration
 * @param brakingRate
 * @param maxVelocity
 * @param climbRate
 * @param decentRate
 * @param pitchClimbRateRelation
 * @param health
 * @author lawrence.daniels@gmail.com
 */
class AbstractVehicle(world: FxWorld,
                      pos: FxPoint3D,
                      vector: FxVelocityVector,
                      turningRate: Double,
                      pitchRate: Double = 0,
                      acceleration: Double,
                      brakingRate: Double,
                      maxVelocity: Double,
                      climbRate: Double = 0,
                      decentRate: Double = 0,
                      pitchClimbRateRelation: Double = 0,
                      health: Double)
  extends AbstractMovingObject(world, pos, vector.getAngle(), vector, new FxAngle3D(), health) with Events {

  private var weaponIndex = 0
  private val weapons = collection.mutable.Buffer[AbstractWeapon]()
  private var myLastPos: FxPoint3D = _
  private var myLastAgl: FxAngle3D = _

  override def interestedOfCollisionWith(obj: FxObject) = {
    obj match {
      case s: AbstractStaticStructure => true
      case m: AbstractMovingStructure => true
      case r: AbstractRound => true
      case v: AbstractVehicle => true
      case _ => super.interestedOfCollisionWith(obj)
    }
  }

  override def handleCollisionWith(obj: FxObject, dt: Double) = {
    obj match {
      case mv: AbstractMovingStructure =>
        oldStates()
        true
      case ss: AbstractStaticStructure =>
        oldStates()
        true
      case r: AbstractRound =>
        // check if the round comes from this actor
        if (r.shooter == this) true
        else {
          if (damageHealth(r.getImpactDamage()) < 0) die()
          false
        }
      case v: AbstractVehicle =>
        oldStates()
        true
      case _ => super.handleCollisionWith(obj, dt)

    }
  }

  private def oldStates() {
    setPosition(myLastPos)
    setAngle(myLastAgl)
    dPos.setVelocity(0)
  }

  def addWeapon(weapon: AbstractWeapon) {
    weapons += weapon
    ()
  }

  def removeWeapon(weapon: AbstractWeapon) {
    weapons += weapon
    ()
  }

  def selectedWeapon: AbstractWeapon = {
    weapons(weaponIndex)
  }

  def switchWeapons(): AbstractWeapon = {
    if (weaponIndex + 1 < weapons.size) weaponIndex += 1 else weaponIndex = 0
    weapons(weaponIndex)
  }

  def selectWeapon(weaponIndex: Int) {
    this.weaponIndex = weaponIndex
  }

  override def update(dt: Double) {
    myLastPos = getPosition()
    myLastAgl = getAngle()

    super.update(dt)

    // -- synchronize this object's angle with the
    // -- angle in the velocity vector
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    val a = getAngle()
    v.synchronizeAngle(a)
    setAngle(a)

    // -- update the weapons
    weapons.foreach(_.update(dt))
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
          case SELECT => selectWeapon(arg - 20)
          case FIRE => selectedWeapon.fire
          case code =>
            throw new IllegalArgumentException(s"Unhandled weapon command ($code)")
        }
        true

      case _ => super.handleEvent(event)
    }
  }

  protected def handleTurnLeft(factor: Double, dt: Double) {
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    v.increaseAngleAboutYaxis(turningRate * factor * dt)
    setdPosition(v)
  }

  protected def handleTurnRight(factor: Double, dt: Double) {
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    v.increaseAngleAboutYaxis(-turningRate * factor * dt)
    setdPosition(v)
  }

  protected def handleIncreaseVelocity(factor: Double, dt: Double) {
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    if (v.getVelocity() < maxVelocity) {
      v.increaseVelocity(acceleration * factor * dt)
      setdPosition(v)
    }
  }

  protected def handleDecreaseVelocity(factor: Double, dt: Double) {
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    if (v.getVelocity() > -maxVelocity) {
      v.increaseVelocity(-acceleration * factor * dt)
      setdPosition(v)
    }
  }

  protected def handleBrake(factor: Double, dt: Double) {
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    if (v.getVelocity() > 0) v.increaseVelocity(-brakingRate * factor * dt)
    else if (v.getVelocity() < 0) v.setVelocity(0)
    setdPosition(v)
  }

  protected def handlePitch(factor: Double, dt: Double) {
    val v = getdPosition().asInstanceOf[FxVelocityVector]
    v.increasePitch(-pitchRate * factor * dt)
    setdPosition(v)
  }

  protected def handleClimb(factor: Double, dt: Double) {
    val p = getPosition()
    p.y += climbRate * dt * factor
    setPosition(p)
  }

  protected def handleDecent(factor: Double, dt: Double) {
    val p = getPosition()
    p.y -= climbRate * dt * factor
    setPosition(p)
  }
}
