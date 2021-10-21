package ast;

import java.util.List;

public class Program
{
    private List<ProcedureDeclaration> procedures;
    private List<Statement> stmts;

    public Program(List<ProcedureDeclaration> procedures, List<Statement> stmts)
    {
        this.procedures = procedures;
        this.stmts = stmts;
    }

    public List<ProcedureDeclaration> getProcedures()
    {
        return this.procedures;
    }

    public List<Statement> getStmts()
    {
        return this.stmts;
    }
}
