package org.combat.game.util

/**
 * Resource Utility
 * @author lawrence.daniels@gmail.com
 */
object ResourceUtil {
  import scala.language.reflectiveCalls

  implicit class AutoClose[T <: { def close() }](closable: T) {

    def use[S](block: T => S): S = {
      try {
        block(closable)
      } finally {
        closable.close()
      }
    }

  }

}