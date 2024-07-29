package PagMath.AST;

public class NumberNode extends ASTNode {
    double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(){
        return value;
    }
}
