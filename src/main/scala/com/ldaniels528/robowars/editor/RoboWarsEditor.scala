package com.ldaniels528.robowars.editor

import java.awt.Dimension
import javax.swing.JFrame

/**
 * RoboWars Editor
 * @author lawrence.daniels@gmail.com
 */
class RoboWarsEditor() extends JFrame("RoboWars Editor") {
  super.setSize(new Dimension(1024, 768))
  super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  super.setVisible(true)

  def run(): Unit = {

  }

}

/**
 * RoboWars Editor Companion Object
 * @author lawrence.daniels@gmail.com
 */
object RoboWarsEditor {

  def main(args: Array[String]) {
    val editor = new RoboWarsEditor()
    editor.run()
  }

}
