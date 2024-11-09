import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

public class AutomatAPD {
    TA ta;
    TS ts;
    Productions pd;
    Stack<String> stackSequence = new Stack<>();
    String startSequence = new String();

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
        String enterWord = getTermFromEnterSequence();
        int indexForTa = getIndexForTa(enterWord);
        String dR = lookAtActionTable(topOfStackToInt(), indexForTa);
        System.out.println("Pushing word and number");
        stackSequence.push(enterWord);
        stackSequence.push(dR.charAt(1)+"");
        System.out.println("Pushed: " + enterWord + " " + dR.charAt(1));
        while(startSequence.length() > 1){
            System.out.println("Looking at stack: " + stackSequence
                    +"\npeeking from stack...");
            String termFromStack = stackSequence.peek();
            String termFromSequence = getTermFromEnterSequence();
            System.out.println("Looking for combination: " + termFromStack + " " + termFromSequence);
            int indexTa = getIndexForTa(termFromSequence);
            dR = lookAtActionTable(Integer.parseInt(termFromStack), indexTa);
            if ((dR.charAt(0) + "").equals("d")){
                System.out.println("Its a shift!");
                stackShifting(dR.charAt(1) + "");
            } else{
                System.out.println("Its a reduction!");
                stackReduction(dR.charAt(1) + "");
            }
            System.out.println("Jumping at next state");
            jumpToNextState();
        }
        System.out.println("Ending with stack :" + stackSequence);
        System.out.println("Done");
    }

    private void jumpToNextState() {
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
        if (compareTerm.equals(newStateTerm)){
            return true;
        }

        return false;
    }

    private void stackReduction(String r) {
        String[][] productions = pd.getProductions();
        Integer a = Integer.parseInt(r) - 1;
        String reduction = productions[a][0];
        stackSequence.pop();
        stackSequence.pop();
        stackSequence.push(reduction);
    }

    private void stackShifting(String d) {
        String[][] production = pd.getProductions();
    }

    private int topOfStackToInt() {
        return Integer.parseInt(stackSequence.peek());
    }

    //obtains your shift or reduction
    private String lookAtActionTable(int startNumber, int indexForTa) {
        String [][]actionTable = ta.getActionTable();
            if (actionTable[startNumber][indexForTa] == null) {
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
        String lastCharOfTerm = term.substring(term.length() - 1);
        int indexOfTerm = startSequence.indexOf(lastCharOfTerm);
        startSequence = startSequence.substring(indexOfTerm + 1);
    }
}
