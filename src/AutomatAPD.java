import java.util.Stack;
import java.util.Scanner;

public class AutomatAPD {
    TA ta;
    TS ts;
    Productions productions;
    Stack<String> stackSequence = new Stack<>();
    String startSequence = new String();

    AutomatAPD() {
        ta = new TA();
        ts = new TS();
        productions = new Productions();
        stackSequence.push("$ 0");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression: ");
        startSequence = scanner.nextLine();
        System.out.println("Following expression entered '" + startSequence + "'");
    }

    public void fabricate(){
        System.out.println("Starting with stack :" + stackSequence);
        System.out.println("Starting with sequence :" + startSequence);
        int startNumber = 0;


        String enterWord = getTermFromEnterSequence();
        int indexForTa = getIndexForTa(enterWord);
        String dR = lookAtActionTable(startNumber, indexForTa);
        System.out.println("Pushing: " + enterWord + " and " + dR);
        stackSequence.push(enterWord);
        stackSequence.push(dR.charAt(1) + "");

    }

    private String lookAtActionTable(int startNumber, int indexForTa) {
        String [][]actionTable = ta.getActionTable();
        if (actionTable[startNumber][indexForTa].equals("null")) {
            return "-1";
        }

        return actionTable[startNumber][indexForTa];
    }

    private int getIndexForTa(String enterWord) {
        String [] terms = ta.getTerms();
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].equals(enterWord)) {
                return i;
            }
        }
        return -1;
    }

    private String getTermFromEnterSequence() {
        String[] terms = ta.getTerms();
        String term = "";
        for (String i : startSequence.split("")) {
            term+= i;
            for (String j : terms) {
                if (j.equals(term)) {
                    removeTerm(term);
                    return term;
                }
            }
        }

        return null;
    }

    private void removeTerm(String term) {
        startSequence = startSequence.replace(term, "");
        System.out.println("Removed: " + term + " from sequence :" + startSequence);
    }
}
