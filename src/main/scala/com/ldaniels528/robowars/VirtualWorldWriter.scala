package com.ldaniels528.robowars

import com.ldaniels528.robowars.actors.AbstractActor
import com.ldaniels528.robowars.structures.{MainGate, AbstractDoor, AbstractStaticStructure}

/**
 * Virtual World Writer
 * @author lawrence.daniels@gmail.com
 */
object VirtualWorldWriter {

  import scala.xml._

  def save(world: VirtualWorld) {
    val player = world.activePlayer
    System.err.println(s"activePlayer = $player")

    // extract the actors first
    val objects = world.myObjects.toSeq flatMap { obj =>
      obj match {
        case actor: AbstractActor => Some(encodeActor(actor, actor == player))
        case structure: AbstractStaticStructure => Some(encodeStructure(structure))
        case door: MainGate => Some(encodeMovingDoor(door))
        case _ => None
      }
    }

    println(constructDocument(objects))
  }

  private def encodeActor(actor: AbstractActor, isPlayer: Boolean): (String, String, Node) = {
    val p = actor.pos
    val id = actor.getClass.getSimpleName
    val className = actor.getClass.getName
    val node = <actor type={id} x={p.x.toString} y={p.y.toString} z={p.z.toString} isPlayer={isPlayer.toString}/>;
    (id, className, node)
  }

  private def encodeStructure(structure: AbstractStaticStructure): (String, String, Node) = {
    val p = structure.Pos
    val h = p.y.toString
    val id = structure.getClass.getSimpleName
    val className = structure.getClass.getName
    val node = <structure type={id} x={p.x.toString} y={p.y.toString} z={p.z.toString} rx="0.0" ry="0.0" rz="0.0" width={h} depth={h} height={h} />;
    (id, className, node)
  }

  private def encodeMovingDoor(door: MainGate): (String, String, Node) = {
    val p = door.Pos
    val w = MainGate.SCALE.x.toString
    val d = MainGate.SCALE.z.toString
    val h = MainGate.SCALE.y.toString
    val id = door.getClass.getSimpleName
    val className = door.getClass.getName
    val node = <door type={id} x={p.x.toString} y={p.y.toString} z={p.z.toString} rx="0.0" ry="0.0" rz="0.0" />;
    (id, className, node)
  }

  private def constructDocument(objects: Seq[(String, String, Node)]): NodeSeq = {
    <world name="Untitled" minX="-500" minY="-500" size="1000" rows="2" gravity="-10">
      <!-- color definitions -->
      <colorDef id="daySky" red="50" green="50" blue="200"/>
      <colorDef id="eveningSky" red="50" green="50" blue="100"/>
      <colorDef id="nightSky" red="30" green="30" blue="50"/>

      <colorDef id="desertLand" red="170" green="170" blue="90"/>
      <colorDef id="pasture1" red="90" green="90" blue="0"/>
      <colorDef id="pasture2" red="50" green="50" blue="0"/>

      <!-- set sky and ground colors -->
      <environment sky="eveningSky" ground="pasture2"/>

      <!-- object definitions -->
      {objects.groupBy(_._1) map { case (id, seq) => <objectDef id={id} class={seq.head._2}/>}}

      <!-- actor definitions -->
      {objects map { case (_, _, xml) => xml}}
    </world>
  }

}