package environment;

import java.util.HashMap;
import java.util.List;

import ast.Statement;
// TODO: update class documentation (incl. class header esp. the date)
/**
 * Environment manages creating, updating, and fetching variables and procedures
 *
 * @author Rohan Thakur
 * @version 10/22/21
 */
public class Environment
{
    private HashMap<String, Integer> vars;
    private HashMap<String, ParamsStatementVarsTriplet> procedures;
    private Environment parentEnv;

    /**
     * Environment constructor fro the construction of an Environment object to use for storing
     * procedures and variables, with a reference to its parent environment
     *
     * @param parentEnv the parent environment of the current Environment object
     */
    public Environment(Environment parentEnv)
    {
        this.vars = new HashMap<String, Integer>();
        this.procedures = new HashMap<String, ParamsStatementVarsTriplet>();
        this.parentEnv = parentEnv;
    }

    /**
     * Gets the Environment object's parent environment
     *
     * @return the Environment object representing the Environment object's parent environment
     */
    public Environment getParentEnv()
    {
        return this.parentEnv;
    }

    /**
     * Declares a variable to have a given value in the current environment
     *
     * @param variable the name of the variable to declare
     * @param value the value to assign to the variable
     */
    public void declareVariable(String variable, int value)
    {
        this.vars.put(variable, value);
    }

    /**
     * Sets the input variable to the input value in the parent environment if the parent
     * environment exists, the variable doesn't exist in the current environment, and the variable
     * exists in the parent environment. Sets the input variable to the input value in the current
     * environment otherwise
     *
     * @param variable the name of the input variable
     * @param value the input value
     */
    public void setVariable(String variable, int value)
    {
        if (this.parentEnv != null && !this.vars.containsKey(variable) &&
                this.parentEnv.vars.containsKey(variable))
            this.parentEnv.vars.put(variable, value);
        else this.vars.put(variable, value);
    }

    /**
     * Gets the value of the input variable from the current environment if the variable exists in
     * the current environment, gets the input variable's value from the parent environment
     * otherwise
     *
     * @param variable the name of the input variable
     * @return the value of the input variable
     */
    public int getVariable(String variable)
    {
        if (this.vars.containsKey(variable)) return this.vars.get(variable);
        return this.parentEnv.getVariable(variable);
    }

    /**
     * Declares the input procedure in the global environment
     *
     * @param procedure the name of the input procedure
     * @param params the list of parameters for the input procedure
     * @param stmt the statement that calling the input procedure will execute
     */
    public void setProcedure(String procedure, List<String> params, Statement stmt,
            List<String> localVars)
    {
        if (this.parentEnv == null)
            this.procedures.put(procedure, new ParamsStatementVarsTriplet(params, stmt, localVars));
        else this.parentEnv.setProcedure(procedure, params, stmt, localVars);
    }

    /**
     * Gets the statement that calling the input procedure will execute
     *
     * @param procedure the name of the input procedure
     * @return the Statement AST object that represents the statement that calling the input
     * procedure will execute
     */
    public Statement getProcedure(String procedure)
    {
        if (this.parentEnv == null) return this.procedures.get(procedure).getSecond();
        return this.parentEnv.getProcedure(procedure);
    }

    /**
     * Gets the list of parameters for the input procedure
     *
     * @param procedure the name of the input procedure
     * @return the List object of Strings that represents the list of parameters for the input
     * procedure
     */
    public List<String> getParams(String procedure)
    {
        if (this.parentEnv == null) return this.procedures.get(procedure).getFirst();
        return this.parentEnv.getParams(procedure);
    }

    public List<String> getLocalVars(String procedure)
    {
        if (this.parentEnv == null) return this.procedures.get(procedure).getThird();
        return this.parentEnv.getLocalVars(procedure);
    }
}
