import java.sql.SQLOutput;
import java.util.Scanner;

public class calculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("welcome to calculator");
        double a= input.nextDouble();
        System.out.println("enter the number"+a);
        double b=input.nextDouble();
        System.out.println("enter the number "+b);

        System.out.println("Choose an operation (+, -, *, /):");
        char operator = input.next().charAt(0);
        double result = 0;

        if (operator == '+') {
            result = a + b;
        } else if (operator == '-') {
            result = a- b;
        } else if (operator == '*') {
            result = a * b;
        } else if (operator == '/') {  if (b != 0) {
            result = a / b;
        } else {
            System.out.println("Error: Cannot divide by zero.");
            return;
        }
        } else {
            System.out.println("Invalid operator.");
            return; // Exit if an invalid operator is entered
        }

        System.out.println("Result: " + result);

    }

    }

