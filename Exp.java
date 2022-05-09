package hw4;
import java.util.Random;
public class Exp {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < Integer.valueOf(args[1]); i++) {
			System.out.println(getExp(Double.valueOf(args[0])));
		}
	}
	
	static double getExp(double lambda) {
		Random rand = new Random();
		return Math.log(1-rand.nextDouble()) / ((-1) * (lambda));
	}
	

}
