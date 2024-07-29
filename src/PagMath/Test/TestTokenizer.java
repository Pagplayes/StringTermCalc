package PagMath.Test;

import PagMath.AST.Tokenizer;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static PagMath.AST.Tokenizer.simplyTokens;

public class TestTokenizer {
    public static void main(String[] args) {
        //testTokenizer();
        //testCorrectTokens();
        //testAdditionalCorrectTokens();
        testSimplifyTokens();
        //testTokenizerWithCorrection();
    }

    public static void testTokenizer() {
        System.out.println("Running testTokenizer...");
        // Call individual test methods with debug outputs
        testSimpleExpression();
        System.out.println("Finished testSimpleExpression.");

        testParentheses();
        System.out.println("Finished testParentheses.");

        testFunctions();
        System.out.println("Finished testFunctions.");

        testNegativeNumbers();
        System.out.println("Finished testNegativeNumbers.");

        testMixedOperators();
        System.out.println("Finished testMixedOperators.");

        testComplexExpression();
        System.out.println("Finished testComplexExpression.");

        System.out.println("Finished testTokenizer.");
        ;
    }

    public static void testSimpleExpression() {
        System.out.println("Testing Simple Expression");
        List<String> tokens = Tokenizer.getCalculationAsList("3 + 5 * 2");
        System.out.println(STR."Expected: [3, +, 5, *, 2], Actual: \{tokens} \n");
    }

    public static void testParentheses() {
        System.out.println("Testing Parentheses Handling");
        List<String> tokens = Tokenizer.getCalculationAsList("(3 + 5) * 2");
        System.out.println(STR."Expected: [(, 3, +, 5, ), *, 2], Actual: \{tokens} \n");
    }

    public static void testFunctions() {
        System.out.println("Testing Functions");
        List<String> tokens = Tokenizer.getCalculationAsList("sin(30) + cos(60)");
        System.out.println(STR."Expected: [sin, (, 30, ), +, cos, (, 60, )], Actual: \{tokens} \n");
    }

    public static void testNegativeNumbers() {
        System.out.println("Testing Negative Numbers");
        List<String> tokens = Tokenizer.getCalculationAsList("-3 + 5");
        System.out.println(STR."Expected: [-3, +, 5], Actual: \{tokens} \n");

        List<String> tokens2 = Tokenizer.getCalculationAsList("3 + -5");
        System.out.println(STR."Expected: [3, +, -5], Actual: \{tokens2} \n");
    }

    public static void testMixedOperators() {
        System.out.println("Testing Mixed Operators");
        List<String> tokens = Tokenizer.getCalculationAsList("3 + 5 * 2 - 4 / 2");
        System.out.println(STR."Expected: [3, +, 5, *, 2, -, 4, /, 2], Actual: \{tokens} \n");
    }

    public static void testComplexExpression() {
        System.out.println("Testing Complex Expression");
        List<String> tokens = Tokenizer.getCalculationAsList("3 * (sin(45) + 2) - sqrt(16)");
        System.out.println(STR."Expected: [3, *, (, sin, (, 45, ), +, 2, ), -, sqrt, (, 16, )], Actual: \{tokens} \n");
    }

