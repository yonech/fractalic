package main.model.frcfrg;

import main.model.fractal.Fragment;
import main.model.fractal.FragmentType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PackFRCFRG {

    File on;
    public PackFRCFRG(File o){ on=o; }

    public static enum Trait
    {PATTERN, COLOR, SHAPE, CONDITION};

    public void removeTrait(Trait a)// I.e. PATTERN, COLOR, etc.
    {
        //TODO

    };

    public void addTraitsFrom(Fragment ft) throws IOException {

        removeTrait(Trait.PATTERN);
        removeTrait(Trait.COLOR);

        BufferedWriter writer = new BufferedWriter(new FileWriter(on, true));

        writer.newLine();
        //Pattern
        writer.write(" [ PATTERN ");
        writer.newLine();
        for (Fragment x: ft.info.recurStep) {
            //NOT INFO NAME, GET THE NAME SOMEHOW AAAAA
            writer.write(" " + (x.info.name.equals(ft.info.name)?"@":x.info.name) + " " + x.spanFrom.real + " " + x.spanFrom.imag + " " + x.spanTo.real + " " + x.spanTo.imag);
            writer.newLine();
        }
        writer.write(" ] ");
        writer.newLine();

        //Color
        writer.write(" [ COLOR " + ft.info.color.toString() + " ] ");
        writer.newLine();

        writer.close();
    };


}
