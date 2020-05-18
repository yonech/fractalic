package main.model.math;

import javafx.scene.shape.Line;
import main.model.fractal.Fragment;

public class Geometric {

    private static class Pair
    {
        double F, S;
        public Pair(double F, double S)
        {
            this.F = F;
            this.S = S;
        }
        public Pair() {
        }
    }

    public static double dist(Fragment s, Complex loc)
    {
        return minDistance(s.spanFrom.real, s.spanFrom.imag, s.spanTo.real, s.spanTo.imag, loc.real, loc.imag);
    };

    static double minDistance(double ax, double ay, double bx, double by, double x, double y)
    {
        Pair AB = new Pair(bx-ax, by-ay);
        Pair BE = new Pair(x-bx, y-by);
        Pair AE = new Pair(x-ax, y-ay);

        double AB_BE, AB_AE;
        AB_BE = (AB.F * BE.F + AB.S * BE.S);
        AB_AE = (AB.F * AE.F + AB.S * AE.S);

        double reqAns = 0;
        if (AB_BE > 0)
        {
            double yy = y - by;
            double xx = x - bx;
            return Math.sqrt(xx * xx + yy * yy);
        }
        else if (AB_AE < 0)
        {
            double yy = y - ay;
            double xx = x - ax;
            return Math.sqrt(xx * xx + yy * yy);
        }
        else
        {
            double x1 = AB.F;
            double y1 = AB.S;
            double x2 = AE.F;
            double y2 = AE.S;
            double mod = Math.sqrt(x1 * x1 + y1 * y1);
            reqAns = Math.abs(x1 * y2 - y1 * x2) / mod;
        }
        return reqAns;
    }

}
