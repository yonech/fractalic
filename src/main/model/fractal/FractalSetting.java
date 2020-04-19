package main.model.fractal;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

/**
 * General information about the drawn fractal
 * - Depth of recursion
 * - Color change
 * - etc.
 *
 * */

public class FractalSetting {

    public int depth=4;

    GraphicsContext space;

    public FractalSetting(GraphicsContext space)
    {
        this.space = space;
    };
    public void setDepth(int x){ depth = x; }

    public void drawFractal(Fragment start)
    {
        drawingFractal(0,start);
    };


    private void drawingFractal(int depth, Fragment frag)
    {

        if(depth>=this.depth)
        {
            //TODO StrokeLine needs to be replaced with the option to place an image or any shape
            space.setStroke(frag.info.color==null?Color.BLUE:frag.info.color);
            space.strokeLine(frag.spanFrom.real, frag.spanFrom.imag, frag.spanTo.real, frag.spanTo.imag);
            return;
        };

        for(Fragment i : frag.info.recurStep)
            drawingFractal(depth+1, frag.newFragment(i));

    };

}
