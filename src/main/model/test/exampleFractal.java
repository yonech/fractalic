package main.model.test;

import main.model.fractal.*;

public class exampleFractal {

    public static Fragment KochSnowflakeFractal(double fromX, double fromY, double toX, double toY)
    {
        FragmentType t = new FragmentType();
        t.recurStep.clear();
        t.recurStep.add(new Fragment(0,         0,          0.333333,   0,          t) );
        t.recurStep.add(new Fragment(0.333333,  0,          0.5,        0.288675,   t) );
        t.recurStep.add(new Fragment(0.5,       0.288675,   0.666667,   0,          t) );
        t.recurStep.add(new Fragment(0.666667,  0,          1,          0,          t) );

        return new Fragment(fromX,fromY,toX,toY, t);
    };


}
