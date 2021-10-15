package ast;

/**
 * If represents an if statement
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class If extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * If constructor for the construction of an if statement with the input condition expression
     * and input statement to execute if the condition expression evaluates to true
     * 
     * @param cond the Condition object that represents the input condition expression
     * @param stmt the Statement object that represents the input statement to execute if the
     * condition expression is true
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Gets the If object's condition expression
     * 
     * @return the Condition object representing the If object's condition expression
     */
    public Condition getCond()
    {
        return this.cond;
    }

    /**
     * Gets the If object's statement to execute
     * 
     * @return the Statement object representing the If object's statement to execute should the
     * condition expression evaluate to true
     */
    public Statement getStmt()
    {
        return this.stmt;
    }
}
