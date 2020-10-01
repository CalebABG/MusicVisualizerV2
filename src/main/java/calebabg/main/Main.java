package calebabg.main;

import processing.core.PApplet;

public class Main {
    static {
//        System.setProperty("sun.java2d.transaccel", "true");
//        System.setProperty("sun.java2d.ddforcevram", "true");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public static void main(String[] args){
        PApplet.main(Visualizer.class);
    }
}
