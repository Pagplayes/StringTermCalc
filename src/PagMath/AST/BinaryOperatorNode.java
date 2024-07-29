package PagMath.AST;

public class BinaryOperatorNode extends ASTNode {
    private final ASTNode left;
    private final ASTNode right;
    private final String operator;

    public BinaryOperatorNode(ASTNode left, ASTNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double evaluate() {
        double leftVal = left.evaluate();
        double rightVal = right.evaluate();

        switch (operator.toString()) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            case "/":
                if (rightVal == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return leftVal / rightVal;
            case "%":
                return leftVal % rightVal;
            case "^":
                return Math.pow(leftVal, rightVal);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    @Override
    public String toString() {
        return STR."BinaryOperatorNode [left=\{left}, right=\{right}, operator=\{operator}]";
    }

}
