package ast;

import java.util.*;

/**
 * Block represents a block of statements
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * Block constructor for the construction of a Block object with a list of statements
     * 
     * @param stmts the input list of Statement objects
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Gets the Block's list of statements
     * 
     * @return the List of Statement objects contained within the Block
     */
    public List<Statement> getStmts()
    {
        return this.stmts;
    }
}
