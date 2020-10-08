package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.backgroundStep
import com.calebabg.main.circleRadius
import com.calebabg.main.ellipseR
import com.calebabg.main.offset
import processing.core.PApplet

class ShapeWave : Visualization {
    override fun update(app: PApplet) {}

    override fun render(app: PApplet) {
        val space = backgroundStep
        var i = -2 * space

        var rx: Float
        var ry: Float

        val rt = 400f
        val size = 1f

        val width = app.width
        val height = app.height

        while (i < width + space) {
            var j = -2 * space
            val cx = i.toFloat()

            while (j < height + space) {

                val cy = j.toFloat()

                var t = app.millis() / rt

                t += PApplet.cos(PApplet.abs(1 - cx / (width / 2))) * PApplet.TWO_PI
                t += PApplet.cos(PApplet.abs(1 - cy / (height / 2))) * PApplet.TWO_PI
                t += offset

                rx = PApplet.sin(t) * (size + PApplet.map(ellipseR, 0f, circleRadius * 1.85f - 30, 0f, 25f))
                //rx = (cos(t) + sin(4*t)) * (size + map(ellipseR, 0f, circleRadius * 1.85f - 30, 0f, 25f))
                ry = rx


                if (rx > 0 && ry > 0) {
                    app.noStroke()
                    app.fill(PApplet.lerp(0f, PApplet.dist(cx, cy, width / 2f, height / 2f), .25f), 255f, 255f)

                    app.pushStyle()

                    app.rectMode(PApplet.CENTER)
                    app.rect(cx, cy, rx, ry)

                    app.popStyle()
                    //ellipse(cx, cy, rx, ry)
                }

                j += space
            }

            i += space
        }
    }
}