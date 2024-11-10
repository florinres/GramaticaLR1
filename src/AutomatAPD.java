import java.util.Stack;
import java.util.Scanner;

public class AutomatAPD {
    TA ta;
    TS ts;
    Productions pd;
    Stack<String> stackSequence = new Stack<>();
    String startSequence;

    AutomatAPD() {
        ta = new TA();
        ts = new TS();
        pd = new Productions();
        stackSequence.push("$");
        stackSequence.push("0");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression: ");
        startSequence = scanner.nextLine();
        System.out.println("Following expression entered '" + startSequence + "'");
    }

    public void fabricate(){
        System.out.println("Starting with stack :" + stackSequence);
        System.out.println("Starting with sequence :" + startSequence
                + "\nexecuting initial operation");
        while(startSequence.length() > 1){
            System.out.println("Looking at stack: " + stackSequence
                    +"\npeeking from stack...");
            String termFromStack = stackSequence.peek();
            String termFromSequence = getTermFromEnterSequence();
            System.out.println("Looking for combination: " + termFromStack + " " + termFromSequence);
            int indexTa = getIndexForTa(termFromSequence);
            String dR = lookAtActionTable(getStateNumber(), indexTa);
            if (isShift(dR)){
                System.out.println("Its a shift!");
                String pushState = dR.charAt(1) + "";
                stackSequence.push(termFromSequence);
                stackSequence.push(pushState);
                assert termFromSequence != null;
                removeTerm(termFromSequence);
            } else {
                System.out.println("Its a reduction!");
                String pushState = dR.charAt(1) + "";
                stackReduction(pushState);
                lookAtJumpTable();
            }
        }
        System.out.println("Ending with stack :" + stackSequence);
        System.out.println("Done");
    }

    private boolean isShift(String dR) {
        String compareChar;
        if (dR.isEmpty()){
            throw new RuntimeException("Unsupported char");
        }
        compareChar = dR.charAt(0) + "";
        return compareChar.equals("d");
    }

    private int getStateNumber() {
        Stack<String> copyStack = (Stack<String>) stackSequence.clone();

        return Integer.parseInt(copyStack.peek());
    }

    private void lookAtJumpTable() {
        String[][] jumpTable = ts.getActionTable();
        Stack<String> copyStack = (Stack<String>) stackSequence.clone();
        String newStateTerm = copyStack.pop();
        int numberOfState = Integer.parseInt(copyStack.pop());
        String newState = "";
        for (int i = 0; i < 3;i++){
            if(hasStateBeenFound(jumpTable[numberOfState][i], newStateTerm)){
                newState = jumpTable[numberOfState][i].charAt(2) + "";
                break;
            }
        }
        stackSequence.push(newState);
        System.out.println("Stack has been modified: " + stackSequence);
    }

    private boolean hasStateBeenFound(String s, String newStateTerm) {
        String compareTerm = s.charAt(0) + "";
        return compareTerm.equals(newStateTerm);
    }

    private void stackReduction(String numberOfProduction) {
        String[][] productions = pd.getProductions();
        int a = Integer.parseInt(numberOfProduction) - 1;
        String reduction = productions[a][0];
        stackSequence.pop();
        stackSequence.pop();
        stackSequence.push(reduction);
    }

    //obtains your shift or reduction
    private String lookAtActionTable(int columnIndex, int indexForTa) {
        String [][]actionTable = ta.getActionTable();
            if (actionTable[columnIndex][indexForTa] == null) {
                throw new UndefinedBehaviour("State not defined at column: " + columnIndex + " " +
                        "\nand term " + getUndefinedStateTerm(indexForTa));
            }

        return actionTable[columnIndex][indexForTa];
    }

    private String getUndefinedStateTerm(int index) {
        String []states = ta.getTerms();

        return states[index];
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
                    return term;
                }
            }
        }

        return null;
    }

    private void removeTerm(String term) {
        String lastCharOfTerm = term.substring(term.length() - 1);
        int indexOfTerm = startSequence.indexOf(lastCharOfTerm);
        startSequence = startSequence.substring(indexOfTerm + 1);
    }
}
