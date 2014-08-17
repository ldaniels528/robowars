package com.ldaniels528.robowars

import java.net.Socket

import com.ldaniels528.robowars.RoboWarsShell.{logger, _}
import com.ldaniels528.robowars.net.{Client, ClientSideProcessor, Transmitter}
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits._
import scala.util.Try

/**
 * RoboWars Shell Client
 * @author lawrence.daniels@gmail.com
 */
class RoboWarsShell(val client: Client, host: String) extends ClientSideProcessor with Transmitter {
  private val commandSet = generateCommands
  private val out = System.out
  private var world: Option[VirtualWorld] = None
  var alive: Boolean = true

  // schedule the updates
  scheduleUpdates()

  // retrieve the virtual world from the server
  {
    logger.info("Retrieving the virtual world from the remote peer...")
    val startTime = System.nanoTime()
    getRemoteWorld(client) foreach { theWorld =>
      val endTime = System.nanoTime()
      val elapsed = (endTime - startTime).toDouble / 1e+6
      logger.info(f"Loaded virtual world $theWorld in $elapsed%.1f msec")
      theWorld.update(.02)
      world = Some(theWorld)
    }
  }

  /**
   * Interactive shell
   */
  def shell() {
    while (alive) {
      // read a line of input
      out.print(s"$host> ")
      val input = scala.io.StdIn.readLine().trim
      if (input.nonEmpty) {
        interpretCommand(input)
      }
    }
  }

  /**
   * "dump" - displays the contents of the world as XML
   * Example: dump
   */
  private def dumpWorld(args: Seq[String]): Unit = {
    world.foreach { w =>
      out.println(VirtualWorldWriter.save(w).toString())
    }
  }

  /**
   * "help" - displays all commands
   * Example: help
   */
  private def help(args: Seq[String]) {
    commandSet.keys.toSeq foreach out.println
  }

  /**
   * "join" - retrieves a virtual world by level ID
   * Example: join
   */
  private def joinGame(args: Seq[String]) = {
    world match {
      case Some(vw) => out.println("You have already joined")
      case None =>
        logger.info("Attempting to join game session...")
        if (world.isEmpty) {
          // schedule the updates
          scheduleUpdates()
        }

        getRemoteWorld(client) foreach { theWorld =>
          logger.info(s"Loaded virtual world $theWorld")
          theWorld.update(.02)
          world = Some(theWorld)
        }
    }
  }

  /**
   * "actors" - retrieves a list of the actors in the virtual world
   * Example: actors
   */
  private def showActors(args: Seq[String]): Unit = {
    world match {
      case Some(vw) =>
        val actors = vw.myObjects.filter(_.isInstanceOf[AbstractVehicle])
        if (actors.isEmpty) out.println("No actors found")
        else actors foreach out.println
      case None => out.println("You have not joined. Use the 'join' command.")
    }
  }

  /**
   * "update" - updates the virtual world
   * Example: update <deltaTime>
   */
  private def updateWorld(args: Seq[String]): Unit = {
    val dt = args.headOption map (_.toDouble) getOrElse .02d
    world.foreach { w =>
      logger.info(f"Updating world by $dt%.2f seconds")
      w.update(dt)
    }
  }

  private def generateCommands: Map[String, Command] = {
    Map(Seq(
      Command("?", help),
      Command("actors", showActors),
      Command("dump", dumpWorld),
      Command("help", help),
      Command("join", joinGame),
      Command("update", updateWorld)) map (c => c.name -> c): _*)
  }

  private def interpretCommand(input: String) = {
    // parse & evaluate the user input
    Try(parseArgs(input) match {
      case (cmd, args) =>
        // match the command
        commandSet.get(cmd) match {
          case Some(command) =>
            command.fx(args)
          case _ =>
            throw new IllegalArgumentException(s"'$input' not recognized")
        }
      case _ =>
    })
  }

  private def parseArgs(input: String): (String, Seq[String]) = {
    val tokens = parseInput(input)
    (tokens.head, tokens.tail)
  }

  /**
   * Parses the given input string into tokens
   */
  private def parseInput(input: String): Seq[String] = {
    val SYMBOLS = Set('!', '@', '?', '&')
    val sb = new StringBuilder()
    var inQuotes = false

    // extract the tokens
    val list = input.foldLeft[List[String]](Nil) { (list, ch) =>
      val result: Option[String] = ch match {
        // symbol (unquoted)?
        case c if SYMBOLS.contains(c) && !inQuotes =>
          val s = sb.toString()
          sb.clear()
          if (s.isEmpty) Some(String.valueOf(c))
          else {
            sb += c
            Some(s)
          }

        // quote
        case '"' =>
          inQuotes = !inQuotes
          None

        // space (unquoted)?
        case c if c == ' ' && !inQuotes =>
          if (sb.nonEmpty) {
            val s = sb.toString()
            sb.clear()
            Some(s)
          } else None

        // any other character
        case c =>
          sb += c
          None
      }

      result map (_ :: list) getOrElse list
    }

    // add the last token
    (if (sb.nonEmpty) sb.toString :: list else list).reverse
  }

}

/**
 * RoboWars Shell Client
 * @author lawrence.daniels@gmail.com
 */
object RoboWarsShell {
  private val logger = LoggerFactory.getLogger(getClass)

  /**
   * Main application entry-point
   * @param args the given command line arguments
   */
  def main(args: Array[String]) {
    // get the command line arguments
    val host = args.headOption getOrElse "localhost"
    val port = if (args.length > 1) args(1).toInt else 8855

    // connect to the remote host
    logger.info(s"Connecting to remote peer $host:$port...")
    val client = new Client(new Socket(host, port))

    // get the system reported hostname
    val systemHost = getHostname getOrElse host

    // accept input for the user
    val app = new RoboWarsShell(client, systemHost)
    app.shell()
  }

  private def getHostname: Try[String] = {
    Try(java.net.InetAddress.getLocalHost) map (_.getHostName)
  }

  case class Command(name: String, fx: Seq[String] => Any)

}
