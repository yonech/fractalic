package main.model.fractal;


import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.Vector;

/**
 * Information about the fractal fragment i.e. recursive step, image/shape used for the fragment
 *
 * Only a couple of these types will be created
 */

public class FragmentType {


    // ? TYPE for draw pattern

    public Vector<Fragment> recurStep;
    public String name="???";
    public Color color=Color.BLACK;

    public static final FragmentType defaultFragmentType = new FragmentType();

    // TODO
        // shape
        // start
        // end

    // Default Constructor, doesn't change on recursion
    public FragmentType()
    {
        recurStep = new Vector<>();
        recurStep.add(new Fragment(this));
    };



    /*
    public FragmentType (double... points) throws BadPolyLineException
    {
        if (points.length%2==1 || points.length==0) throw new BadPolyLineException();
    };

    static class BadPolyLineException extends Throwable {}*/
}

