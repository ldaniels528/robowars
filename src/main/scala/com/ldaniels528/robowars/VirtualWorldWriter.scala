package com.ldaniels528.robowars

import com.ldaniels528.robowars.objects.structures.Structure
import com.ldaniels528.robowars.objects.structures.fixed._
import com.ldaniels528.robowars.objects.structures.moving.{AbstractMovingDoor, MainGate}
import com.ldaniels528.robowars.objects.vehicles.AbstractVehicle

import scala.xml._

/**
 * Virtual World Writer
 * @author lawrence.daniels@gmail.com
 */
object VirtualWorldWriter {

  def save(world: VirtualWorld) {
    val player = world.activePlayer

    // extract the vehicles first
    val objects = world.myObjects.toSeq flatMap {
      case actor: AbstractVehicle => Some(encodeActor(actor, actor == player))
      case door: MainGate => Some(encodeMovingDoor(door))
      case structure: Structure => Some(encodeStructure(structure))
      case _ => None
    }

    println(constructDocument(objects))
  }

  private def encodeActor(actor: AbstractVehicle, isPlayer: Boolean): (String, String, Node) = {
    val p = actor.position
    val id = actor.getClass.getSimpleName
    val className = actor.getClass.getName
    val node = <actor type={id} x={p.x.toString} y={p.y.toString} z={p.z.toString} isPlayer={isPlayer.toString}/>;
    (id, className, node)
  }

  private def encodeStructure(structure: Structure): (String, String, Node) = {
    val (w, p, a, s) = structure match {
      case Building1(world, pos, agl, dim) => (world, pos, agl, dim)
      case Pillar(world, pos, agl, dim) => (world, pos, agl, dim)
      case Tower(world, pos, agl, dim) => (world, pos, agl, dim)
      case Wall1(world, pos, agl, dim) => (world, pos, agl, dim)
      case x =>
        throw new IllegalStateException(s"Class type ${x.getClass.getName} not recognized")
    }
    val id = structure.getClass.getSimpleName
    val className = structure.getClass.getName
    val node = <structure type={id}
                          x={p.x.toString} y={p.y.toString} z={p.z.toString}
                          rx={a.x.toString} ry={a.y.toString} rz={a.z.toString}
                          width={s.w.toString} height={s.h.toString} depth={s.d.toString}/>;
    (id, className, node)
  }

  private def encodeMovingDoor(door: AbstractMovingDoor): (String, String, Node) = {
    val p = door.Pos
    val w = MainGate.SCALE.w.toString
    val d = MainGate.SCALE.d.toString
    val h = MainGate.SCALE.h.toString
    val id = door.getClass.getSimpleName
    val className = door.getClass.getName
    val node = <door type={id} x={p.x.toString} y={p.y.toString} z={p.z.toString} rx="0.0" ry="0.0" rz="0.0"/>;
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

      <!-- object definitions -->{objects.groupBy(_._1) map { case (id, seq) => <objectDef id={id} class={seq.head._2}/>}}<!-- actor definitions -->{objects map { case (_, _, xml) => xml}}
    </world>
  }

}