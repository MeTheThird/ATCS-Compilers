package environment;

import java.util.List;

import ast.Statement;

public class ParamsStatementPair
{
    private List<String> first;
    private Statement second;
    
    public ParamsStatementPair(List<String> first, Statement second)
    {
        this.first = first;
        this.second = second;
    }

    public List<String> getFirst()
    {
        return this.first;
    }

    public Statement getSecond()
    {
        return this.second;
    }
}
