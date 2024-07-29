package PagMath.AST;

import java.util.List;

public class Parser {
    private List<String> tokens;
    private int currentTokenIndex;

    public static ASTNode parse(List<String> tokens) {
        Parser parser = new Parser(tokens);
        return parser.parseExpression();
    }

    private Parser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    private ASTNode parseExpression() {
        ASTNode node = parseTerm();
        while (hasNext() && (peek().equals("+") || peek().equals("-"))) {
            String operator = next();
            ASTNode right = parseTerm();
            node = new OperatorNode(operator, node, right);
        }
        return node;
    }

    private ASTNode parseTerm() {
        ASTNode node = parseFactor();
        while (hasNext() && (peek().equals("*") || peek().equals("/") || peek().equals("%"))) {
            String operator = next();
            ASTNode right = parseFactor();
            node = new OperatorNode(operator,node, right);
        }
        return node;
    }

    private ASTNode parseFactor() {
        String token = next();
        if (token.equals("(")) {
            ASTNode node = parseExpression();
            expect(")");
            return node;
        } else if (isNumber(token)) {
            return new NumberNode(Double.parseDouble(token));
        } else if (isFunction(token)) {
            expect("(");
            ASTNode argument = parseExpression();
            expect(")");
            return new FunctionNode(token,argument);
        } else if (token.equals("-")) {
            ASTNode node = parseFactor();
            return new UnaryOperatorNode(node, "-");
        } else {
            throw new IllegalArgumentException("Unexpected token: " + token);
        }
    }

    private void expect(String expectedToken) {
        if (!next().equals(expectedToken)) {
            throw new IllegalArgumentException("Expected token: " + expectedToken);
        }
    }

    private boolean hasNext() {
        return currentTokenIndex < tokens.size();
    }

    private String peek() {
        return tokens.get(currentTokenIndex);
    }

    private String next() {
        return tokens.get(currentTokenIndex++);
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFunction(String token) {
        return token.equals("sqrt") || token.equals("sin") || token.equals("cos") || token.equals("tan") || token.equals("log") || token.equals("ln");
    }
}

