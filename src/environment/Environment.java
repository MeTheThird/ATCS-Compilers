package environment;

import java.util.HashMap;

/**
 * Environment manages creating, updating, and fetching the values of variables
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Environment 
{
    HashMap<String, Integer> vars = new HashMap<String, Integer>();

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
}
