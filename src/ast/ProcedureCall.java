package ast;

public class ProcedureCall extends Expression
{
    private String name;

    public ProcedureCall(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

}
