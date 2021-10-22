package ast;

import java.util.List;

/**
 * Program represents a program
 * 
 * @author Rohan Thakur
 * @version 10/22/21
 */
public class Program
{
    private List<ProcedureDeclaration> procedures;
    private List<Statement> stmts;

    /**
     * Program constructor for the construction of a program with an input list of procedures to
     * declare and statements to execute in the evaluation phase
     *
     * @param procedures the input List of ProcedureDeclaration objects
     * @param stmts the input List of Statement objects
     */
    public Program(List<ProcedureDeclaration> procedures, List<Statement> stmts)
    {
        this.procedures = procedures;
        this.stmts = stmts;
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
