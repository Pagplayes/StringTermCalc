package PagMath.AST;

import java.util.ArrayList;
import java.util.List;

import static PagMath.Calculation.*;
import static PagMath.Operations.ComplexOperations.isFunction;

public class Tokenizer {
    public static List<String> getCalculationAsList(String expression) {
        System.out.println(STR."Expression: [\{expression}]");

        ArrayList<String> term = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        boolean lastWasOperator = true;
        int consecutiveOperators = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (isSpace(c)) continue;

            if (c == '=') break;

            if (c == '°') {
                term.add(STR."\{temp.toString().trim()}°");
                temp = new StringBuilder();
                continue;
            }

            if (isMathSymbole(c) || isOpeningBracket(String.valueOf(c)) || isClosingBracket(String.valueOf(c))) {
                consecutiveOperators++;
                if (consecutiveOperators > 1 && c == '-') {
                    temp.append(c);
                    lastWasOperator = false;
                } else {
                    if (!temp.isEmpty()) {
                        term.add(temp.toString().trim());
                        temp = new StringBuilder();
                    }
                    term.add(String.valueOf(c));
                    lastWasOperator = true;
                }
            } else {
                if (lastWasOperator) {
                    consecutiveOperators = 0;
                }
                temp.append(c);
                lastWasOperator = false;
            }
        }
        if (!temp.isEmpty()) {
            term.add(temp.toString().trim());
        }
        return term;
    }

    public static List<String> correctTokens(List<String> tokens) {
        List<String> corrected = new ArrayList<>();
        int i = 0;

        while (i < tokens.size()) {
            String token = tokens.get(i);

            if (token.equals("-")) {
                if (i == 0 || isOpeningBracket(tokens.get(i - 1)) || isOperator(tokens.get(i - 1))) {
                    // Handle unary minus before numbers
                    if (i + 1 < tokens.size() && isNumber(tokens.get(i + 1))) {
                        corrected.add(STR."-\{tokens.get(i + 1)}");
                        i++; // Skip the next token as it's already processed
                    } else if (i + 1 < tokens.size() && (isOpeningBracket(tokens.get(i + 1)) || isFunction(tokens.get(i + 1)))) {
                        // Handle unary minus before functions or brackets
                        corrected.add(STR."-\{tokens.get(i + 1)}");
                        i++;
                    } else {
                        corrected.add(token);
                    }
                } else {
                    corrected.add(token);
                }
            } else {
                corrected.add(token);
            }
            i++;
        }
        return simplyTokens(corrected);
    }

    public static List<String> simplyTokens(List<String> tokens) {
        List<String> simplified = new ArrayList<>();
        int i = 0;

        while (i < tokens.size()) {
            String token = tokens.get(i);

            if (isSign(token)) {
                StringBuilder combinedSign = new StringBuilder();
                boolean isNegative = false;

                // Combine consecutive signs
                while (i < tokens.size() && isSign(tokens.get(i))) {
                    if (tokens.get(i).equals("-")) {
                        isNegative = !isNegative;
                    }
                    i++;
                }

                // Determine the final sign
                if (isNegative) {
                    combinedSign.append("-");
                } else if (!combinedSign.toString().isEmpty() || !simplified.isEmpty()) {
                    combinedSign.append("+");
                }

                // Add the combined sign if necessary
                if (!combinedSign.toString().isEmpty()) {
                    simplified.add(combinedSign.toString());
                }
            } else {
                // Handle regular tokens
                if (isNumber(token) && !simplified.isEmpty() && isSign(simplified.get(simplified.size() - 1))) {
                    // Check if the previous token is an operator or start of expression
                    if (simplified.size() >= 2 && (isNumber(simplified.get(simplified.size() - 2)) || isClosingBracket(simplified.get(simplified.size() - 2)))) {
                        // Do not merge signs into the number if the previous token was a number or closing bracket
                        simplified.add(token);
                    } else {
                        // Combine sign with number
                        simplified.set(simplified.size() - 1, simplified.get(simplified.size() - 1) + token);
                    }
                } else if (isOpeningBracket(token) && !simplified.isEmpty() && simplified.get(simplified.size() - 1).equals("-")) {
                    // Handle cases like -(
                    simplified.set(simplified.size() - 1, "-" + token);
                } else {
                    simplified.add(token);
                }
                i++;
            }
        }

        // Remove leading "+" if it's not needed
        if (!simplified.isEmpty() && simplified.get(0).equals("+")) {
            simplified.remove(0);
        }

        return simplified;
    }

    private static boolean isSign(String token) {
        return token.equals("+") || token.equals("-");
    }

    private static String combineSigns(List<String> tokens, int startIndex) {
        System.out.println(STR."Tokens: \{tokens}");
        System.out.println(STR."Start Index: \{startIndex}");

        int signCount = 0;
        boolean isNegative = false;

        int i = startIndex;
        while (i < tokens.size() && isSign(tokens.get(i))) {
            if (tokens.get(i).equals("-")) {
                isNegative = !isNegative; // Toggle negative flag
            }
            signCount++;
            i++;
        }

        String result = isNegative ? "-" : (signCount > 0 ? "+" : "");
        System.out.println(STR."Combined Result: \{result}, Processed Signs Count: \{signCount}");
        return result;
    }

    private static void handleBracketOrFunction(List<String> simplified, String token) {
        if (!simplified.isEmpty() && simplified.get(simplified.size() - 1).equals("-")) {
            simplified.set(simplified.size() - 1, "-" + token);
        } else if (!simplified.isEmpty() && simplified.get(simplified.size() - 1).equals("+")) {
            simplified.remove(simplified.size() - 1);
            simplified.add(token);
        } else {
            simplified.add(token);
        }
    }

    private static void handleRegularToken(List<String> simplified, String token) {
        if (!simplified.isEmpty() && simplified.get(simplified.size() - 1).equals("-") && isNumber(token)) {
            simplified.set(simplified.size() - 1, STR."-\{token}");
        } else {
            simplified.add(token);
        }
    }

    private static void removeLeadingPlus(List<String> simplified, boolean foundNonSignToken) {
        if (!simplified.isEmpty() && simplified.getFirst().equals("+") && !foundNonSignToken) {
            simplified.removeFirst();
        }
    }

    private static boolean isOpeningBracket(String token) {
        return token.matches("[\\{\\[\\(]");
    }

    private static boolean isFunction(String token) {
        // Assuming functions like sin, cos, etc. are identified as valid function names
        // This function can be expanded based on valid function names in the system
        return token.matches("sin|cos|tan|sqrt|log|ln");
    }

    private static boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String token) {
        return "+-*/%^".contains(token);
    }

    public static List<String> oldSimplyTokens(List<String> tokens) {
        List<String> simplified = new ArrayList<>();
        boolean foundNonSignToken = false; // Flag to track if we've seen any non-sign tokens

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (isSign(token)) {
                int signCount = 0;
                boolean isNegative = false;

                // Count consecutive + and - signs
                while (i < tokens.size() && isSign(tokens.get(i))) {
                    if (tokens.get(i).equals("-")) {
                        isNegative = !isNegative; // Toggle negative flag
                    }
                    signCount++;
                    i++;
                }
                i--; // Decrement i because the loop increments it one extra time

                // Determine the final sign
                if (isNegative) {
                    simplified.add("-");
                } else if (signCount > 0 && foundNonSignToken) {
                    simplified.add("+");
                }
            } else {
                if(!simplified.isEmpty() && simplified.get(simplified.size()-1).equals("-") && isNumber(token)) {
                    if(simplified.size() >= 2 && isNumber(simplified.get(simplified.size() - 2))) {
                        simplified.add(token);
                    } else {
                        simplified.set(simplified.size()-1, STR."-\{token}");
                    }
                } else if (token.matches("[{\\[(]") && !simplified.isEmpty() && simplified.getLast().equals("-")) {
                    simplified.set(simplified.size() - 1, "-(");

                } else {
                    foundNonSignToken = true; // Mark that we've seen a non-sign token
                    simplified.add(token);
                }

            }
        }

        // If the first token is a lone "+", remove it (if it's not part of a valid expression)
        if (!simplified.isEmpty() && simplified.getFirst().equals("+") && !foundNonSignToken) {
            simplified.removeFirst();
        }

        return simplified;
    }

    public static String[] getCalculationAsStringArray(List<String> tokens) {
        String[] simplified = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            simplified[i] = tokens.get(i);
        }
        return simplified;
    }
}
