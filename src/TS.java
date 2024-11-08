import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TS {
    String[][] jumpTable = new String[12][3];
    TS(){
        System.out.println("Reading the jump table");
        try {
            File file = new File("C:\\Users\\nxg06737\\OneDrive - NXP\\Desktop\\student_stuff\\Gramatica-LR1\\src\\TS.txt");
            Scanner sc = new Scanner(file);
            int indexRow = 0;
            while (sc.hasNextLine()) {
                int indexColon = 0;
                for (String i : sc.nextLine().split(" ")) {
                    jumpTable[indexRow][indexColon++] = i;
                }
                indexRow++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getActionTable() {
        return jumpTable;
    }
}
