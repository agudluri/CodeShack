### Problem 
Given the arrival times at the car wash, calculate the average waiting time per car.

There is a car wash with just one station in it, that is, there is one ‘server’. The arrival times (with a Poisson distribution) should be generated randomly from the mean arrival time. The service time depends on what the customer wants done, such as wash only, wash and wax, wash and vacuum, and so on. The service time for a car should be calculated just before the car enters the wash station—that’s when the customer knows how much time will be taken until the customer leaves the car wash. The service times, also with a Poisson distribution, should be generated randomly from the mean service time with the same random-number generator used for arrival times.

If an arrival occurs when there is a car being washed and there are cars in the queue, that arrival is turned away as an ‘overflow’ and not counted. Error messages should be printed for an arrival time that is not an integer, less than zero, greater than the sentinel, or less than the previous arrival time.

Here are the details regarding arrivals and departures:
- If an arrival and departure occur during the same minute, the departure is processed first.
- If a car arrives when the queue is empty and no cars are being washed, the car starts getting washed immediately; it is not put on the queue.
- A car leaves the queue, and stops waiting, once the car starts through the wash cycle.

The ***input*** consists of three positive integers: the mean arrival time, the mean service time, and the maximum arrival time. Repeatedly re-prompt until each value is a positive integer. Calculate the average waiting time and the average queue length, both to one fractional digit. The average waiting time is the sum of the waiting times divided by the number of customers. The average queue length is the sum of the queue lengths for each minute of the simulation divided by the number of minutes until the last customer departs.<br> 
To calculate the sum of the queue lengths, we add, for each minute of the simulation, the total number of customers on the queue during that minute. We can calculate this sum another way: we add, for each customer, the total number of minutes that customer was on the queue. But this is the sum of the waiting times! So we can calculate the average queue length as the sum of the waiting times divided by the number of minutes of the simulation until the last customer departs. And we already calculated the sum of the waiting times for the average waiting time. Also calculate the number of overflows. Use a seed of 100 for the random-number generator.
