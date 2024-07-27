package PagMath.Operations;


import PagMath.Calculation;

import java.util.ArrayList;
import java.util.List;

import static PagMath.Calculation.*;

public class PowerTo {
    public static List<String> handlePowerOperations(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("^")) {
                List<String> base = findBase(tokens, i - 1);
                List<String> exponent = findExponent(tokens, i+1);

                if(base.isEmpty()) System.out.println("Base is Empty");
                if(exponent.isEmpty()) System.out.println("Exponent is Empty");

                // Now, calculate the power and replace in the tokens list
                double baseValue = Double.parseDouble(base.get(0));
                double exponentValue = Double.parseDouble(exponent.get(0));
                double result = Math.pow(baseValue, exponentValue);

                // Replace in the tokens list
                int baseStartIndex = i - base.size();
                int exponentEndIndex = i + exponent.size();

                for (int j = baseStartIndex; j <= exponentEndIndex; j++) {
                    tokens.remove(baseStartIndex); // continuously remove at baseStartIndex since indices shift
                }
                tokens.add(baseStartIndex, String.valueOf(result));
                i = baseStartIndex; // reset index to start after newly inserted result
            }
        }
        return tokens;
    }


    private static List<String> findBase(List<String> tokens, int powerIndex) {
        List<String> base = new ArrayList<>();
        int i = powerIndex;

        if (i < 0) {
            return base;
        }

        if(i >= tokens.size()) {
            return base;
        }

        String token = tokens.get(i);

        if (isNumber(token) || (token.equals("-") && i - 1 >= 0 && isNumber(tokens.get(i - 1)))) {
            StringBuilder temp = new StringBuilder();
            while (i >= 0 && (isNumber(tokens.get(i)) || tokens.get(i).equals("-"))) {
                temp.insert(0, tokens.get(i));
                i--;
            }
            base.add(0, temp.toString());
        } else if (isClosingBracket(token)) {
            int openingIndex = Calculation.findOpeningBracket(tokens, i);
            base.addAll(tokens.subList(openingIndex, i + 1));
        }
        return base;
    }




    private static List<String> findExponent(List<String> tokens, int powerIndex) {
        List<String> exponent = new ArrayList<>();
        int i = powerIndex;

        if (i >= tokens.size()) {
            return exponent;
        }

        if (isOpeningBracket(tokens.get(i))) {
            int closingIndex = findClosingBracket(tokens, i);
            exponent.addAll(tokens.subList(i, closingIndex + 1));
        } else if (isNumber(tokens.get(i)) || (tokens.get(i).equals("-") && i + 1 < tokens.size() && isNumber(tokens.get(i + 1)))) {
            StringBuilder temp = new StringBuilder();
            while (i < tokens.size() && (isNumber(tokens.get(i)) || tokens.get(i).equals("-"))) {
                temp.append(tokens.get(i));
                i++;
            }
            exponent.add(temp.toString());
        }

        return exponent;
    }

}
