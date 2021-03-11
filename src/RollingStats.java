import java.util.LinkedList;
import java.util.Iterator;
import java.util.Queue;
import java.lang.Math;

public class RollingStats {
	//A queue to store the data
	private Queue<Double> data = new LinkedList<>();
	//The maximum size of the queue
	private int maxSize;
	
	/**
	 * Create a rolling statistics object.
	 * @param maxItems the maximum amount of values that can be stored in the object (should be bigger than 0)
	 */
	public RollingStats(int maxItems) throws IllegalArgumentException {
		if (maxItems >= 1) {
			maxSize = maxItems;
		}
		else {
			throw new IllegalArgumentException("Max size has to be bigger than 0.");
		}
	}
	
	/**
	 * Add a value to the rolling statistic.
	 * If the object has the max amount of items, then it'll remove the oldest data.
	 * @param value the value to be added
	 */
	public void addValue(Double value) {
		data.add(value);
		//If the data queue is full, remove the oldest data
		if (data.size() == maxSize+1) {
			data.remove();
		}
	}
	
	/** 
	 * @return the mean of the values added to the object
	 * @throws DataEmptyException
	 */
	public Double getMean() throws DataEmptyException {
		return getSum()/Double.valueOf(data.size());
	}
	
	/**
	 * @param start where to start calculating the mean (should be greater than -1)
	 * @param end where to stop calculating the mean (should be less than the max size)
	 * @return the mean of the values added to the object in the specified range
	 * @throws DataEmptyException
	 * @throws InvalidSpecifiedRangeException
	 */
	public Double getMean(int start, int end) throws DataEmptyException, InvalidSpecifiedRangeException {
		if ( (((end-start)+1) <= maxSize) && (start>=0) && (end<maxSize) ) {
			return (getSum(start, end)) / (Double.valueOf(end-start)+1.0);
		}
		else {
			throw new InvalidSpecifiedRangeException(
					"The range specified is invalid. \n");
		}
	}
	
	/**
	 * @return the median of the values added to the object
	 * @throws DataEmptyException
	 */
	public Double getMedian() throws DataEmptyException {		
		if (data.size() != 0) {
			//Sort the list to be able to get the median
			LinkedList<Double> sortedData = createSortedList(0, data.size()-1);
			//Figure out the middle index
			Double middle = (data.size()-1)/2.0;
			return med(middle, sortedData);
		}
		else {
			throw new DataEmptyException(
					"There is no data stored. \n");
		}
	}
	
	/**
	 * @param start where to start calculating the median (should be greater than -1)
	 * @param end where to stop calculating the median (should be less than the max size)
	 * @return the median of the values added to the object in the specified range
	 * @throws DataEmptyException
	 * @throws InvalidSpecifiedRangeException
	 */
	public Double getMedian(int start, int end) throws DataEmptyException, InvalidSpecifiedRangeException {
		if ( (((end-start)+1) <= maxSize) && (start>=0) && (end<maxSize) )  {
			if (data.size() != 0) {
				//Sort the list to be able to get the median
				LinkedList<Double> sortedData = createSortedList(start, end);
				//Figure out the middle index
				Double middle = (end-start)/2.0;
				return med(middle, sortedData);
			}
			else {
				throw new DataEmptyException(
						"There is no data stored. \n");
			}
		}
		else {
			throw new InvalidSpecifiedRangeException(
					"The range specified is invalid. \n");
		}
	}
	
	//Returns the median given a sorted list of data
	private Double med(Double middle, LinkedList<Double> sortedData) {
		Double median;
		//If the data has only one value it is the median
		if (data.size() == 1) {
			return data.peek();
		}
		//Otherwise calculate the median
		else {
			median = calcMedian(middle, sortedData);
		}
		return median;
	}
	
	//Creates a sorted list of data in the specified range
	private LinkedList<Double> createSortedList(int start, int end) {
		LinkedList<Double> sortedData = new LinkedList<Double>();
		int index = 0;
		//Iterate through every value in the range
		Iterator<Double> itr = data.iterator();
		while(itr.hasNext()) {
			//If it is in the specified range add it sortedData list
			if ((index >= start) && (index <= end)) {
				sortedData.add(itr.next());
				//We don't need to look at anymore values
				if (index == end) {
					break;
				}
			}
			else {
				itr.next();
			}
			index++;
		}
		//Sort the list using built in sort and return
		sortedData.sort(null);
		return sortedData;
	}
	
