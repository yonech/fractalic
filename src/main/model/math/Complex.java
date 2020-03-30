package main.model.math;

/**
 * Simple complex number class to help simplify fractal-mapping calculations
 */

public class Complex {

    public double real, imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    };
    public Complex(double real){   this(real,0);   };
    public Complex(){              this(0);         };

    public static Complex plus(Complex A, Complex B)    { return new Complex(A.real+B.real,                 A.imag+B.imag               ); }
    public static Complex minus(Complex A, Complex B)   { return new Complex(A.real-B.real,                 A.imag-B.imag               ); }
    public static Complex times(Complex A, Complex B)   { return new Complex(A.real*B.real-A.imag*B.imag,   A.imag*B.real+A.real*B.imag ); }

    @Override
    public String toString() { return real + "+" + imag + "i"; };



}
