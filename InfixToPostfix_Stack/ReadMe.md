#### Infix notation: X + Y
Operators are written in-between their operands. This is the usual way we write expressions. An expression such as A * ( B + C ) / D is usually taken to mean something like: "First add B and C together, then multiply the result by A, then divide by D to give the final answer." <br>
Infix notation needs extra information to make the order of evaluation of the operators clear: rules built into the language about operator precedence and associativity, and brackets ( ) to allow users to override these rules. For example, the usual rules for associativity say that we perform operations from left to right, so the multiplication by A is assumed to come before the division by D. Similarly, the usual rules for precedence say that we perform multiplication and division before we perform addition and subtraction.

#### Postfix notation: X Y +
Operators are written after their operands. The infix expression given above is equivalent to A B C + * D /. The order of evaluation of operators is always left-to-right, and brackets cannot be used to change this order. <br>
Because the "+" is to the left of the "*" in the example above, the addition must be performed before the multiplication. Operators act on values immediately to the left of them. For example, the "+" above uses the "B" and "C". We can add (totally unnecessary) brackets to make this explicit: 
( (A (B C +) *) D /) <br>
Thus, the "*" uses the two values immediately preceding: "A", and the result of the addition. Similarly, the "/" uses the result of the multiplication and the "D".

I've written a simple JAVA program that uses stacks to convert infix expressions to postfix expressions. (See attached)
