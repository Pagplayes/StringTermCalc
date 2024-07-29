package PagMath.AST;

public class FunctionNode extends ASTNode {
    String function;
    ASTNode child;
    boolean isDegree;

    public FunctionNode(String function, ASTNode child) {
        if (function.endsWith("°")) {
            this.function = function.substring(0, function.length() - 1);
            this.isDegree = true;
        } else {
            this.function = function;
            this.isDegree = false;
        }
        this.child = child;
    }

    @Override
    public double evaluate() {
        double value = child.evaluate();
        if (isDegree) {
            value = Math.toRadians(value);
        }

        try {
            switch (function) {
                case "sqrt":
                    return Math.sqrt(value);
                case "sin":
                    return Math.sin(value);
                case "cos":
                    return Math.cos(value);
                case "tan":
                    return Math.tan(value);
                case "log":
                    return Math.log10(value);
                case "ln":
                    return Math.log(value);
                default:
                    throw new IllegalArgumentException(STR."Unknown function: \{function}");
            }
        } catch (ArithmeticException e) {
            System.err.println("Math error: " + e.getMessage());
            return Double.NaN; // or handle as preferred
        }
    }

    @Override
    public String toString() {
        return STR."FunctionNode [function=\{function}, child=\{child}, isDegree=\{isDegree}]";
    }
}



