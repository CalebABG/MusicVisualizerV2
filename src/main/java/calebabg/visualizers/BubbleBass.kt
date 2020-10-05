package calebabg.visualizers

import calebabg.abstractions.IVisualization
import calebabg.main.*
import calebabg.pojo.BassBubble
import processing.core.PApplet

class BubbleBass : IVisualization {
    var bassBubbles = Array(bassBubbleCount) { BassBubble() }

    override fun update(app: PApplet) {
        val fftCappedVal = fftSmooth.size.coerceAtMost(bassBubbles.size)
        for (i in 0 until fftCappedVal) bassBubbles[i].update(app)
    }

    override fun render(app: PApplet) {
        val fftCappedVal = fftSmooth.size.coerceAtMost(bassBubbles.size)
        for (i in 0 until fftCappedVal) bassBubbles[i].render(app)
    }
}