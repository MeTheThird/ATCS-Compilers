package ast;

import java.util.List;

/**
 * Program represents a program
 * 
 * @author Rohan Thakur
 * @version 11/17/21
 */
public class Program
{
    private List<String> vars;
    private List<ProcedureDeclaration> procedures;
    private List<Statement> stmts;

    /**
     * Program constructor for the construction of a program with an input list of variable names,
     * an input list of procedures to declare, and an input list of statements to be executed
     *
     * @param vars the input list of variable names
     * @param procedures the input List of ProcedureDeclaration objects
     * @param stmts the input List of Statement objects
     */
    public Program(List<String> vars, List<ProcedureDeclaration> procedures, List<Statement> stmts)
    {
        this.vars = vars;
        this.procedures = procedures;
        this.stmts = stmts;
    }

    /**
     * Gets the program's list of variable names
     *
     * @return the List of String objects representing the list of variable names for the current
     * program
     */
    public List<String> getVars()
    {
        return this.vars;
    }

    /**
     * Gets the program's list of procedures that will be declared in the evaluation phase
     * 
     * @return the List of ProcedureDeclaration objects for the current program
     */
    public List<ProcedureDeclaration> getProcedures()
    {
        return this.procedures;
    }

    /**
     * Gets the program's list of statements that will be executed in the evaluation phase
     * 
     * @return the List of Statement objects for the current program
     */
    public List<Statement> getStmts()
    {
        return this.stmts;
    }

}
