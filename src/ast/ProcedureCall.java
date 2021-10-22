package ast;

import java.util.List;

public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> args;

    public ProcedureCall(String name, List<Expression> args)
    {
        this.name = name;
        this.args = args;
    }

    public String getName()
    {
        return this.name;
    }

    public List<Expression> getArgs()
    {
        return this.args;
    }

}
