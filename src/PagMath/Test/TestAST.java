package PagMath.Test;

import PagMath.AST.*;

public class TestAST {
    public static void main(String[] args) {
        //testUnaryOperatorNode();
        //testBinaryOperatorNode();
        testFunctionNode();
        //testComplexExpressions();
    }

    public static void testUnaryOperatorNode() {
        System.out.println("Testing UnaryOperatorNode");
        ASTNode negativeFive = new UnaryOperatorNode(new NumberNode(5), "-");
        System.out.println(STR."Expected: -5, Actual: \{negativeFive.evaluate()} \n");

        ASTNode negativeZero = new UnaryOperatorNode(new NumberNode(0), "-");
        System.out.println(STR."Expected: 0, Actual: \{negativeZero.evaluate()} \n");
    }

    public static void testBinaryOperatorNode() {
        System.out.println("Testing BinaryOperatorNode");
        ASTNode addition = new BinaryOperatorNode(new NumberNode(2), new NumberNode(3), "+");
        System.out.println(STR."Expected: 5, Actual: \{addition.evaluate()} \n");

        ASTNode multiplication = new BinaryOperatorNode(new NumberNode(4), new NumberNode(5), "*");
        System.out.println(STR."Expected: 20, Actual: \{multiplication.evaluate()} \n");
    }

    public static void testFunctionNode() {
        System.out.println("Testing FunctionNode");

        // Square root tests
        ASTNode sqrtNine = new FunctionNode("sqrt", new NumberNode(9));
        System.out.println(STR."Expected: 3, Actual: \{sqrtNine.evaluate()} \n");

        ASTNode sqrtZero = new FunctionNode("sqrt", new NumberNode(0));
        System.out.println(STR."Expected: 0, Actual: \{sqrtZero.evaluate()} \n");

        ASTNode sqrtNegative = new FunctionNode("sqrt", new NumberNode(-9));
        System.out.println(STR."Expected: NaN or error, Actual: \{sqrtNegative.evaluate()} \n");

        // Sin tests (Radians)
        ASTNode sinThirty = new FunctionNode("sin", new NumberNode(Math.toRadians(30)));
        System.out.println(STR."Expected: 0.5, Actual: \{sinThirty.evaluate()} \n");

        ASTNode sinZero = new FunctionNode("sin", new NumberNode(0));
        System.out.println(STR."Expected: 0, Actual: \{sinZero.evaluate()} \n");

        ASTNode sinNinety = new FunctionNode("sin", new NumberNode(Math.toRadians(90)));
        System.out.println(STR."Expected: 1, Actual: \{sinNinety.evaluate()} \n");

        // Sin tests (Degrees)
        ASTNode sinThirtyDeg = new FunctionNode("sin°", new NumberNode(30));
        System.out.println(STR."Expected: 0.5, Actual: \{sinThirtyDeg.evaluate()} \n");

        ASTNode sinNinetyDeg = new FunctionNode("sin°", new NumberNode(90));
        System.out.println(STR."Expected: 1, Actual: \{sinNinetyDeg.evaluate()} \n");

        // Cos tests (Radians)
        ASTNode cosZero = new FunctionNode("cos", new NumberNode(0));
        System.out.println(STR."Expected: 1, Actual: \{cosZero.evaluate()} \n");

        ASTNode cosNinety = new FunctionNode("cos", new NumberNode(Math.toRadians(90)));
        System.out.println(STR."Expected: 0, Actual: \{cosNinety.evaluate()} \n");

        // Cos tests (Degrees)
        ASTNode cosZeroDeg = new FunctionNode("cos°", new NumberNode(0));
        System.out.println(STR."Expected: 1, Actual: \{cosZeroDeg.evaluate()} \n");

        ASTNode cosNinetyDeg = new FunctionNode("cos°", new NumberNode(90));
        System.out.println(STR."Expected: 0, Actual: \{cosNinetyDeg.evaluate()} \n");

        // Tan tests (Radians)
        ASTNode tanZero = new FunctionNode("tan", new NumberNode(0));
        System.out.println(STR."Expected: 0, Actual: \{tanZero.evaluate()} \n");

        ASTNode tanFortyFive = new FunctionNode("tan", new NumberNode(Math.toRadians(45)));
        System.out.println(STR."Expected: 1, Actual: \{tanFortyFive.evaluate()} \n");

        ASTNode tanNinety = new FunctionNode("tan", new NumberNode(Math.toRadians(90)));
        System.out.println(STR."Expected: Undefined or large number, Actual: \{tanNinety.evaluate()} \n");

        // Tan tests (Degrees)
        ASTNode tanZeroDeg = new FunctionNode("tan°", new NumberNode(0));
        System.out.println(STR."Expected: 0, Actual: \{tanZeroDeg.evaluate()} \n");

        ASTNode tanFortyFiveDeg = new FunctionNode("tan°", new NumberNode(45));
        System.out.println(STR."Expected: 1, Actual: \{tanFortyFiveDeg.evaluate()} \n");

        ASTNode tanNinetyDeg = new FunctionNode("tan°", new NumberNode(90));
        System.out.println(STR."Expected: Undefined or large number, Actual: \{tanNinetyDeg.evaluate()} \n");

        // Logarithm and natural logarithm tests
        ASTNode logTen = new FunctionNode("log", new NumberNode(10));
        System.out.println(STR."Expected: 1, Actual: \{logTen.evaluate()} \n");

        ASTNode logOne = new FunctionNode("log", new NumberNode(1));
        System.out.println(STR."Expected: 0, Actual: \{logOne.evaluate()} \n");

        ASTNode logZero = new FunctionNode("log", new NumberNode(0));
        System.out.println(STR."Expected: Undefined or error, Actual: \{logZero.evaluate()} \n");

        ASTNode logNegative = new FunctionNode("log", new NumberNode(-10));
        System.out.println(STR."Expected: Undefined or error, Actual: \{logNegative.evaluate()} \n");

        ASTNode lnE = new FunctionNode("ln", new NumberNode(Math.E));
        System.out.println(STR."Expected: 1, Actual: \{lnE.evaluate()} \n");

        ASTNode lnOne = new FunctionNode("ln", new NumberNode(1));
        System.out.println(STR."Expected: 0, Actual: \{lnOne.evaluate()} \n");

        ASTNode lnZero = new FunctionNode("ln", new NumberNode(0));
        System.out.println(STR."Expected: Undefined or error, Actual: \{lnZero.evaluate()} \n");

        ASTNode lnNegative = new FunctionNode("ln", new NumberNode(-1));
        System.out.println(STR."Expected: Undefined or error, Actual: \{lnNegative.evaluate()} \n");
    }


    public static void testComplexExpressions() {
        System.out.println("Testing ComplexExpressions");
        ASTNode expression = new BinaryOperatorNode(
                new UnaryOperatorNode(new NumberNode(5), "-"),
                new BinaryOperatorNode(new NumberNode(3), new NumberNode(2), "*"),
                "+"
        );
        System.out.println(STR."Expected: 1, Actual: \{expression.evaluate()} \n");
    }
}
