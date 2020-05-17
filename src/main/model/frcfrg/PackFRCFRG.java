package main.model.frcfrg;

import main.model.fractal.FragmentType;

import java.io.File;

public class PackFRCFRG {

    File on;
    PackFRCFRG(File o){ on=o; }

    public static enum Trait
    {PATTERN, COLOR, SHAPE, CONDITION};

    public void removeTrait(Trait a)// I.e. PATTERN, COLOR, etc.
    {
        //TODO

    };

    public void addTraitsFrom(FragmentType ft)
    {

        //TODO
    };


}
