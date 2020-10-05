package calebabg.pojo

import calebabg.main.bassKick
import calebabg.main.circleRadius
import calebabg.main.fftSmooth
import processing.core.PApplet
import java.util.*

class BassBubble {
    companion object {
        var rand: Random = Random()
        var InstancesCount = 0
    }

    var index: Int

    var x: Float = 0f
    var y: Float = 0f

    var xNoise: Float
    var yNoise: Float

    var xSpeed: Float
    var ySpeed: Float

    init {
        xNoise = rand.nextFloat() * 10000f
        yNoise = rand.nextFloat() * 10000f

        xSpeed = (rand.nextFloat() * .005f) + .002f
        ySpeed = (rand.nextFloat() * .009f) + .001f

        index = InstancesCount

        InstancesCount++
    }

    fun update(app: PApplet) {
        xSpeed = app.random(.002f, .02f)
        ySpeed = app.random(.001f, .03f)

        x += (app.noise(xNoise) * 3 - 1) + xSpeed
        y += (app.noise(yNoise) * 3 - 1) + ySpeed

        if (x < 0) x = app.width.toFloat()
        if (x > app.width) x = 0f

        if (y < 0) y = app.height.toFloat()
        if (y > app.height) y = 0f
    }

    fun render(app: PApplet) {
        if (index > fftSmooth.size) return

        app.pushStyle()
        app.colorMode(PApplet.HSB)

        val fftVal = fftSmooth[index]
        val maxR = PApplet.PI * circleRadius * 4

        val r = PApplet.constrain(fftVal * bassKick, 0f, maxR)

        val bassRadius = r * 0.1f
        val bassColor = PApplet.map(r, 0f, maxR, 0f, 360f)

        app.noStroke()
        app.fill(bassColor, 255f, 255f, 150f)
        app.ellipse(x, y, bassRadius, bassRadius)

        app.popStyle()

        /*var r = (ellipseR * 0.95f)
        r = lerp(0f, r, .33f)

        var col = map((starSpeed * .9f) + (xspeed * xspeed) + (yspeed * yspeed), .1f, 6.2f, 0f, 360f)
        var c = color(col, 255f, 255f, 150f)

        noStroke()

        fill(c)
        ellipse(x, y, r, r)*/
    }
}