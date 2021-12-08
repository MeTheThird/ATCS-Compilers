package ast;

import java.util.List;
// TODO: update class documentation (incl. class header esp. the date)
/**
 * ProcedureDeclaration represents the declaration of a procedure
 * 
 * @author Rohan Thakur
 * @version 10/22/21
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private List<String> params;
    private List<String> localVars;
    private Statement stmt;

    /**
     * ProcedureDeclaration constructor for the construction of a procedure with the input name,
     * list of parameters, and statement to execute when called
     *
     * @param name the name of the procedure
     * @param params the List object of Strings that represents the procedure's parameters
     * @param stmt the Statement object that represents the statement to be executed when the
     * procedure is called
     */
    public ProcedureDeclaration(String name, List<String> params, List<String> localVars,
            Statement stmt)
    {
        this.name = name;
        this.params = params;
        this.localVars = localVars;
        this.stmt = stmt;
    }

    /**
     * Gets the procedure's name
     * 
     * @return the name of the procedure
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the procedure's list of parameters
     * 
     * @return the List object of Strings that represents the procedure's parameters
     */
    public List<String> getParams()
    {
        return this.params;
    }

    public List<String> getLocalVars()
    {
        return this.localVars;
    }

    /**
     * Gets the procedure's statement to execute when the procedure is called
     * 
     * @return the Statement object that represents the statement to be executed when the
     * procedure is called
     */
    public Statement getStmt()
    {
        return this.stmt;
    }

}
