package com.calebabg.main

import processing.core.PApplet

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(Visualizer::class.java)
    }

    init {
        //System.setProperty("sun.java2d.transaccel", "true")
        //System.setProperty("sun.java2d.ddforcevram", "true")
        System.setProperty("apple.laf.useScreenMenuBar", "true")
    }
}