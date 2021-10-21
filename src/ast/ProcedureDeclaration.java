package ast;

public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement stmt;

    public ProcedureDeclaration(String name, Statement stmt)
    {
        this.name = name;
        this.stmt = stmt;
    }

    public String getName()
    {
        return this.name;
    }

    public Statement getStmt()
    {
        return this.stmt;
    }

}
