package PagMath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static PagMath.Calculation.calc;

public class Test {
    public static void main(String[] args) {
        test();
    }

    public static void testCalculation(String calculation) {
        try {
            calc(calculation);
        } catch (Exception e) {
            System.out.println("Error processing calculation: " + calculation);
            e.printStackTrace();
        }
    }

    public static void test() {
        String[] test1 = {
                "2^2","-3*2","-2/2","-2--2","-2+-2","2+2","155%5",
                "-2^2","2(4*3)+6*(6+7)","sqrt(4)","2*sqrt(4)","sqrt(4)^sqrt(4)","2^(3^2)","-(2^3)","((2+3)*2)^2"
                ,"2^(-3)","2^-3","2+3^2*2","sqrt(16) + sqrt(9)","sqrt(16) * sqrt(9)","log(100) + 1","cos(0) + sin(90)",
                "2^(sqrt(4))","1 + 2 * 3 - 4 / 2","-(sqrt(4))^2","5 % 2 + 3","sin(30)^2 + cos(30)^2"
        };
        String[] test2 = {
                // Grundlegende Operationen
                "2^2",  //correct   4
                "-3*2", //correct   -6
                "-2/2", //correct   -1
                "-2--2",//correct   0
                "-2+-2",//correct   -4
                "2+2",  //correct   4
                "155%5",//correct   0
                "-2^2", //correct   4
                "2(4*3)+6*(6+7)",   //correct   102

                // Komplexe Operationen mit Wurzeln
                "sqrt(4)",  //correct   2
                "2*sqrt(4)",//correct   4
                "sqrt(4)^sqrt(4)",  //correct   4
                "sqrt(16) + sqrt(9)",   //correct   7
                "sqrt(16) * sqrt(9)",   //correct   12

                // Potenzen und Klammern
                "2^(3^2)",  //correct   512
                "(2+3)*2",  //correct   10
                "((2+3)*2)^2",  //correct   100
                "2^(-3)",   //correct   0.125
                "2^-3", //correct   0.125
                "2+3^2*2",  //correct   20

                // Logarithmen und trigonometrische Funktionen
                "log(100) + 1", //TODO: 5.6
                "log10(100) + 1", //TODO:   log10                  Annahme, dass log10 unterstützt wird
                "ln(100) + 1",    //TODO:   ln                     Annahme, dass ln als natürlicher Logarithmus unterstützt wird
                "cos(0) + sin(90)", //correct 2
                "sin(30)^2 + cos(30)^2", //correct 1

                // Verschachtelte Wurzeln und trigonometrische Funktionen
                "sqrt(sqrt(16))", //TODO: "sqrt"
                "sin(cos(45))", //TODO: "cos"

                // Testfälle mit fehlenden oder inkorrekten Syntax
                "sqrt(2",        //correct crash     Fehlende Klammer
                "2sqrt(4)",      //TODO: 2sqrt       Fehlende Operation zwischen Zahl und Funktion
                "sqrt2)",        //TODO: sqrt2       Fehlende Klammer am Anfang
                "sqrt(16) +",    //=> Index 2 out of bounds for length 2  Fehlendes Operand     Error by Addition maybe ignorable with only return a or b
                "2^*3",          //correct crash => Index 0 out of bounds for length 0  Ungültige Syntax

                // Weitere mathematische Ausdrücke
                "abs(-5) + abs(5)", //TODO: abs implement Annahme, dass abs unterstützt wird
                "-(sqrt(4))^2", // 4
                "5 % 2 + 3" //correct 4
        };

        String[] test3 = {
                // Grundlegende Operationen
                "2^2", "-3*2", "-2/2", "-2--2", "-2+-2", "2+2", "155%5", "-2^2", "2(4*3)+6*(6+7)",

                // Komplexe Operationen mit Wurzeln
                "sqrt(4)", "2*sqrt(4)", "sqrt(4)^sqrt(4)", "sqrt(16) + sqrt(9)", "sqrt(16) * sqrt(9)",

                // Potenzen und Klammern
                "2^(3^2)", "(2+3)*2", "((2+3)*2)^2", "2^(-3)", "2^-3", "2+3^2*2",

                // Logarithmen und trigonometrische Funktionen
                "log(100) + 1", "log10(100) + 1", "ln(100) + 1", "cos(0) + sin(90)", "sin(30)^2 + cos(30)^2",

                // Verschachtelte Wurzeln und trigonometrische Funktionen
                "sqrt(sqrt(16))", "sin(cos(45))",

                // Testfälle mit fehlenden oder inkorrekten Syntax
                "sqrt(2", "2sqrt(4)", "sqrt2)", "sqrt(16) +", "2^*3",

                // Weitere mathematische Ausdrücke
                "abs(-5) + abs(5)", "-(sqrt(4))^2", "5 % 2 + 3",
                // Weitere zusätzliche Tests für rekursive Funktionsaufrufe und verschiedene Operatoren
                "log(sqrt(100))", "cos(sin(0)) + 1", "2^3^2", "sqrt(2^2 + 2^2)", "log(abs(-10))"
        };

        iterate(test3);
    }

    public static void testPowerSimple(){
        String[] testCases = {
                "2^3", "-2^3", "2^-3", "-2^-3","(2+3)^2","2^(2+3)","(2+3)^(2+3)"
                ,"-(2+3)^2"
        };

        iterate(testCases);
    }

    public static void testPowerTo(){
        String[] testCases = {
                "2^3", "-2^3", "2^-3", "-2^-3",
                "0^3", "0^-3", "2^0", "-2^0", "0^0",
                "(2+3)^2", "(2*3)^2", "2+3^2", "2^(3+2)", "2^(3-2)", "2^(2*3)",
                "((2+3))^2", "((2+3)*2)^2", "2^((2+3)*2)",
                "2^3*2", "2*2^3", "2*(2^3)", "(2^3)*2",
                "(-2)^3", "(-2)^-3", "2^3^2", "(2^3)^2",
                "0^-3", "8^(1/3)", "(-8)^(1/3)", "2^^3", "2^*3", "^2^3",
                "2^^3", "2^+3", "2^3+", "999^999", "2^1024", "2^-1000"
        };

        iterate(testCases);
    }

    public static void openFile() {
        String filePath = "C:\\Users\\pagpl\\Documents\\Projects\\JustForFun\\ressoucrces\\math_test_strings.txt"; // Pfad zur Textdatei
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                testCalculation(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void iterate(List<String > testCases){
        for (String test : testCases) {
            try {
                System.out.println("Term: " + test +" -> ");
                calc(test);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Error processing calculation: " + test + " => " + e.getMessage());
            }
        }
    }

    public static void  sqrtTest(){
        String[] strings1 = {
                "2^sqrt(2+2)","sqrt(2)","sqrt(100)","1-sqrt(2)","-sqrt(2)",
                "2+2*sqrt(2)^2"
        };
        String[] strings2= {
                "-sqrt(2)^sqrt(2)","2+2sqrt(2)^2","sqrt2","sqrt(2","sqrt2)",
                "sqrt(sqrt(sqrt(2)))"
        };
        iterate(strings1);
    }

    public static void iterate(String[] testCases){
        for (String test : testCases) {
            try {
                System.out.println("Term: " + test +" -> ");
                calc(test);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Error processing calculation: " + test + " => " + e.getMessage());
            }
        }
    }
}
