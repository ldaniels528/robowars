package com.ldaniels528.robowars.objects

/**
 * <h2>Damageable</h2>
 * This trait is implemented by instances within the virtual world that can be damaged
 * @author lawrence.daniels@gmail.com
 */
trait Damageable {
  self: {def initialHealth: Double} =>

  var health: Double = self.initialHealth

  def maxHealth = initialHealth

  /**
   * Causes the host's health to be reduced by the given damage amount
   * @param damage the given damage amount
   * @return the remaining health amount
   */
  def damageHealth(damage: Double): Double = {
    health -= damage
    if (health > 5) health = 5
    health
  }

}
