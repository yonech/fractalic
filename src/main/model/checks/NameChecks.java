package main.model.checks;

import javafx.scene.control.RadioButton;
import main.control.making.MakingFrgController;
import main.control.menu.examples.ExamplesController;

import java.util.ArrayList;

public class NameChecks {

    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

    public static boolean canBeAFile(String x)
    {
        for(char c : ILLEGAL_CHARACTERS) if(x.contains(String.valueOf(c)))  return false;
        return true;
    };

    public static boolean properName(String name, ArrayList<RadioButton> buttonList)
    {
        boolean isGoodName = canBeAFile(name);
        if(name.equals("")) isGoodName=false;
        for(RadioButton r : buttonList) if(r.getText().equals(name))  isGoodName=false;

        return  isGoodName;
    };

}
