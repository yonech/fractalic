package main.model.making;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import main.model.fractal.Fragment;
import main.model.fractal.FragmentType;
import main.model.math.Complex;
import main.model.math.Geometric;

import java.util.ArrayList;

public class FragmentsOnPane {


    final static double deleteRange = 10;

    Circle startOf = new Circle(5.0, Color.web("#b13e53"));
    Circle endOf = new Circle(5.0, Color.web("#3b5dc9"));

    Pane space;

    // space description
    Fragment base = new Fragment(150,220,350,220, null);
    ArrayList<Fragment> pieces = new ArrayList<>();

    public FragmentsOnPane(Pane on){ space = on; }

    public void addStartAndEndCircles()
    {
        startOf.setCenterX(base.spanFrom.real);  startOf.setCenterY(base.spanFrom.imag);
        endOf.setCenterX(base.spanTo.real);      endOf.setCenterY(base.spanTo.imag);
        space.getChildren().add(startOf);
        space.getChildren().add(endOf);
    };

    public void layFragments(FragmentType source)
    {
        for (Fragment a: source.recurStep) {
            Fragment displayed = base.newFragment(a);
            pieces.add(displayed);
        }
        refreshPane();
    };

    public void refreshPane()
    {
        space.getChildren().clear();
        addStartAndEndCircles();

        for (Fragment s: pieces) {
            Line drawn = new Line(s.spanFrom.real, s.spanFrom.imag, s.spanTo.real, s.spanTo.imag);
            drawn.setStroke(s.info.color);
            space.getChildren().add(drawn);
        };
    };


    public Fragment toFragment()
    {
        base.info = new FragmentType();
        base.info.recurStep.clear();
        for (Fragment x: pieces) {
            base.info.recurStep.add(base.newReverseFragment(x));
        }

        return base;
    };

    public void deleteNearest(double px, double py)
    {
        Fragment nearest = null;
        double bestd=deleteRange;
        for(Fragment s : pieces) {
            double d = Geometric.dist(s,new Complex(px,py));
            if(d < bestd) {
                nearest=s;
                bestd=d;
            }
        }
        if(nearest!=null) pieces.remove(nearest);
        refreshPane();
    };

    public void add(Fragment x)
    {
        pieces.add(x);
        refreshPane();
    };

}
