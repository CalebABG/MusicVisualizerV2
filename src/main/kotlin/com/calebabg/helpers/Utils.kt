package com.calebabg.helpers

import com.calebabg.abstractions.AudioResource
import com.calebabg.abstractions.LocalAudioSource
import com.calebabg.main.audioFormats
import com.calebabg.main.maxVal
import com.calebabg.main.minVal
import ddf.minim.analysis.FFT
import org.apache.commons.io.FilenameUtils
import java.awt.FileDialog
import java.awt.Font
import java.awt.Frame
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.UIManager

open class Utils {
    companion object {
        private var OS = System.getProperty("os.name").toLowerCase()

        fun isWindows(): Boolean = isOS("win")
        fun isMac(): Boolean = isOS("mac")
        fun isUnix(): Boolean = isOS("nix") || isOS("nux") || isOS("aix")
        fun isSolaris(): Boolean = isOS("sunos")

        private fun isOS(name: String) = OS.indexOf(name) > -1

        fun minValueGuard(min: Float, max: Float, default_val: Float, promptText: String, parent: JFrame?): Float {
            setLookAndFeel()

            val label = JLabel(promptText)
            label.font = Font(Font.SERIF, Font.PLAIN, 18)

            val stringAmount: String? = JOptionPane.showInputDialog(parent, label, null, JOptionPane.PLAIN_MESSAGE)
            val floatAmount = stringAmount?.toFloatOrNull()

            return if (floatAmount == null || floatAmount !in min..max) default_val
            else floatAmount
        }

        fun applySmoothingToFFT(array: FloatArray, fft: FFT, smoothing: Float) {
            for (i in 0 until fft.avgSize()) {
                array[i] = smoothing * array[i] + (1 - smoothing) * fft.getAvg(i)

                // Find max and min values ever displayed across whole spectrum
                if (array[i] > maxVal) maxVal = array[i]
                if (array[i] < minVal) minVal = array[i]
            }
        }

        fun setLookAndFeel() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (ex: Exception) {
                println(ex.message)
            }
        }

        private fun acceptedAudioFormats(): String {
            val builder = StringBuilder()
            for (format in audioFormats) builder.append("*.$format; ")
            return builder.toString()
        }

        fun getHtml(file: String, buffer: Int = 1024): String {
            var htmlString: String = ""

            try {
                BufferedReader(InputStreamReader(Utils::class.java.getResourceAsStream(file))).use { br ->
                    val lines = StringBuilder(buffer)
                    var line: String?
                    while (br.readLine().also { line = it } != null) lines.append(line)
                    htmlString = lines.toString()
                }
            } catch (e: java.lang.Exception) {
                println(e.message)
            }

            return htmlString
        }

        fun getSecret(secretName: String): String {
            var secret = System.getenv(secretName)
            if (secret == null) secret = ""
            return secret
        }

        fun selectMusicFromGui(title: String = "Select Music..."): ArrayList<AudioResource>? {
            setLookAndFeel()

            val dialog = FileDialog(null as Frame?, title, FileDialog.LOAD)
            dialog.isMultipleMode = true
            dialog.file = acceptedAudioFormats()
            dialog.isVisible = true

            val fileList: Array<File> = dialog.files
            var audioList: ArrayList<AudioResource>? = null

            if (fileList.isNotEmpty()) {
                audioList = ArrayList()
                fileList.forEach {
                    val mediaTitle = FilenameUtils.getBaseName(it.name)
                    val mediaPath = it.absolutePath

                    val localAudioResource = LocalAudioSource(mediaTitle, mediaPath)
                    audioList.add(localAudioResource)
                }
            }

            return audioList
        }
    }
}