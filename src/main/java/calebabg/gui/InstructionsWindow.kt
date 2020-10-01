package calebabg.gui

import calebabg.main.Utils
import calebabg.main.iconImage
import java.awt.BorderLayout
import java.awt.Font
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

class InstructionsWindow private constructor(parent: JFrame?, w: Int, h: Int, title: String, instructions: String) {

    companion object {
        private var instructionsWindow: InstructionsWindow? = null

        lateinit var frame: JFrame
        private val mvInstructions: String = Utils.getHtml("/MVInstructions.html")!!

        fun getInstance(parent: JFrame?) {
            if (instructionsWindow == null) {
                instructionsWindow = InstructionsWindow(parent, 855, 490, "Music Visualizer Instructions", mvInstructions)
            }
            frame.toFront()
        }
    }

    init {
        Utils.setLookAndFeel()

        createGui(title, w, h, parent, instructions)

        frame.isVisible = true
    }

    private fun createGui(title: String, w: Int, h: Int, parent: JFrame?, instructions: String) {
        frame = JFrame(title)
        frame.iconImage = iconImage
        frame.setSize(w, h)
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE

        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                close()
            }
        })

        frame.setLocationRelativeTo(parent)
        frame.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent) {
                close(e)
            }
        })

        val panel = JPanel()
        panel.layout = BorderLayout()
        frame.contentPane.add(panel)

        val label = JLabel(instructions)
        label.font = Font(Font.SERIF, Font.PLAIN, 18)
        label.horizontalAlignment = SwingConstants.LEFT

        val scrollPane = JScrollPane()
        scrollPane.viewport.add(label)
        panel.add(scrollPane, BorderLayout.CENTER)
    }

    private fun close() {
        frame.dispose()
        instructionsWindow = null
    }

    private fun close(keyEvent: KeyEvent) {
        if (keyEvent.keyCode == KeyEvent.VK_ESCAPE) {
            close()
        }
    }
}
