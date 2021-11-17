package compiler;

import ast.*;
import ast.Number;
import emitter.Emitter;
// TODO: do ALL class documentation to reflect the fact that this compiles
// TODO: ESPECIALLY the class header
public class Compiler
{
    Emitter e;

    public void compile(Program program, String outputFileName)
    {
        e = new Emitter(outputFileName);
        e.emit(".data");
        for (String var : program.getVars()) e.emit("var" + var + ": .word 0");
        e.emit("newLine: .asciiz \"\\n\"");
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        for (Statement stmt : program.getStmts()) compile(stmt);
        e.emit("li $v0 10");
        e.emit("syscall");
    }

    private void compile(Statement stmt)
    {
        if (stmt.getClass() == Writeln.class) compile((Writeln) stmt);
        else if (stmt.getClass() == Block.class) compile((Block) stmt);
        else if (stmt.getClass() == Assignment.class) compile((Assignment) stmt);
    }

    private void compile(Expression exp)
    {
        if (exp.getClass() == Number.class) compile((Number) exp);
        else if (exp.getClass() == BinOp.class) compile((BinOp) exp);
        else if (exp.getClass() == Variable.class) compile((Variable) exp);
        else if (exp.getClass() == Condition.class) compile((Condition) exp);
    }

    private void compile(Writeln writeln)
    {
        compile(writeln.getExpression());
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0 newLine");
        e.emit("li $v0 4");
        e.emit("syscall");
    }

    private void compile(Block block)
    {
        for (Statement stmt : block.getStmts()) compile(stmt);
    }

    private void compile(Assignment assignment)
    {
        compile(assignment.getExp());
        e.emit("sw $v0 var" + assignment.getVar());
    }

    private void compile(Number num)
    {
        e.emit("li $v0 " + num.getValue());
    }

    private void compile(BinOp binop)
    {
        compile(binop.getExp1());
        e.emitPush("$v0");
        compile(binop.getExp2());
        e.emitPop("$t0");
        switch (binop.getOp())
        {
            case "+":
                e.emit("add $v0 $t0 $v0");
                return;
            case "-":
                e.emit("sub $v0 $t0 $v0");
                return;
            case "*":
                e.emit("mult $t0 $v0");
                e.emit("mflo $v0");
                return;
            default:
                e.emit("div $t0 $v0");
                e.emit("mflo $v0");
                return;
        }
    }

    private void compile(Variable var)
    {
        e.emit("lw $v0 var" + var.getName());
    }

    private void compile(Condition cond, String endLabel)
    {
        compile(cond.getExp1());
        e.emitPush("$v0");
        compile(cond.getExp2());
        e.emitPop("$t0");
        switch (cond.getRelop())
        {
            case "=":
                return;
            case "<>":
                return;
            case "<":
                return;
            case ">":
                return;
            case "<=":
                return;
            default:
                return;
        }
    }
}
