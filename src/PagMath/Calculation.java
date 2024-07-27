package PagMath;

import PagMath.Operations.ComplexOperations;
import PagMath.Operations.PowerTo;

import java.util.ArrayList;
import java.util.List;

import static PagMath.Operations.AdditiveOperations.handleAdditiveOperations;
import static PagMath.Operations.PowerTo.handlePowerOperations;

public class Calculation {
    public static void main(String[] args) {
        String test1 = "3 + 5 * (2 ^ 3 - 4 / 2) - 7 % 3 + (6 - (2 ^ 2))";
        String test2 = "6*(4*(3*4-5/5+1-7)) =";
        String test3 = "3(3+3) =";
        String test4 = "3*(3+3)";
        String test5 = "2^3";
        String test6 = "(2*2)^3";
        String test7 = "2^(2*3)";
        calc(test7);
    }

    public static List<String> generalCalc(List<String> input) {
        List<String> result = new ArrayList<>(input);

        //ConvertsFirstNumber to one
        handelStartingMinus(result);
        System.out.println("Starting Minus:" + result);

        // First, handle power (^) operations
        handlePowerOperations(result);
        System.out.println("Power:" + result);

        // Then, handle *, /, and % operations
        handleMultiplicativeOperations(result);
        System.out.println("Multiplicative:" + result);

        // Finally, handle + and - operations
        handleAdditiveOperations(result);
        System.out.println("Additive:" + result);

        return result;
    }


    public static void handleMultiplicativeOperations(List<String> result) {
        for (int i = 0; i < result.size(); i++) {
            String token = result.get(i);
            if (token.equals("*") || token.equals("/") || token.equals("%")) {
                double left = Double.parseDouble(result.get(i - 1));
                double right = Double.parseDouble(result.get(i + 1));
                double calcResult = 0;

                switch (token) {
                    case "*":
                        calcResult = left * right;
                        break;
                    case "/":
                        if (right == 0) throw new DivisionByZeroException("Division with zero is not defined.");
                        calcResult = left / right;
                        break;
                    case "%":
                        calcResult = left % right;
                        break;
                }

                result.set(i - 1, String.valueOf(calcResult));
                result.remove(i); // Remove operator
                result.remove(i); // Remove operand
                i--; // Adjust index
            }
        }
    }

    private static void handleMissingOperationSymbols(List<String> result) {
        boolean lastCharWasNumber = false;
        for (int i = 0; i < result.size(); i++) {
            String token = result.get(i);
            if (isNumber(token)) {
                lastCharWasNumber = true;
            } else if (lastCharWasNumber && isOpeningBracket(token)) {
                result.add(i, "*");
                lastCharWasNumber = false;
            } else {
                lastCharWasNumber = false;
            }
        }
    }


    public static void handelStartingMinus(List<String> result) {
        if (result.get(0).equals("-")) {
            StringBuilder temp = new StringBuilder(result.get(0));
            int i = 1;

            while (i < result.size() && isNumber(result.get(i))) {
                temp.append(result.get(i));
                i++;
            }

            System.out.println("Temp: " + temp.toString());

            for (int j = 0; j < i; j++) {
                result.remove(0);
            }

            result.add(0, temp.toString());
        }
    }


    public static ArrayList<String> getCalculationAsList(String calculation) {
        ArrayList<String> term = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        boolean lasWasOperator = true;
        int consecutiveOperators = 0;

        for (int i = 0; i < calculation.length(); i++) {
            char c = calculation.charAt(i);

            if(isSpace(c)) continue;

            if (c == '=') break;

            if(isMathSymbole(c) || isOpeningBracket(String.valueOf(c)) || isClosingBracket(String.valueOf(c))) {
                consecutiveOperators++;
                if (consecutiveOperators > 1 && c == '-'){
                    temp.append(c);
                    lasWasOperator = false;
                } else {
                    if (temp.length() > 0) {
                        term.add(temp.toString().trim());
                        temp = new StringBuilder();
                    }
                    term.add(String.valueOf(c));
                    lasWasOperator = true;
                }
            } else {
                if (lasWasOperator) {
                    consecutiveOperators = 0;
                }
                temp.append(c);
                lasWasOperator = false;
            }
        }
        if (temp.length() > 0) {
            term.add(temp.toString().trim());
        }
        return term;
    }

    public static boolean isMathSymbole(char input) {
        return input == '+' || input == '-' || input == '*' || input == '/' || input == '%' || input == '^';
    }

    public static boolean isSpace(char input) {
        return input == ' ';
    }

    public static boolean hasPriority(char input) {
        return input == '*' || input == '/' || input == '%';
    }

    public static List<String> processTokens(List<String> tokens) {
        List<String> result = new ArrayList<>();
        int i = 0;

        while (i < tokens.size()) {
            String token = tokens.get(i);
            if (isOpeningBracket(token)) {
                int closingIndex = findClosingBracket(tokens, i);
                List<String> subExpression = createSublist(tokens, i + 1, closingIndex);
                List<String> subResult = processTokens(subExpression);
                result.add(subResult.get(0)); // Add the result of the sublist
                i = closingIndex; // Continue after the closing bracket
            } else {
                result.add(token);
            }
            i++;
        }

        return generalCalc(result);
    }

    public static List<String> createSublist(List<String> tokens, int start, int end) {
        List<String> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(tokens.get(i));
        }
        return result;
    }

    public static int findClosingBracket(List<String> tokens, int openingIndex) {
        int bracketCount = 0;
        String openingBracket = tokens.get(openingIndex);
        String closingBracket = getMatchingClosingBracket(openingBracket);

        for (int i = openingIndex; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals(openingBracket)) {
                bracketCount++;
            } else if (token.equals(closingBracket)) {
                bracketCount--;
                if (bracketCount == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("No matching closing bracket found for: " + openingBracket);
    }

    public static String getMatchingClosingBracket(String openingBracket) {
        switch (openingBracket) {
            case "(":
                return ")";
            case "[":
                return "]";
            case "{":
                return "}";
            default:
                throw new IllegalArgumentException("Unknown opening bracket: " + openingBracket);
        }
    }

    public static boolean isOpeningBracket(String token) {
        return token.equals("(") || token.equals("[") || token.equals("{");
    }

    public static boolean isClosingBracket(String token) {
        return token.equals(")") || token.equals("]") || token.equals("}");
    }

    public static void calc(String expression) {
        List<String> tokens = getCalculationAsList(expression);
        handleMissingOperationSymbols(tokens);

        //ConvertComplexOperations
        List<String> functionName = tokens.stream().filter(ComplexOperations::isFunction).toList();
        if (!functionName.isEmpty()) {
            ComplexOperations.handleCommonUsedFunctions(tokens);
            ComplexOperations.handleFunctions(tokens);
            System.out.println("Complex Operation "+ functionName.get(0) + ": " + tokens);
        }

        List<String> result = processTokens(tokens);
        System.out.println("Result: " +expression + " "+ result.get(0));
    }

    public static boolean isNumber(String token){
        if(token == null || token.isEmpty()){
            return false;
        }
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    public static int findOpeningBracket(List<String> tokens, int closingIndex) {
        int bracketCount = 0;
        for (int i = closingIndex; i >= 0; i--) {
            String token = tokens.get(i);
            if (isClosingBracket(token)) {
                bracketCount++;
            } else if (isOpeningBracket(token)) {
                bracketCount--;
                if (bracketCount == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
}
