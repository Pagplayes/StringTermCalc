package PagMath.Operations;

import PagMath.Calculation;

import java.util.List;

import static PagMath.Calculation.findClosingBracket;
import static PagMath.Calculation.processTokens;

public class ComplexOperations {

    public static void handleFunctions(List<String> tokens) {
      for (int i = 0; i < tokens.size(); i++) {
          String token = tokens.get(i);

          if(isFunction(token)){
              int openingIndex = i+1;
              if(openingIndex < tokens.size() && tokens.get(openingIndex).equals("(")){
                  int closingIndex = findClosingBracket(tokens, openingIndex);
                  List<String> subTokens = tokens.subList(openingIndex +1, closingIndex);
                  System.out.println("Sub Tokens: " + subTokens)
                  ;
                  double argument = evaluateArgument(subTokens);
                  double result = applyFunction(token,argument);
                  replaceTokenwithResult(tokens,i,closingIndex,result);
                  i = closingIndex;
              } else {
                  throw new IllegalArgumentException("Invalid syntax for function: " + token);
              }
          }
      }
    }

    public static boolean isFunction(String token){
        return token.equals("sqrt") || token.equals("sin") || token.equals("cos") || token.equals("tan") || token.equals("log") ;
    }

    // Function calls that are not correct syntax but are often used!
    public static boolean isCommonUsedFunction(String token){
        return token.equals("sqrt2") || token.equals("log10");
    }

    private static double evaluateArgument(List<String> argumentTokens) {
        return Double.parseDouble(processTokens(argumentTokens).get(0));
    }

    private static void replaceTokenwithResult(List<String> tokens,int startingIndex,int closingIndex,double result){
        for(int j = startingIndex+1; j < closingIndex; j++){
            tokens.remove(startingIndex);
        }
        tokens.add(startingIndex,String.valueOf(result));
    }

    private static double applyFunction(String function, double argument) {
        switch (function) {
            case "sqrt":
                return Math.sqrt(argument);
            case "sin":
                return Math.sin(Math.toRadians(argument)); // Argument in Grad, falls gewünscht
            case "cos":
                return Math.cos(Math.toRadians(argument)); // Argument in Grad, falls gewünscht
            case "tan":
                return Math.tan(Math.toRadians(argument)); // Argument in Grad, falls gewünscht
            case "log":
                return Math.log(argument); // Natürlicher Logarithmus
            default:
                throw new IllegalArgumentException("Unknown function: " + function);
        }
    }
}
