package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.backgroundStep
import com.calebabg.main.circleRadius
import com.calebabg.main.ellipseR
import processing.core.PApplet

class GridBackground : Visualization {

    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        var x = backgroundStep

        while (x < app.width) {
            var y = backgroundStep

            while (y < app.height) {
                val n = app.noise(x * 0.005f, y * 0.007f, app.frameCount * 0.02f)

                app.pushMatrix()
                app.translate(x.toFloat(), y.toFloat())

                app.pushStyle()

                //rotate(TWO_PI * n)
                app.scale(PApplet.map(ellipseR, 0f, circleRadius * 1.85f - 30, 0f, 15f) * n)
                app.strokeWeight(.2f)

                val x1 = 0f
                val y1 = 0f
                val w = .8f
                val h = .8f

                app.fill(255)
                app.triangle(x1, y1, x1 + w / 2, y1 - h, x1 + w, y1)
                //triangle(0f,.1f, .1f,)
                //rect(0f, 0f, .8f, .8f)

                app.popStyle()
                app.popMatrix()

                y += backgroundStep
            }

            x += backgroundStep
        }
    }
}