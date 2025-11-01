import java.io.*;
class Geeks {
    public static void main(String[] args) {

        // Byte array for "PREM NAMAN"
        byte[] byteArray = {80, 82, 69, 77, 32, 78, 65, 77, 65, 78};
        String str1 = new String(byteArray);
        System.out.println("String from byte array: " + str1);

        // Byte array for "NAMANPREM492@GMAIL.COM"
        byte[] byteArra = {78, 65, 77, 65, 78, 80, 82, 69, 77, 52, 57, 50, 64, 71, 77, 65, 73, 76, 46, 67, 79, 77};
        String str2 = new String(byteArra);
        System.out.println("String from byte array: " + str2);
    }
}