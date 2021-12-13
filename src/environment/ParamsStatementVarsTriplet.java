package environment;

import java.util.List;

import ast.Statement;

/**
 * ParamsStatementVarsTriplet represents the triplet of the List object representing a procedure's
 * parameters, the Statement object representing the statement that calling the procedure will
 * execute during the evaluation phase, and the List object representing the procedure's local
 * variables
 *
 * @author Rohan Thakur
 * @version 12/13/21
 */
public class ParamsStatementVarsTriplet
{
    private List<String> first;
    private Statement second;
    private List<String> third;

    /**
     * ParamsStatementVarsTriplet constructor for the construction of a triplet of a procedure's
     * list of parameters, its corresponding statement object to be executed when the procedure is
     * called, and its list of local variables
     *
     * @param first the List object of Strings that represents the procedure's list of parameters
     * @param second the Statement object that represents the statement to be executed when the
     * procedure is called
     * @param third the List object of Strings that represents the procedure's list of local
     * variables
     */
    public ParamsStatementVarsTriplet(List<String> first, Statement second, List<String> third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Gets the list of parameters in the ParamsStatementVarsTriplet object
     *
     * @return the List object of Strings in the first slot of the current
     * ParamsStatementVarsTriplet object
     */
    public List<String> getFirst()
    {
        return this.first;
    }

    /**
     * Gets the statement in the ParamsStatementVarsTriplet object
     *
     * @return the Statement object in the second slot of the current ParamsStatementVarsTriplet
     * object
     */
    public Statement getSecond()
    {
        return this.second;
    }

    /**
     * Gets the list of local variables in the ParamsStatementVarsTriplet object
     * 
     * @return the List object of Strings in the third slot of the current
     * ParamsStatementVarsTriplet object
     */
    public List<String> getThird()
    {
        return this.third;
    }
}