    public static void testCorrectTokens() {
        System.out.println("Testing Correct Tokens");

        List<String> tokens1 = List.of("-", "3", "+", "5");
        boolean result1 = Objects.equals(Tokenizer.correctTokens(tokens1), List.of("-3", "+", "5"));
        System.out.println(STR."Original: \{tokens1}, Corrected: \{Tokenizer.correctTokens(tokens1)} -> Expected: [-3, +, 5] -> Result: \{result1}\n");

        List<String> tokens2 = List.of("3", "+", "-", "5");
        boolean result2 = Objects.equals(Tokenizer.correctTokens(tokens2), List.of("3", "+", "-5"));
        System.out.println(STR."Original: \{tokens2}, Corrected: \{Tokenizer.correctTokens(tokens2)} -> Expected: [3, +, -5] -> Result: \{result2}\n");

        List<String> tokens3 = List.of("sin", "(", "-", "30", ")");
        boolean result3 = Objects.equals(Tokenizer.correctTokens(tokens3), List.of("sin", "(", "-30", ")"));
        System.out.println(STR."Original: \{tokens3}, Corrected: \{Tokenizer.correctTokens(tokens3)} -> Expected: [sin, (, -30, )] -> Result: \{result3}\n");

        List<String> tokens4 = List.of("-", "sqrt", "(", "16", ")");
        boolean result4 = Objects.equals(Tokenizer.correctTokens(tokens4), List.of("-sqrt", "(", "16", ")"));
        System.out.println(STR."Original: \{tokens4}, Corrected: \{Tokenizer.correctTokens(tokens4)} -> Expected: [-sqrt, (, 16, )] -> Result: \{result4}\n");

        List<String> tokens5 = List.of("3", "+", "-", "(", "5", "+", "2", ")");
        boolean result5 = Objects.equals(Tokenizer.correctTokens(tokens5), List.of("3", "+", "-(", "5", "+", "2", ")"));
        System.out.println(STR."Original: \{tokens5}, Corrected: \{Tokenizer.correctTokens(tokens5)} -> Expected: [3, +, -(, 5, +, 2, )] -> Result: \{result5}\n");

        List<String> tokens6 = List.of("-", "5", "*", "(", "-", "3", ")");
        boolean result6 = Objects.equals(Tokenizer.correctTokens(tokens6), List.of("-5", "*", "(", "-3", ")"));
        System.out.println(STR."Original: \{tokens6}, Corrected: \{Tokenizer.correctTokens(tokens6)} -> Expected: [-5, *, (, -3, )] -> Result: \{result6}\n");

        List<String> tokens7 = List.of("5", "-", "-", "3");
        boolean result7 = Objects.equals(Tokenizer.correctTokens(tokens7), List.of("5", "-", "-3"));
        System.out.println(STR."Original: \{tokens7}, Corrected: \{Tokenizer.correctTokens(tokens7)} -> Expected: [5, -, -3] -> Result: \{result7}\n");

        List<String> tokens8 = List.of("(", "-", "3", ")", "*", "5");
        boolean result8 = Objects.equals(Tokenizer.correctTokens(tokens8), List.of("(", "-3", ")", "*", "5"));
        System.out.println(STR."Original: \{tokens8}, Corrected: \{Tokenizer.correctTokens(tokens8)} -> Expected: [(, -3, ), *, 5] -> Result: \{result8}\n");

        List<String> tokens9 = List.of("sqrt", "(", "-", "9", ")");
        boolean result9 = Objects.equals(Tokenizer.correctTokens(tokens9), List.of("sqrt", "(", "-9", ")"));
        System.out.println(STR."Original: \{tokens9}, Corrected: \{Tokenizer.correctTokens(tokens9)} -> Expected: [sqrt, (, -9, )] -> Result: \{result9}\n");

        List<String> tokens10 = List.of("(", "-", "(", "3", "+", "5", ")", ")");
        boolean result10 = Objects.equals(Tokenizer.correctTokens(tokens10), List.of("(", "-(", "3", "+", "5", ")", ")"));
        System.out.println(STR."Original: \{tokens10}, Corrected: \{Tokenizer.correctTokens(tokens10)} -> Expected: [(, -(, 3, +, 5, ), )] -> Result: \{result10}\n");
    }

