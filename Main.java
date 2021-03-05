import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Calculator parser = new Calculator(System.in);
            System.err.println(parser.eval());
        }
        catch (IOException | ParseError e) {
            System.err.println(e.getMessage());
        }
    }
}
