import java.util.*;


class GrammarParser {

    static class Gramatica {
        Set<String> terminale;
        Set<String> neterminale;
        List<Productions> productii;
        String simbolStart;

        public Gramatica(Set<String> terminale, Set<String> neterminale, List<Productions> productii, String simbolStart) {
            this.terminale = terminale;
            this.neterminale = neterminale;
            this.productii = productii;
            this.simbolStart = simbolStart;
        }
    }

    static class Productions {
        String left;
        List<String> right;

        public Productions(String left, List<String> right) {
            this.left = left;
            this.right = right;
        }
    }

    static class Item {
        String left;
        List<String> right;
        int dotPos;
        String lookahead;

        public Item(String left, List<String> right, int dotPos, String lookahead) {
            this.left = left;
            this.right = right;
            this.dotPos = dotPos;
            this.lookahead = lookahead;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return dotPos == item.dotPos && Objects.equals(left, item.left) && Objects.equals(right, item.right) && Objects.equals(lookahead, item.lookahead);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right, dotPos, lookahead);
        }
    }

    public static Gramatica citireGramatica() {
        Set<String> terminale = new HashSet<>(Arrays.asList("a", "+", "-", "*", "/", "(", ")", "id"));
        Set<String> neterminale = new HashSet<>(Arrays.asList("E", "T", "F"));
        String simbolStart = "E";

        List<Productions> productii = Arrays.asList(
                new Productions("E", Arrays.asList("E", "+", "T")),
                new Productions("E", Arrays.asList("T")),
                new Productions("T", Arrays.asList("T", "*", "F")),
                new Productions("T", Arrays.asList("F")),
                new Productions("F", Arrays.asList("(", "E", ")")),
                new Productions("F", Arrays.asList("id")),
                new Productions("F", Arrays.asList("-", "(", "E", ")"))
        );

        return new Gramatica(terminale, neterminale, productii, simbolStart);
    }

    public static Set<Item> closure(Set<Item> items, Gramatica gramatica) {
        Set<Item> closureSet = new HashSet<>(items);

        boolean changed;
        do {
            changed = false;
            Set<Item> newItems = new HashSet<>();
            for (Item item : closureSet) {
                if (item.dotPos < item.right.size() && gramatica.neterminale.contains(item.right.get(item.dotPos))) {
                    String B = item.right.get(item.dotPos);
                    for (Productions productie : gramatica.productii) {
                        if (productie.left.equals(B)) {
                            Item newItem = new Item(productie.left, productie.right, 0, item.lookahead);
                            if (!closureSet.contains(newItem)) {
                                newItems.add(newItem);
                                changed = true;
                            }
                        }
                    }
                }
            }
            closureSet.addAll(newItems);
        } while (changed);

        return closureSet;
    }

    public static Set<Item> goTo(Set<Item> items, String simbol, Gramatica gramatica) {
        Set<Item> newItems = new HashSet<>();

        for (Item item : items) {
            if (item.dotPos < item.right.size() && item.right.get(item.dotPos).equals(simbol)) {
                newItems.add(new Item(item.left, item.right, item.dotPos + 1, item.lookahead));
            }
        }

        return newItems.isEmpty() ? null : closure(newItems, gramatica);
    }

    public static void genereazaTabele(Gramatica gramatica) {
        Set<Item> initialItem = new HashSet<>();
        initialItem.add(new Item(gramatica.simbolStart, gramatica.productii.get(0).right, 0, "$"));

        List<Set<Item>> stari = new ArrayList<>();
        stari.add(closure(initialItem, gramatica));
        Map<Pair<Integer, String>, Integer> tranzitii = new HashMap<>();
        Map<Pair<Integer, String>, String> actiuni = new HashMap<>();
        Map<Pair<Integer, String>, Integer> salt = new HashMap<>();

        boolean changed;
        do {
            changed = false;
            List<Set<Item>> newStates = new ArrayList<>();

            for (int i = 0; i < stari.size(); i++) {
                Set<Item> stare = stari.get(i);

                for (String simbol : gramatica.terminale) {
                    Set<Item> nouaStare = goTo(stare, simbol, gramatica);

                    if (nouaStare != null && !stari.contains(nouaStare)) {
                        stari.add(nouaStare);
                        newStates.add(nouaStare);
                        changed = true;
                    }
                }
            }
        } while (changed);
    }
}