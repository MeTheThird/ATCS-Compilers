package parser;

import java.util.*;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser parses the input lexemes from an instance of the Scanner class
 *
 * @author Rohan Thakur
 * @version 9/29/21
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;
    private HashMap<String, Integer> vars;

    /**
     * Parser constructor for the construction of a Parser that uses a Scanner as input
     *
     * @param scanner the input Scanner object to use
     */
    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
        try
        {
            this.currentToken = scanner.nextToken();
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        vars = new HashMap<String, Integer>();
    }

    /**
     * Advances currentToken to the next token by calling scanner.nextToken() if the expected string
     * value matches currentToken, throws an IllegalArgumentException otherwise
     *
     * @param expected the expected value of currentToken
     * @throws IllegalArgumentException when the value of currentToken is not equal to the expected
     * value of currentToken
     */
    private void eat(String expected) throws IllegalArgumentException
    {
        if (expected.equals(currentToken))
        {
            try
            {
                this.currentToken = scanner.nextToken();
            }
            catch (ScanErrorException e)
            {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        else
            throw new IllegalArgumentException("Illegal character: expected " + expected +
                                            " and found " + currentToken);
    }

    /**
     * Parses the current integer and returns its value
     *
     * @precondition currentToken is an integer
     * @postcondition currentToken has advanced past the parsed integer token, the current integer
     * token has been eaten
     * @return the value of the parsed integer
     */
    private int parseNumber()
    {
        int ret = Integer.parseInt(currentToken);
        eat(currentToken);
        return ret;
    }

    /**
     * Parses and executes the current statement or block of statements where a statement is either
     * a WRITELN statement that prints out integer expressions or a variable assignment statement
     * that assigns a variable to an integer expression value
     *
     * @precondition currentToken begins a WRITELN statement or a block of WRITELN statements
     * @postcondition currentToken has advanced past all of the WRITELN statement's associated
     * tokens, and all of the WRITELN statement's associated tokens have been eaten
     */
    public void parseStatement()
    {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            System.out.println(parseExpr());
            eat(")");
            eat(";");
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while (!currentToken.equals("END"))
                parseStatement();
            eat("END");
            eat(";");
        }
        else
        {
            String varName = currentToken;
            eat(currentToken);
            eat(":=");
            int varVal = parseExpr();
            eat(";");

            vars.put(varName, varVal);
        }
    }

    /**
     * Parses and returns the value of the current numeric factor where a factor represents any
     * integer expression that might be multiplied or divided with other factors
     *
     * @precondition currentToken begins a factor
     * @postcondition currentToken has advanced past the current factor, all of the factor's
     * associated tokens have been eaten
     * @return the value of the current factor
     */
    private int parseFactor()
    {
        if (currentToken.equals("("))
        {
            eat("(");
            int ret = parseExpr();
            eat(")");
            return ret;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return -1 * parseFactor();
        }
        else if (vars.containsKey(currentToken))
        {
            String temp = currentToken;
            eat(temp);
            return vars.get(temp);
        }
        return parseNumber();
    }

    /**
     * Parses and returns the value of the current numeric term where a term represents any integer
     * expression that might be added or subtracted with other terms
     *
     * @precondition currentToken begins a term
     * @postcondition currentToken has advanced past the current term, all of the term's associated
     * tokens have been eaten
     * @return the value of the current term
     */
    private int parseTerm()
    {
        int ret = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                ret *= parseFactor();
            }
            else
            {
                eat("/");
                ret /= parseFactor();
            }
        }
        return ret;
    }

    /**
     * Parses and returns the value of the current integer expression
     * 
     * @precondition currentToken begins an integer expression
     * @postcondition currentToken has advanced past the current expression, all of the expression's
     * associated tokens have been eaten
     * @return the value of the current expression
     */
    private int parseExpr()
    {
        int ret = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                ret += parseTerm();
            }
            else
            {
                eat("-");
                ret -= parseTerm();
            }
        }
        return ret;
    }


}