    public static void testAdditionalCorrectTokens() {
        System.out.println("Testing Additional Correct Tokens");

        List<String> tokens1 = Tokenizer.getCalculationAsList("--3");
        System.out.println(STR."Original: \{tokens1}, Corrected: \{Tokenizer.correctTokens(tokens1)} -> Expected: [3] -> Result: \{Tokenizer.correctTokens(tokens1).equals(List.of("3"))}\n");

        List<String> tokens2 = Tokenizer.getCalculationAsList("---3");
        System.out.println(STR."Original: \{tokens2}, Corrected: \{Tokenizer.correctTokens(tokens2)} -> Expected: [-3] -> Result: \{Tokenizer.correctTokens(tokens2).equals(List.of("-3"))}\n");

        List<String> tokens3 = Tokenizer.getCalculationAsList("-cos(60)");
        System.out.println(STR."Original: \{tokens3}, Corrected: \{Tokenizer.correctTokens(tokens3)} -> Expected: [-cos, (, 60, )] -> Result: \{Tokenizer.correctTokens(tokens3).equals(List.of("-cos", "(", "60", ")"))}\n");

        List<String> tokens4 = Tokenizer.getCalculationAsList("-log(10)");
        System.out.println(STR."Original: \{tokens4}, Corrected: \{Tokenizer.correctTokens(tokens4)} -> Expected: [-log, (, 10, )] -> Result: \{Tokenizer.correctTokens(tokens4).equals(List.of("-log", "(", "10", ")"))}\n");

        List<String> tokens5 = Tokenizer.getCalculationAsList("-(3 * -2)");
        System.out.println(STR."Original: \{tokens5}, Corrected: \{Tokenizer.correctTokens(tokens5)} -> Expected: [-(, 3, *, -2, )] -> Result: \{Tokenizer.correctTokens(tokens5).equals(List.of("-(3", "*", "-2", ")"))}\n");

        List<String> tokens6 = Tokenizer.getCalculationAsList("-(sin(45) - cos(30))");
        System.out.println(STR."Original: \{tokens6}, Corrected: \{Tokenizer.correctTokens(tokens6)} -> Expected: [-(, sin, (, 45, ), -, cos, (, 30, ), )] -> Result: \{Tokenizer.correctTokens(tokens6).equals(List.of("-(sin", "(", "45", ")", "-", "cos", "(", "30", ")", ")"))}\n");

        List<String> tokens7 = Tokenizer.getCalculationAsList("-x");
        System.out.println(STR."Original: \{tokens7}, Corrected: \{Tokenizer.correctTokens(tokens7)} -> Expected: [-x] -> Result: \{Tokenizer.correctTokens(tokens7).equals(List.of("-x"))}\n");

        List<String> tokens8 = Tokenizer.getCalculationAsList("-3x");
        System.out.println(STR."Original: \{tokens8}, Corrected: \{Tokenizer.correctTokens(tokens8)} -> Expected: [-3x] -> Result: \{Tokenizer.correctTokens(tokens8).equals(List.of("-3x"))}\n");

        List<String> tokens9 = Tokenizer.getCalculationAsList("3 +- 5");
        System.out.println(STR."Original: \{tokens9}, Corrected: \{Tokenizer.correctTokens(tokens9)} -> Expected: [3, +, -5] -> Result: \{Tokenizer.correctTokens(tokens9).equals(List.of("3", "+", "-5"))}\n");

        List<String> tokens10 = Tokenizer.getCalculationAsList("5 -- 3");
        System.out.println(STR."Original: \{tokens10}, Corrected: \{Tokenizer.correctTokens(tokens10)} -> Expected: [5, -, -3] -> Result: \{Tokenizer.correctTokens(tokens10).equals(List.of("5", "-", "-3"))}\n");

        List<String> tokens11 = Tokenizer.getCalculationAsList("sin(cos(-45))");
        System.out.println(STR."Original: \{tokens11}, Corrected: \{Tokenizer.correctTokens(tokens11)} -> Expected: [sin, (, cos, (, -45, ), )] -> Result: \{Tokenizer.correctTokens(tokens11).equals(List.of("sin", "(", "cos", "(", "-45", ")", ")"))}\n");

        List<String> tokens12 = Tokenizer.getCalculationAsList("log(sqrt(16))");
        System.out.println(STR."Original: \{tokens12}, Corrected: \{Tokenizer.correctTokens(tokens12)} -> Expected: [log, (, sqrt, (, 16, ), )] -> Result: \{Tokenizer.correctTokens(tokens12).equals(List.of("log", "(", "sqrt", "(", "16", ")", ")"))}\n");

        List<String> tokens13 = Tokenizer.getCalculationAsList("-0");
        System.out.println(STR."Original: \{tokens13}, Corrected: \{Tokenizer.correctTokens(tokens13)} -> Expected: [-0] -> Result: \{Tokenizer.correctTokens(tokens13).equals(List.of("-0"))}\n");

        List<String> tokens14 = Tokenizer.getCalculationAsList("--0");
        System.out.println(STR."Original: \{tokens14}, Corrected: \{Tokenizer.correctTokens(tokens14)} -> Expected: [0] -> Result: \{Tokenizer.correctTokens(tokens14).equals(List.of("0"))}\n");

        List<String> tokens15 = Tokenizer.getCalculationAsList("-");
        System.out.println(STR."Original: \{tokens15}, Corrected: \{Tokenizer.correctTokens(tokens15)} -> Expected: [-] -> Result: \{Tokenizer.correctTokens(tokens15).equals(List.of("-"))}\n");

        List<String> tokens16 = Tokenizer.getCalculationAsList("sin()");
        System.out.println(STR."Original: \{tokens16}, Corrected: \{Tokenizer.correctTokens(tokens16)} -> Expected: [sin, (, )] -> Result: \{Tokenizer.correctTokens(tokens16).equals(List.of("sin", "(", ")"))}\n");
    }

