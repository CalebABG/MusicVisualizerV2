package calebabg.visualizers

import calebabg.abstractions.IVisualization
import calebabg.main.*
import processing.core.PApplet
import kotlin.math.sign

class MixedWaveSignal : IVisualization {
    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        var i = 0

        while (i < avgSize - 1) {
            val width = app.width
            val height = app.height

            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)

            val x1 = PApplet.map(i.toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val x2 = PApplet.map((i + 1).toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val y1 = -mixedSignal(x1, height) + height / 2
            val y2 = -mixedSignal(x2, height) + height / 2

            val c1 = PApplet.map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 275f)
            app.stroke(c1, 255f, 255f)

            app.strokeWeight(strokeWeight)
            app.line(1.7f * x1, y1 + amp, 1.7f * x2, y2 - amp)

            i += barStep
        }

        angle += 0.015f
    }

    private fun mixedSignal(x: Float, height: Int): Float {
        fun defaultSignal(): Float = 75f * PApplet.sin(.015f * x + angle)

        return when (transformMode) {
            0 -> defaultSignal()
            1 -> {
                val m = 2f
                val a = 325f
                m * (x - a) * sign(a - x) + m * a - height / 2 + 60 * PApplet.sin(.08f * x + angle)
            }
            2 -> 60f * PApplet.sin(2 * PApplet.sin(2 * PApplet.sin(2 * PApplet.sin(2 *
                    PApplet.sin(2 * PApplet.sin(2 * PApplet.sin(2 * PApplet.sin(.032f * x))))))))
            3 -> PApplet.pow(.02f * x, 2f)
            else -> defaultSignal()
        }
    }
}