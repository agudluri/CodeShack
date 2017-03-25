The definition of a Fibonacci function:<br>

![definition](http://timmurphy.org/examples/fibonacci.png)

Now, you can come up with a simple recursive method that utilizes the function defintion but not so surprisingly, its runtime complexity would be **exponential** owing to the fact that several redundant calls would have to be made to itself to calculate the same values. And we, as programmers, know how much we hate anything exponential. <br>
To tackle this situation, we could use the concept of memoization and devise a method whose complexity would be linear, i.e., O(n). I've done the same in the code attached. You're welcome.
