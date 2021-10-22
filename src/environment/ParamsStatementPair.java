package environment;

import java.util.List;

import ast.Statement;

/**
 * ParamsStatementPair represents the pair of the List object representing a procedure's parameters
 * and the Statement object representing the statement that calling the procedure will execute
 * during the evaluation phase
 * 
 * @author Rohan Thakur
 * @version 10/22/21
 */
public class ParamsStatementPair
{
    private List<String> first;
    private Statement second;

    /**
     * ParamsStatementPair constructor for the construction of a pair of a procedure's list of
     * parameters and its corresponding statement object to be executed when the procedure is called
     *
     * @param first the List object of Strings that represents the procedure's list of parameters
     * @param second the Statement object that represents the statement to be executed when the
     * procedure is called
     */
    public ParamsStatementPair(List<String> first, Statement second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the list of parameters in the ParamsStatementPair object
     * 
     * @return the List object of Strings in the current ParamsStatementPair object
     */
    public List<String> getFirst()
    {
        return this.first;
    }

    /**
     * Gets the statement in the ParamsStatementPair object
     * 
     * @return the Statement object in the current ParamsStatementPair object
     */
    public Statement getSecond()
    {
        return this.second;
    }
}
