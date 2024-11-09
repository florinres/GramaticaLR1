import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TA {
    private String[]symbols = new String[6];
    private String[][] symbolsDR;
    int symbolCounter = 0;

    TA(){
        System.out.println("Reading total symbols");
        try {
            File file = new File("D:\\repo\\GramaticaLR1\\src\\TA.txt");
            Scanner sc = new Scanner(file);
            System.out.println("Reading symbols");
            while(sc.hasNext()){
                String line = sc.nextLine();
                for (String a : line.split(" ")) {
                    symbols[symbolCounter++] = a;
                }
            }

            System.out.println("Total symbols read: " + symbolCounter +
                    "\nBegin phase two");
            symbolsDR = new String[12][6];
            try{
                File file1 = new File("D:\\repo\\GramaticaLR1\\src\\TA_states.txt");
                Scanner sc1 = new Scanner(file1);
                int lineCounter = 0;
                while(sc1.hasNext()){
                    String line = sc1.nextLine();
                    for (String a : line.split(" ")) {
                        if(a.equals("acc")){
                            break;
                        }
                        String term = getTerm(a);
                        int indexColon = getSymbolIndex(term);
                        symbolsDR[lineCounter][indexColon] = getDR(a);
                    }
                    lineCounter++;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    private String getDR(String a) {
        return a.substring(a.indexOf(":")+1);
    }

    private String getTerm(String a) {
        return a.substring(0, a.lastIndexOf(":"));
    }

    protected String[][] getActionTable(){
        return symbolsDR;
    }

    private int getSymbolIndex(String symbol) {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].equals(symbol)) {
                return i;
            }
        }
        return -1;
    }

    public String[] getTerms() {
        return symbols;
    }
}
