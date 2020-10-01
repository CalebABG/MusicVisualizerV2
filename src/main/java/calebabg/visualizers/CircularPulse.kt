package calebabg.visualizers

import calebabg.abstractions.IVisualization
import calebabg.main.*
import processing.core.PApplet

class CircularPulse : IVisualization {
    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        var i = 0

        while (i < avgSize) {
            val angle = i.toFloat() * angOffset * 2f * PApplet.PI / avgSize
            val fftSmoothDisplay = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)

            val rad = circleRadius * 2
            val x = rad * PApplet.cos(angle)
            val y = rad * PApplet.sin(angle)
            val x2 = (rad + fftSmoothDisplay) * PApplet.cos(angle) + .85f
            val y2 = (rad + fftSmoothDisplay) * PApplet.sin(angle) + .85f

            //int c = (int) map(i, 0, fft.specSize(), 0, 470);
            val c = PApplet.map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 275f)
            app.colorMode(PApplet.HSB)
            app.stroke(c, 255f, 255f)

            val width = app.width
            val height = app.height

            app.strokeWeight(strokeWeight)
            app.line(x + width / 2, y + height / 2, x2 + width / 2, y2 + height / 2)

            i += barStep
        }

        app.noStroke()
        app.fill(PApplet.lerp(0f, PApplet.map(ellipseR, 0f, circleRadius * 3 - 50, 0f, 255f), .23f), 255f, 255f)
        app.ellipse(app.width / 2f, app.height / 2f, ellipseR, ellipseR)
    }
}