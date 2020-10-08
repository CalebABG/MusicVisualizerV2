package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.*
import processing.core.PApplet

class ColorSignal : Visualization {
    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        var i = 0

        while (i < avgSize - 1) {
            val width = app.width
            val height = app.height

            val x1 = PApplet.map(i.toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val x2 = PApplet.map((i + 1).toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)

            val y = height / 2f - 20

            val c = PApplet.map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 285f)
            app.stroke(c, 255f, 255f)

            app.strokeWeight(strokeWeight)
            app.line(x1, y + amp, x2, y - amp)

            i += barStep
        }
    }
}