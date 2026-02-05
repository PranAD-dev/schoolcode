public class test {
    public static void main(String[] args) 
    {
        int m = 100;
        int n = 99;
        int i = 0;
        while (m!=n)
        {
            if (m>n)
            {
                m = m - n;
            }
            else 
            {
                n = n - m;
            }
            i++;
            System.out.println(i);
        }
    }
}
