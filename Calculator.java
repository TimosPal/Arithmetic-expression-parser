import java.io.InputStream;
import java.io.IOException;

class Calculator {

    private int lookaheadToken;
    private InputStream in;

    public Calculator(InputStream in) throws IOException {
		this.in = in;
		lookaheadToken = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
		if (lookaheadToken != symbol)
			throw new ParseError();
		lookaheadToken = in.read();
    }

    private Boolean isEof(int lookaheadToken){
        return lookaheadToken == '\n' || lookaheadToken == -1;
    }

    private Boolean isNumDigit(int lookaheadToken){
        return lookaheadToken >= '0' && lookaheadToken <= '9';
    }

    public int expr() throws IOException, ParseError {
        if(isNumDigit(lookaheadToken) || lookaheadToken == '('){
            int result = term();
            return expr2(result);
        }else{
            throw new ParseError();
        }
    }

    public int expr2(int value) throws IOException, ParseError {
        if(lookaheadToken == '+' || lookaheadToken == '-'){
            boolean isPlus = lookaheadToken == '+';
            consume(lookaheadToken);
            int result = term();
            result = (isPlus) ? value + result : value - result;
            return expr2(result);
        }else if(lookaheadToken == ')' || isEof(lookaheadToken)){
            return value;
        }else{
            throw new ParseError();
        }
    }

    public int term() throws IOException, ParseError {
        if(isNumDigit(lookaheadToken) || lookaheadToken == '('){
            int result = fac();
            return term2(result);
        }else{
            throw new ParseError();
        }
    }

    public int term2(int value) throws IOException, ParseError {
        if(lookaheadToken == '+' || lookaheadToken == '-' || lookaheadToken == ')' || isEof(lookaheadToken)){
            return value;
        }else if(lookaheadToken == '*' || lookaheadToken == '/'){
            boolean isMult = lookaheadToken == '*';
            consume(lookaheadToken);
            int result = fac();
            result = (isMult) ? value * result : value / result;
            return term2(result);
        }else{
            throw new ParseError();
        }
    }

    public int ParseNumber() throws IOException, ParseError {
        int number = 0;
        while(isNumDigit(lookaheadToken)){
            int digit = lookaheadToken - '0';
            number = number*10 + digit;

            consume(lookaheadToken);
        }
        return number;
    }

    public int fac() throws IOException, ParseError {
        if(lookaheadToken == '('){
            consume('(');
            int result = expr();
            consume(')');
            return result;
        }else if(isNumDigit(lookaheadToken)){
            return ParseNumber();
        }else{
            throw new ParseError();
        }
    }

    public int eval() throws IOException, ParseError {
        int result = expr();
		if (!isEof(lookaheadToken))
			throw new ParseError();
		return result;
    }

}