import java.util.Scanner;

public class NumberPattern {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);

        int a = input.nextInt();
        System.out.println("enter your number a = " + a);
        int b = input.nextInt();
        System.out.println("enter your number b =" + b);
        int c = input.nextInt();
        System.out.println("enter your number c =" + c);

        if (a != b && b != c && a != c) {
            if (a < b && b < c) {
                System.out.println("Strictly Increasing");
            } else if (a > b && b > c) {
                System.out.println("Strictly Decreasing");
            } else if (b > a && b > c) {
                System.out.println("Middle number is largest");
            } else {
                System.out.println("No specific pattern");
            }
        } else {
            System.out.println("No specific pattern");
        }
    }
}

