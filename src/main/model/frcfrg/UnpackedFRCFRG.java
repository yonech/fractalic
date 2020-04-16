package main.model.frcfrg;

import javafx.scene.paint.Color;
import main.model.fractal.Fragment;
import main.model.fractal.FragmentType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UnpackedFRCFRG {

    HashMap<String, FragmentType> unpacked = new HashMap<>();

    public FragmentType getByName(String id) throws FileNotFoundException
    {
        FragmentType ft = unpacked.get(id);
        if(ft!=null)  return ft;

        File sourceFile = new File("src/resources/fragments/" + id + ".frcfrg");
       //System.out.println(sourceFile.exists());

        ft = new FragmentType();
        unpacked.put(id,ft);

        createFragmentType(sourceFile,id,ft);
        return ft;
    };

    void createFragmentType(File sourceFile, String id, FragmentType ft) throws FileNotFoundException {
        //System.out.println(sourceFile);


        Scanner scanner = new Scanner(sourceFile);


        Vector<String> trait = new Vector<>();
        while (scanner.hasNext())
        {
            String[] words = scanner.nextLine().split(" ");
           // System.out.println(Arrays.toString(words));
            boolean exit=false;
            for(String o : words)
            {
                switch(o)
                {
                    case "":                                            break;
                    case "//":  exit=true;                              break;
                    case "[":   trait.clear();                          break;
                    case "]":   encode(ft,trait,id);  trait.clear();    break;
                    default:    trait.add(o);                           break;
                };
                if(exit) break;
            }
        };
        trait.clear();
    };


    void encode(FragmentType ret, Vector<String> trait, String id) {

        if(trait.isEmpty()) return;

        switch(trait.get(0))
        {
            case "PATTERN":
                ret.recurStep.clear();
                for(int i=1; i+4<trait.size(); i+=5)
                {
                    String pieceId = trait.get(i);
                    FragmentType fragmentType;

                    if(pieceId.equals(id) || pieceId.equals("@")) fragmentType=ret;
                    else try {
                        fragmentType = getByName(pieceId);
                    } catch (FileNotFoundException e) {
                        fragmentType = FragmentType.defaultFragmentType;
                    }

                    ret.recurStep.add(
                            new Fragment(   Double.parseDouble(trait.get(i+1)),
                                            Double.parseDouble(trait.get(i+2)),
                                            Double.parseDouble(trait.get(i+3)),
                                            Double.parseDouble(trait.get(i+4)),
                                            fragmentType));
                };

                break;
            case "COLOR":
                ret.color = Color.web(trait.get(1));
                break;
            default:    return;
        };

    };


}

