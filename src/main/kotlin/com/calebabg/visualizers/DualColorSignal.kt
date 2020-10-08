package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.*
import processing.core.PApplet

class DualColorSignal : Visualization {
    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        var i = 0

        val size = avgSize - 1

        while (i < size) {
            val width = app.width
            val height = app.height

            val x1 = PApplet.map(i.toFloat(), 0f, size.toFloat(), 0f, width.toFloat())
            val x2 = PApplet.map((i + 1).toFloat(), 0f, size.toFloat(), 0f, width.toFloat())

            val x3 = PApplet.map(i.toFloat(), 0f, size.toFloat(), width.toFloat(), 0f)
            val x4 = PApplet.map((i + 1).toFloat(), 0f, size.toFloat(), width.toFloat(), 0f)

            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)
            val y = height / 2f - 20

            val c = PApplet.map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 275f)
            app.stroke(c, 255f, 255f)

            app.strokeWeight(strokeWeight)
            app.line(x1, y, x2, y + amp)
            app.line(x3, y, x4, y - amp)

            i += barStep
        }
    }
}