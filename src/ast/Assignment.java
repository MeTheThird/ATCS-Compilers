package ast;

public class Assignment extends Statement
{
    String var;
    Expression exp;

    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }
}
