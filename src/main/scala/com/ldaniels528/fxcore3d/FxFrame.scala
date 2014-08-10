package com.ldaniels528.fxcore3d

import java.awt.{Toolkit, Dimension, GridLayout}
import javax.swing.{JFrame, JPanel}

/**
 * FxEngine Window Frame
 * @author lawrence.daniels@gmail.com
 */
class FxFrame(title: String, windowed: Boolean) extends JFrame(title) {
  super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  super.setContentPane({
    val cp = new JPanel(true)
    cp.setLayout(new GridLayout(1, 1))
    cp.setPreferredSize(getScreenDimensions)
    cp
  })
  super.pack()
  super.setVisible(true)

  private def getScreenDimensions: Dimension = {
    if (windowed) new Dimension(1024, 768)
    else {
      val frameSize = Toolkit.getDefaultToolkit.getScreenSize
      val insets = super.getInsets
      val width = frameSize.width - (insets.left + insets.right)
      val height = frameSize.height - (insets.top + insets.bottom)
      new Dimension(width, height)
    }
  }

}
