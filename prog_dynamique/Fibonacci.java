package prog_dynamique;

public class Fibonacci {
	public int pdf(int n) {
		if(n==0 || n==1)
			return 1;
		else {
			int a = 1;
			int b = 1;
			int c = 0;
			for(int i=2; i <= n; i++) {
				c = a+b;
				a = b;
				b = c;
			}
			return c;	
		}
	}
	
	public static void main(String[] args) {
		Fibonacci F = new Fibonacci();
		System.out.println(F.pdf(6));
	}
}
