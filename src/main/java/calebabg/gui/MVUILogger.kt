package calebabg.gui

import calebabg.helpers.Utils
import calebabg.main.iconImage
import javax.swing.*
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date

class MVUILogger private constructor(parent: JFrame?) {

    companion object {
        private var mvuiLogger: MVUILogger? = null

        lateinit var frame: JFrame
        private val textArea = JTextArea()

        fun getInstance(parent: JFrame?) {
            if (mvuiLogger == null) {
                mvuiLogger = MVUILogger(parent)
            }
            frame.toFront()
        }

        private fun getStackTrace(e: Exception): String {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            return sw.toString()
        }

        private fun logText(s: String) {
            textArea.append(s + "\n")
        }

        private fun setText(s: String) {
            textArea.text = s
        }

        fun setException(except: Exception) {
            val builder = getGenericExceptionStringBuilder(except)
            textArea.text = builder.toString()
        }

        fun logException(except: Exception) {
            val builder = getGenericExceptionStringBuilder(except)
            textArea.append(builder.toString())
        }

        private fun getGenericExceptionStringBuilder(except: Exception): StringBuilder {
            val builder = getStringBuilder(300, SimpleDateFormat("h:mm:ss a").format(Date()))
            builder.appendln(getStackTrace(except))
            return builder
        }

        private fun getStringBuilder(buffer: Int = 100, initialText: String = ""): StringBuilder {
            val builder: StringBuilder = StringBuilder(buffer)
            if (initialText.isNotEmpty()) builder.append(initialText)
            return builder
        }

        private fun closeWindow() {
            frame.dispose()
            mvuiLogger = null
        }
    }

    init {
        Utils.setLookAndFeel()

        createGui(parent)

        frame.isVisible = true
    }

    private fun createGui(parent: JFrame?) {
        frame = JFrame("Exception Log")
        frame.iconImage = iconImage
        frame.setSize(430, 260)
        frame.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                closeWindow()
            }
        })

        frame.setLocationRelativeTo(parent)
        frame.addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ESCAPE)
                    closeWindow()

                if (e.isControlDown && e.keyCode == KeyEvent.VK_C)
                    textArea.text = ""
            }
        })

        val panel = JPanel()
        panel.layout = BorderLayout()
        frame.add(panel)

        textArea.isEditable = false
        textArea.lineWrap = true
        textArea.font = Font(Font.SERIF, Font.PLAIN, 18)
        textArea.background = Color.BLACK
        textArea.foreground = Color.WHITE

        val scrollPane = JScrollPane()
        scrollPane.background = Color.BLACK
        scrollPane.viewport.add(textArea)
        panel.add(scrollPane, BorderLayout.CENTER)
    }
}
