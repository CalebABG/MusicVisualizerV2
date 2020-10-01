package calebabg.visualizers

import calebabg.abstractions.IVisualization
import calebabg.main.pojo.Star
import calebabg.main.starCount
import processing.core.PApplet

class StarField : IVisualization {
    var stars = Array(starCount) { Star() }

    override fun update(app: PApplet) {
        for (s in stars) s.update(app)
    }

    override fun render(app: PApplet) {
        for (s in stars) s.render(app)
    }
}