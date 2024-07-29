package PagMath.Operations;

import PagMath.Calculation;

import java.util.List;

import static PagMath.Calculation.findClosingBracket;
import static PagMath.Calculation.processTokens;

public class ComplexOperations {

    public static void handleFunctions(List<String> tokens) {
        boolean functionFound = true;

        while (functionFound) {
            functionFound = false;

            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);

                if (isFunction(token)) {
                    functionFound = true;
                    int openingIndex = i + 1;
                    if (openingIndex < tokens.size() && tokens.get(openingIndex).equals("(")) {
                        int closingIndex = findClosingBracket(tokens, openingIndex);
                        List<String> subTokens = tokens.subList(openingIndex + 1, closingIndex);

                        List<String> evaluatedSubTokens = processTokens(subTokens);
                        double argument = Double.parseDouble(evaluatedSubTokens.get(0));
                        double result = applyFunction(token, argument);

                        replaceTokenWithResult(tokens, i, closingIndex, result);

                        i = i - (closingIndex - openingIndex);
                        functionFound = true;
                        break;
                    } else {
                        throw new IllegalArgumentException("Invalid syntax for function: " + token);
                    }
                }
            }
        }
    }

    public static boolean isFunction(String token) {
        // Check if the token starts with a function name followed by digits (for custom base logs)
        return token.matches("(sqrt|sin|cos|tan|log|ln)(\\d*)");
    }

    // Function calls that are not correct syntax but are often used!
    public static boolean isCommonUsedFunction(String token){
        return token.equals("sqrt2") || token.equals("log") || token.equals("ln");
    }

    private static void replaceTokenWithResult(List<String> tokens,int startingIndex,int closingIndex,double result){
        for(int j = startingIndex+1; j <= closingIndex +1 ; j++){
            tokens.remove(startingIndex);
        }
        tokens.add(startingIndex,String.valueOf(result));
    }

    public static void handleCommonUsedFunctions(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (isCommonUsedFunction(token)) {
                if (token.equals("sqrt2")) {
                    tokens.set(i, "sqrt(2)");
                } else if (token.startsWith("log") && token.length() > 3) {
                    // Extract the base from log (e.g., log10 -> log(10))
                    String base = token.substring(3);
                    tokens.set(i, "log" + base);
                } else if (token.equals("ln")) {
                    tokens.set(i, "ln");
                }
            }
        }
    }


    private static double applyFunction(String function, double argument) {
        String funcName = function.replaceAll("\\d", "");
        String baseStr = function.replaceAll("[^\\d]", "");

        Double base = null;
        if (!baseStr.isEmpty()) {
            base = Double.parseDouble(baseStr);
        }

        switch (funcName) {
            case "sqrt":
                return Math.sqrt(argument);
            case "sin":
                return Math.sin(Math.toRadians(argument));
            case "cos":
                return Math.cos(Math.toRadians(argument));
            case "tan":
                return Math.tan(Math.toRadians(argument));
            case "ln":
                return Math.log(argument); // Natural log (base e)
            case "log":
                if (base != null) {
                    return Math.log(argument) / Math.log(base); // Custom base log
                } else {
                    return Math.log10(argument); // Common log (base 10)
                }
            case "abs":
                return Math.abs(argument);
            default:
                throw new IllegalArgumentException("Unknown function: " + funcName);
        }
    }

}
