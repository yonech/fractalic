package main.model.fractal;


import java.util.Vector;

/**
 * Information about the fractal fragment i.e. recursive step, image/shape used for the fragment
 *
 * Only a couple of these types will be created
 */

public class FragmentType {

    // ? TYPE for draw pattern

    public Vector<Fragment> recurStep;

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

