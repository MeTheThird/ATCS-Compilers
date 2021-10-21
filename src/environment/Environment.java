package environment;

import java.util.HashMap;

import ast.Statement;
// TODO: update documentation (esp. the general class documentation) to include procedure stuff
// TODO: also update stuff for accessing parents
/**
 * Environment manages creating, updating, and fetching the values of variables
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Environment 
{
    private HashMap<String, Integer> vars;
    private HashMap<String, Statement> procedures;
    private Environment parentEnv;

    public Environment(Environment parentEnv)
    {
        vars = new HashMap<String, Integer>();
        procedures = new HashMap<String, Statement>();
        this.parentEnv = parentEnv;
    }

    public Environment getParentEnv()
    {
        return this.parentEnv;
    }

    /**
     * Sets the input variable to the input value
     * 
     * @param variable the input variable name
     * @param value the input value
     */
    public void setVariable(String variable, int value)
    {
        vars.put(variable, value);
    }

    /**
     * Gets the value of the input variable name
     * 
     * @param variable the input variable name
     * @return the value of the input variable
     */
    public int getVariable(String variable)
    {
        return vars.get(variable);
    }

    public void setProcedure(String procedure, Statement stmt)
    {
        if (this.parentEnv == null) procedures.put(procedure, stmt);
        else this.parentEnv.setProcedure(procedure, stmt);
    }

    public Statement getProcedure(String procedure)
    {
        if (this.parentEnv == null) return procedures.get(procedure);
        return this.parentEnv.getProcedure(procedure);
    }
}