    public static void testSimplifyTokens() {
        System.out.println("Testing Simplify Tokens");

        try {
            testSimplifyCase(new String[]{"+", "+", "3"}, new String[]{"3"});
            //System.out.println("Discussion:");
            //testSimplifyCase(new String[]{"+", "-", "3"}, new String[]{"-", "3"});
            testSimplifyCase(new String[]{"+", "-", "3"}, new String[]{"-3"});
            //System.out.println("Discussion:\n");
            //testSimplifyCase(new String[]{"-", "+", "3"}, new String[]{"-", "3"});
            testSimplifyCase(new String[]{"-", "+", "3"}, new String[]{"-3"});
            //System.out.println();
            testSimplifyCase(new String[]{"-", "-", "3"}, new String[]{"3"});
            testSimplifyCase(new String[]{"+", "+", "+", "3"}, new String[]{"3"});
            //System.out.println("Discussion:");
            //testSimplifyCase(new String[]{"-", "-", "-", "3"}, new String[]{"-", "3"});
            testSimplifyCase(new String[]{"-", "-", "-", "3"}, new String[]{"-3"});
            //System.out.println();
            //testSimplifyCase(new String[]{"+", "-", "-", "-", "3"}, new String[]{"-", "3"});
            testSimplifyCase(new String[]{"+", "-", "-", "-", "3"}, new String[]{"-3"});
            testSimplifyCase(new String[]{"3", "+", "+", "-", "5"}, new String[]{"3", "-", "5"});
            testSimplifyCase(new String[]{"3", "-", "-", "-", "-", "5"}, new String[]{"3", "+", "5"});
            testSimplifyCase(new String[]{"3", "+", "-", "+", "-", "5"}, new String[]{"3", "+", "5"});
            testSimplifyCase(new String[]{"(","+","3","*","-","7",")","^","-","7"}, new String[]{"(","3","*","-7",")","^","-7"});
            testSimplifyCase(new String[]{"-","(","+","3","*","-","7",")","^","-","7"}, new String[]{"-(","3","*","-7",")","^","-7"});
            testSimplifyCase(new String[]{"-","(","-","+","3","*","-","7",")","^","-","7"}, new String[]{"-(","-3","*","-7",")","^","-7"});
            testSimplifyCase(new String[]{"-","(","-","3","*","-","7",")","^","-","7"}, new String[]{"-(","-3","*","-7",")","^","-7"});
            testSimplifyCase(new String[]{"-","sqrt","(","-","2",")"}, new String[]{"-sqrt","(","-2",")"});
            testSimplifyCase(new String[]{"-","[","-","2","]"}, new String[]{"-[","-2","]"});
            testSimplifyCase(new String[]{"-","{","-","2","}"}, new String[]{"-{","-2","}"});
            testSimplifyCase(Tokenizer.getCalculationAsStringArray(Tokenizer.getCalculationAsList("-sqrt(10)^-cos(10)--10*-6^7/3+(1-2)%7+tan(10)/ln10")),
                    new String[]{"-sqrt","(","10",")","^","-cos","(","10",")","+","10","*","-6","^","7","/","3","+","(","1","-","2",")","%","7","+","tan","(","10",")","/","ln","10"});

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private static void testSimplifyCase(String[] input, String[] expected) {
        List<String> tokens = Arrays.asList(input);
        List<String> expectedTokens = Arrays.asList(expected);
        List<String> result = simplyTokens(tokens);
        boolean isCorrect = result.equals(expectedTokens);
        System.out.println(STR."Original: \{tokens}, Simplified: \{result} -> Expected: \{expectedTokens} -> Result: \{isCorrect}");
    }

    public static void testTokenizerWithCorrection() {
        System.out.println("Testing Tokenizer with Correction");

        String[] expressions = {
                "-3 + 5",
                "3 + -5",
                "sin(-30)",
                "-sqrt(16)",
                "3 + -(5 + 2)",
                "-5 * (-3)",
                "5--3",
                "(-3) * 5",
                "sqrt(-9)",
                "-(3 + 5)"
        };

        System.out.println(Arrays.toString(expressions));
        for (int i = 0; i < expressions.length; i++) {
            System.out.println("Hi");
            List<String> tokens = Tokenizer.getCalculationAsList(expressions[i]);
            System.out.println(STR."Tokens: \{tokens}");
            List<String> correctedTokens = Tokenizer.correctTokens(tokens);
            System.out.println(STR."CorrectedTokens : \{correctedTokens}");
            System.out.println(STR."Expression: \{expressions[i]} Tokens: \{tokens.toString()} Corrected: \{correctedTokens.toString()}\n");
        }
    }
}
