package com.gmail.zendarva.aie.gui;

import java.awt.*;

/**
 * Created by James on 7/28/2018.
 */
public class RenderHelper {
    static Point mouseLoc;

    public static void setMouseLoc(int x, int y){
        mouseLoc = new Point(x, y);
    }

    public static Point getMouseLoc(){
        return mouseLoc;
    }
}
