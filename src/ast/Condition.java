package ast;

/**
 * Condition represents a condition expression
 *
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Condition extends Expression
{
    private String relop;
    private Expression exp1;
    private Expression exp2;

    /**
     * Condition constructor for the construction of a condition expression with two expressions
     * separated by a relative operator
     *
     * @param relop the relative operator as a String
     * @param exp1 the Expression object that represents the expression on the left-hand side of the
     * relative operator
     * @param exp2 the Expression object that represents the expression on the right-hand side of
     * the relative operator
     */
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Gets the Condition object's relative operator
     *
     * @return the relative operator as a String
     */
    public String getRelop()
    {
        return this.relop;
    }

    /**
     * Gets the Condition object's expression on the left-hand side of the relative operator
     *
     * @return the Expression object that represents the expression on the left-hand side of the
     * relative operator
     */
    public Expression getExp1()
    {
        return this.exp1;
    }

    /**
     * Gets the Condition object's expression on the right-hand side of the relative operator
     *
     * @return the Expression object that represents the expression on the right-hand side of the
     * relative operator
     */
    public Expression getExp2()
    {
        return this.exp2;
    }
}
