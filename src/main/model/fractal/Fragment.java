package main.model.fractal;


import main.model.math.Complex;

/**
 * Fragment, as defined in README.md
 * Describes the element that will be duplicated during recursion
 *
 * Thousands of these will be created when drawing a fractal
 *
 * There are two types of Fragments that serve different purpose, albeit aren't different in any way
 * - Fragments that are drawn somewhere on the canvas
 * - Fragments that define a recursive step for some fragment that goes from 0 to 1
 *      (usually small values near 0-1)
 */

public class Fragment {

    public Complex spanFrom, spanTo;
    public FragmentType info;


    public Fragment(double fr, double fi, double tr, double ti, FragmentType info)
    {
        spanFrom = new Complex(fr,fi);
        spanTo = new Complex(tr,ti);
        this.info=info;
    }
    public Fragment(Complex from, Complex to, FragmentType info)
    {
        spanFrom = from;
        spanTo = to;
        this.info=info;
    }
    public Fragment(FragmentType info){    this(0,0,1,0, info); }


    public Fragment newFragment(Fragment stepEl)
    {
        Complex span = Complex.minus(spanTo,spanFrom);
        Complex retFrom = Complex.plus(spanFrom,Complex.times(stepEl.spanFrom,span));
        Complex retTo = Complex.plus(spanFrom,Complex.times(stepEl.spanTo,span));
        return new Fragment(retFrom,retTo,stepEl.info);
    }
}
