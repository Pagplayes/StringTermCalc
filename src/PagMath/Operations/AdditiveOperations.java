package PagMath.Operations;

import java.util.List;

public class AdditiveOperations {
    public static void handleAdditiveOperations(List<String> result) {
        for (int i = 0; i < result.size(); i++) {
            String token = result.get(i);
            if (token.equals("+") || token.equals("-")) {
                double left = Double.parseDouble(result.get(i - 1));
                double right = Double.parseDouble(result.get(i + 1));
                double calcResult = 0;

                switch (token) {
                    case "+":
                        calcResult = left + right;
                        break;
                    case "-":
                        calcResult = left - right;
                        break;
                }

                result.set(i - 1, String.valueOf(calcResult));
                result.remove(i); // Remove operator
                result.remove(i); // Remove operand
                i--; // Adjust index
            }
        }
    }
}
