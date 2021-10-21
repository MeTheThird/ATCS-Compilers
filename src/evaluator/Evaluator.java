package evaluator;

import ast.*;
import ast.Number;
import environment.Environment;

/**
 * Evaluator executes and evaluates AST Statements and Expressions
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class Evaluator
{

    public void exec(Program program, Environment env)
    {
        for (ProcedureDeclaration procedure : program.getProcedures())
            env.setProcedure(procedure.getName(), procedure.getStmt());
        for (Statement stmt : program.getStmts())
            exec(stmt, env);
    }

    /**
     * Executes the input statement
     * 
     * @param stmt the input statement
     * @param env the environment to use for variables
     */
    private void exec(Statement stmt, Environment env)
    {
        if (stmt.getClass() == Writeln.class) exec((Writeln) stmt, env);
        else if (stmt.getClass() == Assignment.class) exec((Assignment) stmt, env);
        else if (stmt.getClass() == Block.class) exec((Block) stmt, env);
        else if (stmt.getClass() == If.class) exec((If) stmt, env);
        else exec((While) stmt, env);
    }

    /**
     * Executes the input Writeln statement
     * 
     * @param writeln the input Writeln statement
     * @param env the environment to use for variables
     */
    private void exec(Writeln writeln, Environment env)
    {
        System.out.println(eval(writeln.getExpression(), env));
    }

    /**
     * Executes the input Assignment statement
     * 
     * @param assignment the input Assignment statement
     * @param env the environment to use for variables
     */
    private void exec(Assignment assignment, Environment env)
    {
        env.setVariable(assignment.getVar(), eval(assignment.getExp(), env));
    }

    /**
     * Executes the input Block statement
     * 
     * @param block the input Block statement
     * @param env the environment to use for variables
     */
    private void exec(Block block, Environment env)
    {
        for (Statement stmt : block.getStmts()) exec(stmt, env);
    }

    /**
     * Executes the input If statement
     * 
     * @param ifStmt the input If statement
     * @param env the environment to use for variables
     */
    private void exec(If ifStmt, Environment env)
    {
        if (eval(ifStmt.getCond(), env)) exec(ifStmt.getStmt(), env);
    }

    /**
     * Executes the input While statement
     * 
     * @param whileStmt the input While statement
     * @param env the environment to use for variables
     */
    private void exec(While whileStmt, Environment env)
    {
        while (eval(whileStmt.getCond(), env)) exec(whileStmt.getStmt(), env);
    }

    /**
     * Evaluates the input expression
     * 
     * @param exp the input expression
     * @param env the environment to use for variables
     * @return the value of the input expression
     */
    private int eval(Expression exp, Environment env)
    {
        if (exp.getClass() == Number.class) return eval((Number) exp, env);
        if (exp.getClass() == Variable.class) return eval((Variable) exp, env);
        if (exp.getClass() == BinOp.class) return eval((BinOp) exp, env);
        if (env.getParentEnv() == null)
        {
            exec(env.getProcedure(((ProcedureCall) exp).getName()), env);
        }
        else
        {
            exec(env.getProcedure(((ProcedureCall) exp).getName()), env.getParentEnv());
        }
        return 0;
    }

    /**
     * Evaluates the input Number object
     * 
     * @param num the input Number object
     * @param env the environment to use for variables
     * @return the value of the input Number object
     */
    private int eval(Number num, Environment env)
    {
        return num.getValue();
    }

    /**
     * Evaluates the input Variable
     * 
     * @param var the input Variable
     * @param env the environment to use for variables
     * @return the value of the input Variable
     */
    private int eval(Variable var, Environment env)
    {
        return env.getVariable(var.getName());
    }

    /**
     * Evaluates the input BinOp expression
     * 
     * @param binop the input BinOp expression
     * @param env the environment to use for variables
     * @return the value of the input BinOp expression
     */
    private int eval(BinOp binop, Environment env)
    {
        int exp1Val = eval(binop.getExp1(), env);
        int exp2Val = eval(binop.getExp2(), env);
        switch (binop.getOp())
        {
            case "+": return exp1Val + exp2Val;
            case "-": return exp1Val - exp2Val;
            case "*": return exp1Val * exp2Val;
            default: return exp1Val / exp2Val;
        }
    }

    /**
     * Evaluates the input Condition
     * 
     * @param cond the input Condition
     * @param env the environment to use for variables
     * @return true if the input Condition evaluates to true, false otherwise
     */
    public boolean eval(Condition cond, Environment env)
    {
        int exp1Val = eval(cond.getExp1(), env);
        int exp2Val = eval(cond.getExp2(), env);
        switch (cond.getRelop())
        {
            case "=":
                return exp1Val == exp2Val;
            case "<>":
                return exp1Val < exp2Val || exp1Val > exp2Val;
            case "<":
                return exp1Val < exp2Val;
            case ">":
                return exp1Val > exp2Val;
            case "<=":
                return exp1Val <= exp2Val;
            default:
                return exp1Val >= exp2Val;
        }
    }
}
