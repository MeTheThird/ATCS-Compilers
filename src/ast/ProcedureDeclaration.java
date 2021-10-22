package ast;

import java.util.List;

public class ProcedureDeclaration extends Statement
{
    private String name;
    private List<String> params;
    private Statement stmt;

    public ProcedureDeclaration(String name, List<String> params, Statement stmt)
    {
        this.name = name;
        this.params = params;
        this.stmt = stmt;
    }

    public String getName()
    {
        return this.name;
    }

    public List<String> getParams()
    {
        return this.params;
    }

    public Statement getStmt()
    {
        return this.stmt;
    }

}
