import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Productions {
    String[][]productions = new String[10][2];

    Productions(){
        System.out.println("Finding productions");
        int index = 0;
        try {
            File file = new File("C:\\Users\\nxg06737\\OneDrive - NXP\\Desktop\\student_stuff\\GramaticaLR1\\src\\productions.txt");
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
}
