public class test {

    public static int fib(int x)
    {
        if(x==0 || x==1)
        {
            return x;
        }
        return fib(x-1) + fib(x-2);
    }
    public static void main(String[] args) {
        System.out.println("chicken");
        int a=100;
        System.out.println(fib(a));
    }
}
