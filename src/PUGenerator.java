import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PUGenerator {

    private List<String> productions = new ArrayList<>();
    private String nonTerminals = "";
    private List<String> terminals = new ArrayList<>();
    private List<String> orTerm = new ArrayList<>();
    private String termOfStart = "E";
    public PUGenerator() {
            try {
                File file = new File("D:\\repo\\GramaticaLR1\\src\\ProdToGenerate.txt");
                Scanner sc = new Scanner(file);
                while(sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.contains("(1)")) {
                        line = sc.nextLine();
                        nonTerminals = line;
                    }
                    if (line.contains("(2)")) {
                        line = sc.nextLine();
                        String[] lineArray = line.split(" ");
                        terminals = Arrays.asList(lineArray);
                    }
                    if (line.contains("(3)")) {
                        line = sc.nextLine();
                        while(sc.hasNextLine()) {
                            String[] lineArray = line.split(" ");
                            for(String anotherLine : lineArray ){
                                if(anotherLine.contains("|")) {
                                    orTerm.add(anotherLine);
                                }
                                else {
                                    productions.add(anotherLine);
                                }
                            }
                            line = sc.nextLine();
                        }
                    }
                }
            }
            catch (FileNotFoundException e) {

            }
    }
    private void prime(){

    }

    private void last(){

    }

    public void generate() {
        String termOfStart = this.termOfStart;
        while(true){
            String production = getProduction(termOfStart);
            System.out.println(production);
            if(containsNonTerminals(production)){
                termOfStart = getProduction(production);
            }
            else{

            }
        }
    }

    private boolean containsNonTerminals(String production) {
        for(int a = 0; a < production.length();a++){
            if(productions.contains(production.charAt(a) + "")){
                return true;
            }
        }

        return false;
    }

    private String getProduction(String termOfStart) {
        return productions.get(productions.indexOf(termOfStart)+1);
    }

    private String getFirstNeterminal(String production) {
        for (int i = 0; i < production.length(); i++) {
            String character = production.charAt(i) + "";

        }

        return String.valueOf(production.indexOf(0));
    }
}
