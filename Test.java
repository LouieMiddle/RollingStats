public class Test {
	//A testing class
	public static void main(String[] args) {
		/*
		 * Play around with these variables to test the RollingStats utility class.
		 * JavaDoc information should pop up for each method.
		 * 
		 */
		RollingStats rollStats = new RollingStats(5);
		rollStats.addValue(5.0);
		rollStats.addValue(3.0);
		rollStats.addValue(6.0);
		rollStats.addValue(12.0);
		rollStats.addValue(4.25);
		
		//The mean
		try {
			System.out.println("The mean: " + rollStats.getMean());
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		}
		
		//The mean with specified range
		try {
			System.out.println("The mean between the 1st and 3rd values: " + rollStats.getMean(1,3));
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		} catch (InvalidSpecifiedRangeException ex) {
			System.err.print(ex);
		}
		
		//The median
		try {
			System.out.println("The median: " + rollStats.getMedian());
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		} 
		
		//The median with specified range
		try {
			System.out.println("The median between the 1st and 3rd values: " + rollStats.getMedian(1,3));
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		} catch (InvalidSpecifiedRangeException ex) {
			System.err.print(ex);
		}
		
		//The sum
		try {
			System.out.println("The sum: " + rollStats.getSum());
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		}
		
		//The sum with specified range
		try {
			System.out.println("The sum between the 1st and 3rd values: " + rollStats.getSum(1,3));
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		} catch (InvalidSpecifiedRangeException ex) {
			System.err.print(ex);
		}
		
		//The standard deviation
		try {
			System.out.println("The standard deviation: " + rollStats.getSD());
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		}
		
		//The standard deviation with specified range
		try {
			System.out.println("The standard deviation between the 1st and 3rd values: " + rollStats.getSD(1,3));
		} catch (DataEmptyException ex) {
			System.err.print(ex);
		} catch (InvalidSpecifiedRangeException ex) {
			System.err.print(ex);
		}
	}
}