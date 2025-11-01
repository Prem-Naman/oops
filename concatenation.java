class Example {
    public static void main(String[] args) {
        String s1 = "Hello";
        String s2 = "World";

        // Using + operator for concatenation
        String s3 = s1 + " " + s2;

        // Using concat() method for concatenation
        String s4 = s1.concat(" " + s2);

        System.out.println(s3); // Hello World
        System.out.println(s4); // Hello World
    }
}