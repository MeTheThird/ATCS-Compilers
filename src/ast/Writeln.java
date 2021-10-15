package ast;

/**
 * Writeln represents a writeln statement
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Writeln constructor for the construction of a writeln statement that prints out the value of
     * the input expression
     * 
     * @param exp the Expression object that represents the input expression
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Gets the Writeln object's expression
     * 
     * @return the Expression object that represents the expression whose value the writeln
     * statement will print out when executed
     */
    public Expression getExpression()
    {
        return this.exp;
    }
}
