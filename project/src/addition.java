import java.util.Scanner;

public class addition {
    public static void main(String[] args) {
        System.out.println("addition of two number");
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b=input.nextInt();
        int sum = a+b;
        System.out.println("sum of the two number"+sum);
    }
}
