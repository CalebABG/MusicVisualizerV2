package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.*
import processing.core.PApplet

class BassNation : Visualization {
    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        if (fftSmooth.isEmpty()) return

        app.pushMatrix()
        app.translate(app.width / 2f, app.height / 2f)

        val bass = bassKick * PApplet.floor(fftSmooth[1])
        val radius = 0.75f * -(circleRadius * 2 + bass * 0.25f)
        val scale = 0.75f * amplitude

        for (i in 0 until avgSize) {
            val amp = fftSmooth[i]
            //val a = -(angOffset / 100f)
            val a = -(60f / 100f)
            val b = PApplet.PI / 180f
            val angle = a * b

            app.rotate(angle)
            app.colorMode(PApplet.HSB)

            val c = PApplet.map(i.toFloat(), 0f, avgSize.toFloat(), 0f, 275f)
            app.fill(c, 255f, 255f)
            app.rect(0f, radius, 2f, -amp * scale)
        }

        for (i in 0 until avgSize) {
            val amp = fftSmooth[i]
            //val a = angOffset / 100f
            val a = 60f / 100f
            val b = PApplet.PI / 180f

            app.rotate(a * b)
            app.colorMode(PApplet.HSB)

            val c = PApplet.map(i.toFloat(), avgSize.toFloat(), 0f, 275f, 0f)
            app.fill(c, 255f, 255f)
            app.rect(0f, radius, 2f, -amp * scale)
        }

        app.popMatrix()
    }
}