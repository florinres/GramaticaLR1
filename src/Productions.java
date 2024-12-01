import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Productions {
    String[][]productions = new String[7][2];

    Productions(){
        System.out.println("Finding ProdToGenerate.txt");
        int index = 0;
        try {
            File file = new File("D:\\repo\\GramaticaLR1\\src\\ProdToGenerate.txt.txt");
            Scanner sc = new Scanner(file);
            int indexLine=0;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String term = line.split("-")[0];
                String production = line.substring(line.indexOf("-")+1);
                productions[indexLine][0] = term;
                productions[indexLine++][1] = production;
                System.out.println(term + " " + production);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getProductions(){
        return productions;
    }
}
