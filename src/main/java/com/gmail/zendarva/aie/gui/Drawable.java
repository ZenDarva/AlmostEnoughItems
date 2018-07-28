package com.gmail.zendarva.aie.gui;

import java.awt.*;

/**
 * Created by James on 7/28/2018.
 */
public abstract class Drawable {
    protected Rectangle rect;
    public Drawable(int x, int y, int width, int height) {
        rect = new Rectangle(x,y,width,height);
    }
    public Drawable(Rectangle rect){
        this.rect = rect;
    }

    public abstract void draw();

    public boolean isHighlighted(){
        Point mousePoint = AEIRenderHelper.getMouseLoc();
        return rect.contains(mousePoint.x,mousePoint.y);
    }
}
