package calebabg.main.pojo

import calebabg.main.bassKick
import calebabg.main.ellipseR
import calebabg.main.starSpeed
import processing.core.PApplet

class Star {
    var x = 0f
    var y = 0f
    var z = 0f

    fun update(app: PApplet) {
        z -= starSpeed

        val width = app.width
        val height = app.height

        if (z <= 1) {
            z = app.random(width / 2f)
            x = app.random(-width / 2f, width / 2f)
            y = app.random(-height / 2f, height / 2f)
        }
    }

    fun render(app: PApplet) {
        val maxR = 6f
        val minR = 0.2f

        val width = app.width
        val height = app.height

        val sx: Float = PApplet.map(x / z, 0f, 1f, 0f, width / 2f)
        var sy: Float = PApplet.map(y / z, 0f, 1f, 0f, height / 2f)
        val r: Float = PApplet.map(z, 0f, width / 2f, maxR, minR)

        app.pushStyle()

        app.fill(PApplet.map(r, maxR, minR, 255f, 0f))
        app.noStroke()

        val added: Float = PApplet.map(ellipseR, 0f, r * 2 - 50, 0f, 2 * PApplet.PI)
        sy += 69 * PApplet.sin(.06f * added)

        app.ellipse(sx + width / 2, sy + height / 2, r, r)

        app.popStyle()
    }
}