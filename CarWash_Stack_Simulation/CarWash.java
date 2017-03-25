package cs401.lab8.trees;

import java.util.*;


public class CarWash {
	public final static String OVERFLOW = " (Overflow)\n";

	public final static String HEADING = "\nTime\tEvent\t\tWaiting Time\n";

	public final static int INFINITY = 10000; // indicates no car being washed

	public static int Service_time=0;
	
	public final static int MAX_SIZE = 5; // maximum cars allowed in carQueue

	protected Queue<Car> carQueue;

	protected LinkedList<String> results; // the sequence of events in the
											// simulation

	protected int currentTime, nextDepartureTime, numberOfCars, waitingTime, sumOfWaitingTimes;

	protected int num_overflows, meanArrivalTime, meanServiceTime, maxArrivalTime;
	
	protected Random random;
	
	/**
	 * Initializes this CarWash object.
	 *
	 */
	public CarWash() {
		carQueue = new LinkedList<Car>();
		results = new LinkedList<String>();
		results.add(HEADING);
		currentTime = 0;
		numberOfCars = 0;
		waitingTime = 0;
		sumOfWaitingTimes = 0;
		num_overflows = 0;
		nextDepartureTime = INFINITY; // no car being washed
		random = new Random(100);
	} // constructor

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public int getMeanArrivalTime() {
		return meanArrivalTime;
	}

	public void setMeanArrivalTime(int meanArrivalTime) {
		this.meanArrivalTime = meanArrivalTime;
	}

	public int getMeanServiceTime() {
		return meanServiceTime;
	}

	public void setMeanServiceTime(int meanServiceTime) {
		this.meanServiceTime = meanServiceTime;
	}

	public int getMaximumArrivalTime() {
		return maxArrivalTime;
	}

	public void setMaximumArrivalTime(int maximumArrivalTime) {
		this.maxArrivalTime = maximumArrivalTime;
	}

	/**
	 * Handles all events from the current time up through the specified time
	 * for the next arrival.
	 *
	 * @param nextArrivalTime
	 *            - the specified time when the next arrival will occur.
	 *
	 * @throws IllegalArgumentException
	 *             - if nextArrivalTime is less than the current time.
	 *
	 */
	
	public LinkedList<String> process(int nextArrivalTime) {
		
		final String BAD_TIME = "The time of the next arrival cannot be less than the current time.";

		if (nextArrivalTime < currentTime)
			throw new IllegalArgumentException(BAD_TIME);
		while (nextArrivalTime >= nextDepartureTime){
			processDeparture();
		}
		return processArrival(nextArrivalTime);
	} // process

	/**
	 * Moves the just arrived car into the car wash (if there is room on the car
	 * queue), or turns the car away (if there is no room on the car queue).
	 *
	 * @param nextArrivalTime
	 *            - the arrival time of the just-arrived car.
	 *
	 */
	protected LinkedList<String> processArrival(int nextArrivalTime) {
		
		
		final String ARRIVAL = "\tArrival";

		currentTime = nextArrivalTime;
		if (carQueue.size() == MAX_SIZE) {
			num_overflows++;
			results.add(Integer.toString(currentTime) + ARRIVAL + OVERFLOW);
		} else {
			results.add(Integer.toString(currentTime) + ARRIVAL);
			numberOfCars++;
			if (nextDepartureTime == INFINITY){ // if no car is being washed
				
				Service_time = getTime(meanServiceTime);
				nextDepartureTime = currentTime + Service_time;
				System.out.println("Service time:"+Service_time);
			}
			else
				carQueue.add(new Car(nextArrivalTime));
			results.add("\n");
		} // not an overflow
		return results;
	} // method processArrival

	/**
	 * Updates the simulation to reflect the fact that a car has finished
	 * getting washed.
	 *
	 */
	protected LinkedList<String> processDeparture() {
		final String DEPARTURE = "\tDeparture\t\t";

		int arrivalTime;

		currentTime = nextDepartureTime;
		results.add(Integer.toString(currentTime) + DEPARTURE + Integer.toString(waitingTime) + "\n");
		if (!carQueue.isEmpty()) {
			Car car = carQueue.remove();
			arrivalTime = car.getArrivalTime();
			waitingTime = currentTime - arrivalTime;
			sumOfWaitingTimes += waitingTime;
			
			int lol = getTime(meanServiceTime);
			nextDepartureTime = currentTime + lol;
			System.out.println("Service time: "+lol);
		} // carQueue was not empty
		else {
			waitingTime = 0;
			nextDepartureTime = INFINITY; // no car is being washed
		} // carQueue was empty
		return results;
	} // method processDeparture

	/**
	 * Washes all cars that are still unwashed after the final arrival.
	 *
	 */
	public LinkedList<String> finishUp() {
		while (nextDepartureTime < INFINITY) // while there are unwashed cars
			processDeparture();
		return results;
	} // finishUp

	/**
	 * Returns the history of this CarWash object's arrivals and departures, and
	 * the average waiting time.
	 *
	 * @return the history of the simulation, including the average waiting
	 *         time.
	 *
	 */
	public LinkedList<String> getResults() {
		final String NO_CARS_MESSAGE = "There were no cars in the car wash.\n";

		final String AVERAGE_WAITING_TIME_MESSAGE = "\n\nThe average waiting time, in minutes, was ";

		final String temp1 = "\n\nThe average queue length was ";
		
		final String temp2 = "\n\nThe number of overflows was ";

		if (numberOfCars == 0)
			results.add(NO_CARS_MESSAGE);
		else {
			results.add(AVERAGE_WAITING_TIME_MESSAGE + Double.toString((double) sumOfWaitingTimes / numberOfCars));
			
			results.add(temp1+Double.toString((double) sumOfWaitingTimes / currentTime)+" cars per minute");
			
			results.add(temp2+ num_overflows+"\n");
		}
		return results;
	} // method getResults
	
	public int getTime(int meanTime) {
		double randomDouble = random.nextDouble();
		return (int) Math.round(-meanTime * Math.log(1 - randomDouble));
	}

}
