package environment;

import java.util.List;

import ast.Statement;
// TODO: update class documentation (incl. the class header esp. the date)
// TODO: this class's name was changed, so update that in the documentation
/**
 * ParamsStatementPair represents the pair of the List object representing a procedure's parameters
 * and the Statement object representing the statement that calling the procedure will execute
 * during the evaluation phase
 * 
 * @author Rohan Thakur
 * @version 10/22/21
 */
public class ParamsStatementVarsTriplet
{
    private List<String> first;
    private Statement second;
    private List<String> third;

    /**
     * ParamsStatementPair constructor for the construction of a pair of a procedure's list of
     * parameters and its corresponding statement object to be executed when the procedure is called
     *
     * @param first the List object of Strings that represents the procedure's list of parameters
     * @param second the Statement object that represents the statement to be executed when the
     * procedure is called
     */
    public ParamsStatementVarsTriplet(List<String> first, Statement second, List<String> third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
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

    public List<String> getThird()
    {
        return this.third;
    }
}
