public class UndefinedBehaviour extends RuntimeException {
    public UndefinedBehaviour(String message) {
        super(message);
        System.out.println(message);
    }
}
