package ast;

/**
 * While represents a while statement
 *
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class While extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * While constructor for the construction of a while statement with the input condition
     * expression and input statement to execute while the condition expression evaluates to true
     *
     * @param cond the Condition object that represents the input condition expression
     * @param stmt the Statement object that represents the input statement to execute while the
     * condition expression is true
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Gets the While object's condition expression
     *
     * @return the Condition object representing the While object's condition expression
     */
    public Condition getCond()
    {
        return this.cond;
    }

    /**
     * Gets the While object's statement to execute
     *
     * @return the Statement object representing the While object's statement to execute while the
     * condition expression evaluates to true
     */
    public Statement getStmt()
    {
        return this.stmt;
    }
}
