package environment;

import java.util.HashMap;
import java.util.List;

import ast.Statement;
// TODO: update documentation (esp. the general class documentation) to include procedure stuff
// TODO: also update stuff for accessing parents
// TODO: update the documentation of each method because they now access parent stuff, and the usage
// TODO: of setVariable has changed
/**
 * Environment manages creating, updating, and fetching the values of variables
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Environment
{
    private HashMap<String, Integer> vars;
    private HashMap<String, ParamsStatementPair> procedures;
    private Environment parentEnv;

    public Environment(Environment parentEnv)
    {
        this.vars = new HashMap<String, Integer>();
        this.procedures = new HashMap<String, ParamsStatementPair>();
        this.parentEnv = parentEnv;
    }

    public Environment getParentEnv()
    {
        return this.parentEnv;
    }

    public void declareVariable(String variable, int value)
    {
        this.vars.put(variable, value);
    }

    /**
     * Sets the input variable to the input value
     * 
     * @param variable the input variable name
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
     * Gets the value of the input variable name
     * 
     * @param variable the input variable name
     * @return the value of the input variable
     */
    public int getVariable(String variable)
    {
        if (this.vars.containsKey(variable)) return this.vars.get(variable);
        return this.parentEnv.getVariable(variable);
    }

    public void setProcedure(String procedure, List<String> params, Statement stmt)
    {
        if (this.parentEnv == null)
            this.procedures.put(procedure, new ParamsStatementPair(params, stmt));
        else this.parentEnv.setProcedure(procedure, params, stmt);
    }

    public Statement getProcedure(String procedure)
    {
        if (this.parentEnv == null) return this.procedures.get(procedure).getSecond();
        return this.parentEnv.getProcedure(procedure);
    }

    public List<String> getParams(String procedure)
    {
        if (this.parentEnv == null) return this.procedures.get(procedure).getFirst();
        return this.parentEnv.getParams(procedure);
    }
}
