package ast;

import java.util.List;

/**
 * ProcedureCall represents the call to a procedure
 * 
 * @author Rohan Thakur
 * @version 10/22/21
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> args;

    /**
     * ProcedureCall constructor for the construction of a procedure call with the input procedure
     * name and list of arguments
     *
     * @param name the input procedure name
     * @param args the input List object of Expression objects that represents the procedure
     * arguments
     */
    public ProcedureCall(String name, List<Expression> args)
    {
        this.name = name;
        this.args = args;
    }

    /**
     * Gets the name of the procedure being called
     * 
     * @return the name of the procedure being called in the procedure call
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the procedure call's arguments
     * 
     * @return the input List object of Expression objects that represents the procedure
     * arguments
     */
    public List<Expression> getArgs()
    {
        return this.args;
    }

}
