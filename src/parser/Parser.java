package parser;

import java.util.*;

import ast.*;
import ast.Number;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser parses the input lexemes from an instance of the Scanner class
 *
 * @author Rohan Thakur
 * @version 12/13/21
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;

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
     * Parses the current program
     * 
     * @precondition currentToken begins a program
     * @postcondition currentToken has advanced to the end of the input stream, and all of the
     * tokens in the input stream have been eaten
     * @return a Program AST object that represents the parsed program
     */
    public Program parseProgram()
    {
        List<String> vars = parseVars();
        List<ProcedureDeclaration> procedures = parseProcedureDeclarations();
        List<Statement> stmts = new ArrayList<Statement>();

        while (!currentToken.equals("END"))
        {
            Statement stmt = parseStatement();
            stmts.add(stmt);
        }
        return new Program(vars, procedures, stmts);
    }

    /**
     * Parses the current variable declarations
     *
     * @precondition currentToken begins a variable declaration
     * @postcondition currentToken has advanced past the current variable declarations, and all of
     * the tokens associated with the current variable declarations have been eaten
     * @return a List object of Strings containing the names of the variables that have been parsed
     */
    private List<String> parseVars()
    {
        List<String> vars = new ArrayList<String>();

        while (currentToken.equals("VAR"))
        {
            eat("VAR");
            while (!currentToken.equals(";"))
            {
                vars.add(currentToken);
                eat(currentToken);
                if (!currentToken.equals(";")) eat(",");
            }
            eat(";");
        }
        return vars;
    }

    /**
     * Parses the procedure declarations
     * 
     * @precondition currentToken begins a procedure declaration
     * @postcondition currentToken has advanced past all procedure declarations, and all of the
     * token associated with the procedure declarations have been eaten
     * @return a List object of ProcedureDeclaration objects representing the procedure declarations
     * that have been parsed
     */
    private List<ProcedureDeclaration> parseProcedureDeclarations()
    {
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();

        while (currentToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String procedureName = currentToken;
            eat(currentToken);
            eat("(");
            List<String> params = new ArrayList<String>();
            if (!currentToken.equals(")"))
            {
                params.add(currentToken);
                eat(currentToken);
                while (!currentToken.equals(")"))
                {
                    eat(",");
                    params.add(currentToken);
                    eat(currentToken);
                }
            }
            eat(")");
            eat(";");
            List<String> localVars = parseVars();
            Statement stmt = parseStatement();

            procedures.add(new ProcedureDeclaration(procedureName, params, localVars, stmt));
        }
        return procedures;
    }

    /**
     * Parses the current integer
     *
     * @precondition currentToken is an integer
     * @postcondition currentToken has advanced past the parsed integer token, the current integer
     * token has been eaten
     * @return an Expression AST object that represents the parsed integer
     */
    private Expression parseNumber()
    {
        int numVal = Integer.parseInt(currentToken);
        eat(currentToken);
        return new Number(numVal);
    }

    /**
     * Parses the current statement or block of statements and returns a new AST object of the
     * appropriate type for evaluation by the Evaluator
     *
     * @precondition currentToken begins a statement
     * @postcondition currentToken has advanced past the current statement, all of the current
     * statement's associated tokens have been eaten
     * @return a Statement AST object that represents the current statement
     */
    private Statement parseStatement()
    {
        switch (currentToken)
        {
            case "WRITELN":
                eat("WRITELN");
                eat("(");
                Expression exp = parseExpr();
                eat(")");
                eat(";");
                return new Writeln(exp);

            case "BEGIN":
                eat("BEGIN");
                List<Statement> stmts = new ArrayList<Statement>();
                while (!currentToken.equals("END"))
                    stmts.add(parseStatement());
                eat("END");
                eat(";");
                return new Block(stmts);

            case "IF":
                eat("IF");
                Condition cond = parseCondition();
                eat("THEN");
                Statement stmt = parseStatement();
                return new If(cond, stmt);

            case "WHILE":
                eat("WHILE");
                cond = parseCondition();
                eat("DO");
                stmt = parseStatement();
                return new While(cond, stmt);

            default:
                String varName = currentToken;
                eat(currentToken);
                eat(":=");
                Expression varVal = parseExpr();
                eat(";");
                return new Assignment(varName, varVal);
        }
    }

    /**
     * Parses the current condition expression and returns a new AST Condition object with the
     * parameters of the current condition expression
     *
     * @precondition currentToken begins a condition expression
     * @postcondition currentToken has been advanced past the current condition expression, all of
     * the condition expression's associated tokens have been eaten
     * @return a Condition AST object that represents the parsed condition expression
     */
    private Condition parseCondition()
    {
        Expression exp1 = parseExpr();
        String relop = currentToken;
        switch (relop)
        {
            case "=":
                eat("=");
                break;
            case "<>":
                eat("<>");
                break;
            case "<":
                eat("<");
                break;
            case ">":
                eat(">");
                break;
            case "<=":
                eat("<=");
                break;
            default:
                eat(">=");
                break;
        }
        Expression exp2 = parseExpr();
        return new Condition(relop, exp1, exp2);
    }

    /**
     * Parses the current numeric factor where a factor represents any integer expression that might
     * be multiplied or divided with other factors
     *
     * @precondition currentToken begins a factor
     * @postcondition currentToken has advanced past the current factor, all of the factor's
     * associated tokens have been eaten
     * @return an Expression AST object that represents the parsed factor
     */
    private Expression parseFactor()
    {
        if (currentToken.equals("("))
        {
            eat("(");
            Expression ret = parseExpr();
            eat(")");
            return ret;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return new BinOp("*", new Number(-1), parseFactor());
        }
        else if (currentToken.matches("[0-9]+"))
        {
            return parseNumber();
        }
        String id = currentToken;
        eat(id);
        if (currentToken.equals("("))
        {
            eat("(");
            List<Expression> args = new ArrayList<Expression>();
            if (!currentToken.equals(")"))
            {
                args.add(parseExpr());
                while (!currentToken.equals(")"))
                {
                    eat(",");
                    args.add(parseExpr());
                }
            }
            eat(")");
            return new ProcedureCall(id, args);
        }
        return new Variable(id);
    }

    /**
     * Parses the current numeric term where a term represents any integer expression that might be
     * added or subtracted with other terms
     *
     * @precondition currentToken begins a term
     * @postcondition currentToken has advanced past the current term, all of the term's associated
     * tokens have been eaten
     * @return an Expression AST object that represents the parsed term
     */
    private Expression parseTerm()
    {
        Expression ret = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(op, ret, parseFactor());
        }
        return ret;
    }

    /**
     * Parses the current integer expression
     *
     * @precondition currentToken begins an integer expression
     * @postcondition currentToken has advanced past the current expression, all of the expression's
     * associated tokens have been eaten
     * @return an Expression AST object that represents the parsed expression
     */
    private Expression parseExpr()
    {
        Expression ret = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String op = currentToken;
            eat(currentToken);
            ret = new BinOp(op, ret, parseTerm());
        }
        return ret;
    }


}
