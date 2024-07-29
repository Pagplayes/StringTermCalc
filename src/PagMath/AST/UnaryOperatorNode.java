package PagMath.AST;

public class UnaryOperatorNode extends ASTNode {
    private final ASTNode operand;
    private final String operator;

    public UnaryOperatorNode(ASTNode operand, String operator) {
        this.operand = operand;
        this.operator = operator;
    }

    @Override
    public double evaluate(){
        double value = operand.evaluate();
        switch (operator) {
            case "-":
                return -value;
            default:
                throw new IllegalArgumentException(STR."Unkown unary operator: \{operator}");
        }
    }

    @Override
    public String toString() {
        return STR."UnaryOperatorNode [operand=\{operand}, operator=\{operator}]";
    }
}
