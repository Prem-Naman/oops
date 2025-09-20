class ThisDemo {
    int a,b;
    public void get(int x,int y){
        this.a = a;
        this.b = b;
    }
    public void put(){
        System.out.println("a = "+a);
        System.out.println("b = "+b);
    }
}
class Demo{
    public static void main(String[] args) {
        ThisDemo obj=new ThisDemo();
        obj.get(10,20);
        obj.put();
    }
}