	//Returns the median when given the middle index and a sorted list of data
	private Double calcMedian(Double middle, LinkedList<Double> sortedData) {
		Double curr = 0.0;
		int index = 0;
		//Iterate through every value
		Iterator<Double> itr = sortedData.iterator();
		while(itr.hasNext()) {
			curr = itr.next();
			//If the middle index is x.5 take the average of the two nearest points. 
			if (middle - index == 0.5) {
				double currPlusOne = itr.next();
				curr = (currPlusOne + curr) / 2.0;
				break;
			}
			//Otherwise the current value is exactly the middle and is the median.
			else if (middle - index == 0) {
				break;
			}
			index++;
		}
		return curr;
	}
	
	/**
	 * @return the sum of the values given to the object
	 * @throws DataEmptyException
	 */
	public Double getSum() throws DataEmptyException {
		return calcSum(0, data.size()-1);
	}
	
	/**
	 * @param start where to start calculating the sum (should be greater than -1)
	 * @param end where to stop calculating the sum (should be less than the max size)
	 * @return the sum of the values given to the object in the specified range
	 * @throws DataEmptyException
	 * @throws InvalidSpecifiedRangeException
	 */
	public Double getSum(int start, int end) throws DataEmptyException, InvalidSpecifiedRangeException {
		if ( (((end-start)+1) <= maxSize) && (start>=0) && (end<maxSize) )  {
			return calcSum(start, end);
		}
		else {
			throw new InvalidSpecifiedRangeException(
					"The range specified is invalid. \n");
		}
	}
	
	//Returns the sum given a specified range
	private Double calcSum(int start, int end) throws DataEmptyException {
		if (data.size() != 0) {
			Double sum = 0.0;
			int index = 0;
			//Iterate through every value in the range
			Iterator<Double> itr = data.iterator();
			while(itr.hasNext()) {
				//If it is in the specified range add it to the sum
				if ((index >= start) && (index <= end)) {
					sum += itr.next();
					//We don't need to look at anymore values
					if (index == end) {
						break;
					}
				}
				else {
					itr.next();
				}
				index++;
			}
			return sum;
		}
		else {
			throw new DataEmptyException(
					"There is no data stored. \n");
		}
	}
	
	/**
	 * @return the standard deviation of the values given to the object
	 * @throws DataEmptyException
	 */
	public Double getSD() throws DataEmptyException {
		return calcSD(0, data.size()-1);
	}
	
	/**
	 * @param start where to start calculating the standard deviation (should be greater than -1)
	 * @param end where to stop calculating the standard deviation (should be less than the max size)
	 * @return the standard deviation of the values given to the object in the specified range
	 * @throws DataEmptyException
	 * @throws InvalidSpecifiedRangeException
	 */
	public Double getSD(int start, int end) throws DataEmptyException, InvalidSpecifiedRangeException {
		if ( (((end-start)+1) <= maxSize) && (start>=0) && (end<maxSize) )  {
			return calcSD(start, end);
		}
		else {
			throw new InvalidSpecifiedRangeException(
					"The range specified is invalid. \n");
		}
	}
	
	//Returns the standard deviation given a specified range
	private Double calcSD(int start, int end) throws DataEmptyException {
		if (data.size() != 0) {
			//Uses the formula for the uncorrected sample standard deviation
			Double N = Double.valueOf(end-start)+1.0;
			
			Double mean;
			try {
				mean = getMean(start, end);
			} catch (DataEmptyException | InvalidSpecifiedRangeException e) {
				e.printStackTrace();
				return null;
			}
			
			Double sum = 0.0;
			int index = 0;
			//Iterate through every value in the range
			Iterator<Double> itr = data.iterator();
			while(itr.hasNext()) {
				if ((index >= start) && (index <= end)) {
					//Apply the formula
					sum += Math.pow(itr.next()-mean, 2);
					if (index == end) {
						break;
					}
				}
				else {
					itr.next();
				}
				index++;
			}
			//Square root to get SD not variance
			return Math.pow(sum/N, 0.5);
		}
		else {
			throw new DataEmptyException(
					"There is no data stored. \n");
		}
	}
}