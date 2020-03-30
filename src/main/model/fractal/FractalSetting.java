package main.model.fractal;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * General information about the drawn fractal
 * - Depth of recursion
 * - Color change
 * - etc.
 *
 * */

public class FractalSetting {

    public int depth=5;

    GraphicsContext space;

    public FractalSetting(GraphicsContext space)
    {
        this.space = space;
    };

    public void drawFractal(Fragment start)
    {
        drawingFractal(0,start);
    };


    private void drawingFractal(int depth, Fragment frag)
    {

        if(depth==this.depth)
        {
            /** StrokeLine needs to be replaced with the option to place an image or any shape   */

            space.strokeLine(frag.spanFrom.real, frag.spanFrom.imag, frag.spanTo.real, frag.spanTo.imag);
            return;
        };

        for(Fragment i : frag.info.recurStep)
            drawingFractal(depth+1, frag.newFragment(i));

    };

}
