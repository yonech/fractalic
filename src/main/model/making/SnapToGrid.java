package main.model.making;

import javafx.util.Pair;

public class SnapToGrid {

    final static double snapRange = 4;


    public static Pair<Double, Double> snapToNearest(double x, double y)
    {
        double sx = Math.round((x - FragmentsOnPane.horOffset)/FragmentsOnPane.gridInterval);
        double sy = Math.round((y - FragmentsOnPane.verOffset)/FragmentsOnPane.gridInterval);

        double rx = x - FragmentsOnPane.horOffset - sx*FragmentsOnPane.gridInterval;
        double ry = y - FragmentsOnPane.verOffset - sy*FragmentsOnPane.gridInterval;

        if(Math.abs(rx)<=snapRange && Math.abs(ry)<=snapRange)  return new Pair<>(x - rx, y - ry);

        return new Pair<>(x, y);
    };

}
