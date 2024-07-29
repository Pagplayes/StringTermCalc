package PagMath.AST;

public class OperatorNode extends ASTNode {
    String operator;

    ASTNode left,right;

    OperatorNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate(){
        switch (operator) {
            case "+":
                return left.evaluate() + right.evaluate();
            case "-":
                return left.evaluate() - right.evaluate();
            case "*":
                return left.evaluate() * right.evaluate();
            case "/":
                return left.evaluate() / right.evaluate();
            case "%":
                return left.evaluate() % right.evaluate();
            case "^":
                return Math.pow(left.evaluate(), right.evaluate());
            default:
                throw new IllegalArgumentException(STR."Unknown operator: \{operator}");
        }
    }
}
