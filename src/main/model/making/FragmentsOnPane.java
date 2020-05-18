package main.model.making;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.model.fractal.Fragment;
import main.model.fractal.FragmentType;
import main.model.math.Complex;
import main.model.math.Geometric;
import main.model.shapes.Arrow;

import java.util.ArrayList;

public class FragmentsOnPane {


    final static double deleteRange = 10;


    public boolean drawLabels = false;
    public boolean drawGrid = false;



    Circle startOf = new Circle(5.0, Color.web("#94b0c2"));
    Circle endOf = new Circle(5.0, Color.web("#ef7d57"));

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
            Arrow drawn = new Arrow(s.spanFrom.real, s.spanFrom.imag, s.spanTo.real, s.spanTo.imag, 8);
            drawn.setFill(s.info.color);

            space.getChildren().add(drawn);
            if(drawLabels)
            {
                Text text = new Text(s.info.name);
                double rotate = Math.toDegrees(Math.atan2(s.spanTo.imag-s.spanFrom.imag, s.spanTo.real-s.spanFrom.real));
                if(rotate<-90 || (rotate>=90 && rotate<270)) rotate+=180;
                text.setRotate(rotate);
                text.setFont(Font.font(11));
                text.setStroke(s.info.color);
                text.setX((s.spanFrom.real-10));
                text.setY((s.spanFrom.imag));
                space.getChildren().add(text);
            }
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

    public void toggleLabels(boolean to)
    {
        drawLabels=to;
        refreshPane();
    };

}
