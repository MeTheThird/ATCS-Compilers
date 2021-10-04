package ast;

import java.util.*;

public class Block extends Statement
{
    List<Statement> stmts;

    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }
}
