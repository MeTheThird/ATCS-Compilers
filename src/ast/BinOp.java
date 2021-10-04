package ast;

public class BinOp extends Expression
{
    String op;
    Expression exp1;
    Expression exp2;
    
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
}
