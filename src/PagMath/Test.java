package PagMath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static PagMath.Calculation.calc;

public class Test {
    public static void main(String[] args) {
        sqrtTest();
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
        String[] testCases = {
                "2^2","-3*2","-2/2","-2--2","-2+-2","2+2","155%5",
                "-2^2","2(4*3)+6*(6+7)"
        };

        iterate(testCases);
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
        String[] strings = {
                "sqrt(2+2)"
        };
        String[] testString= {
                "sqrt(2)","sqrt(100)","1-sqrt(2)","-sqrt(2)","2+2sqrt(2)^2",
                "2+2*sqrt(2)^2","-sqrt(2)^sqrt(2)","sqrt2","sqrt(2","sqrt2)",
                "sqrt(sqrt(sqrt(2)))"
        };
        iterate(testString);
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
