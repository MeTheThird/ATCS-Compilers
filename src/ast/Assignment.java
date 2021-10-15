package ast;

/**
 * Assignment represents assignment statements
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Assignment constructor for the construction of an assignment statement that sets the input
     * variable to the input expression
     *
     * @param var the input variable name
     * @param exp the input expression
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Gets the Assignment object's variable name
     * 
     * @return the name of the variable in the assignment statement as a String
     */
    public String getVar()
    {
        return this.var;
    }

    /**
     * Gets the Assignment object's expression
     * 
     * @return the Expression object being assigned to the Assignment object's variable
     */
    public Expression getExp()
    {
        return this.exp;
    }
}
