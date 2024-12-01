import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PUGenerator {
    private List<String> nonTerminals = new ArrayList<>();
    private LinkedHashMap<String, ArrayList<String>> productions = new LinkedHashMap<>();
    private List<String> someSets = new ArrayList<>();
    private String startSymbol;

    public PUGenerator() {
        try {
            File file = new File("D:\\repo\\GramaticaLR1\\src\\ProdToGenerate.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] lineArray = line.split(" ");
                String term = lineArray[0];
                nonTerminals.add(term);
                String firstProduction = lineArray[1];
                String secondProduction = lineArray[2];
                ArrayList<String> production = new ArrayList<>();
                production.add(firstProduction);
                production.add(secondProduction);
                productions.put(term, production);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate() {
        String startSymbol = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter start symbol");
        startSymbol = sc.nextLine();
        System.out.println("Looping thorugh productions");
        this.startSymbol = startSymbol;

        for (String term : nonTerminals) {
            someSets.add(PRIM(term, startSymbol));
        }
    }

    private String PRIM(String term, String startSymbol) {
        ArrayList<String> temp =  productions.get(term);
        String firstNonTerminal = "";
        while(productionsContainsNTerminals(temp, startSymbol)){
            System.out.println("Running through productions " + temp);
            firstNonTerminal = getFirstNonterminal(temp.toString(), startSymbol);
            if (firstNonTerminal.length() > 1) {break;}
            startSymbol = firstNonTerminal;
            temp = productions.get(firstNonTerminal);
        }
        System.out.println("Stopped at: " + startSymbol + " with productions " + firstNonTerminal);

        return term;
    }

    private boolean productionsContainsNTerminals(ArrayList<String> temp, String startSymbol) {
        for (String production : temp) {
            for(int i = 0; i < production.length(); i++){
                String currentChar = production.charAt(i) + "";
                if (currentChar.equals(startSymbol)) {
                    continue;
                }
                if(nonTerminals.contains(currentChar)){
                    return true;
                }
            }
        }
        return false;
    }

    private String getFirstNonterminal(String production, String startSymbol) {
        for (int i = 0; i < production.length(); i++) {
            String currentChar = production.charAt(i) + "";
            if(currentChar.equals(startSymbol) || currentChar.equals(this.startSymbol)) {
                continue;
            }
            if (nonTerminals.contains(currentChar)) {
                return currentChar;
            }
        }

        System.out.println("No more productions available " + production);
        production = production.replaceAll("[\\[\\]|]", "");

        return production;
    }
}
