
import java.util.ArrayList;
import java.util.List;
/*
 * Author: Dr. Strout 
 * 
 * Prints out an arrangement of the given dice that adds up to the desired sum.
 */
public class Section02Main {
	public static void main(String[] args) {
		System.out.println(diceRollSum(3, 6,new ArrayList<Integer>()));
	}

	public static int sum(List<Integer> nums) {
		int sum=0;
		for (int n : nums) {
			sum += n;
		}
		return sum;
	}
	
	public static String diceRollSum(int N, int X, List<Integer> sofar) {
		if (sofar.size()==N){
			if (sum(sofar)==X) {   // evaluate this leaf
				return sofar.toString();
			} else { return null; }
		} 
		for (int i=1; i<=6; i++) {   // all possible local decisions 
			sofar.add(i);  // make local decision
			String answer = diceRollSum(N, X, sofar);
			if (answer!=null) {return answer; }        
			sofar.remove(sofar.size()-1);     // undo local decision
		}
		return null;
	}

}
