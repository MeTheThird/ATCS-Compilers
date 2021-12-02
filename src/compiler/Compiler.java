package compiler;

import java.time.LocalDate;

import ast.*;
import ast.Number;
import emitter.Emitter;
// TODO: update class documentation including class header (esp the version)
/**
 * Compiler compiles Program objects into MIPS Assembly code
 * 
 * @author Rohan Thakur
 * @version 12/2/21
 */
public class Compiler
{
    Emitter e;

    /**
     * Compiles the input program into a MIPS Assembly output file with a given file name
     * 
     * @param program the input Program object to compile
     * @param outputFileName the given file name of the output file
     */
    public void compile(Program program, String outputFileName)
    {
        e = new Emitter(outputFileName);
        e.emit("# auto-generated compiled MIPS Assembly code");
        e.emit("#");
        e.emit("# @author Rohan Thakur");
        e.emit("# @version " + LocalDate.now());
        e.emit(".data");
        for (String var : program.getVars()) e.emit("var" + var + ": .word 0");
        e.emit("newLine: .asciiz \"\\n\"");
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        for (Statement stmt : program.getStmts()) compile(stmt);
        e.emit("li $v0 10\t# normal program termination");
        e.emit("syscall");
        for (ProcedureDeclaration procedure : program.getProcedures()) compile(procedure);
        e.close();
    }

    /**
     * Compiles the input statement
     * 
     * @param stmt the input Statement object to compile
     */
    private void compile(Statement stmt)
    {
        if (stmt.getClass() == Writeln.class) compile((Writeln) stmt);
        else if (stmt.getClass() == Block.class) compile((Block) stmt);
        else if (stmt.getClass() == Assignment.class) compile((Assignment) stmt);
        else if (stmt.getClass() == If.class) compile((If) stmt);
        else compile((While) stmt);
    }

    /**
     * Compiles the input expression
     * 
     * @param exp the input Expression object to compile
     */
    private void compile(Expression exp)
    {
        if (exp.getClass() == Number.class) compile((Number) exp);
        else if (exp.getClass() == BinOp.class) compile((BinOp) exp);
        else if (exp.getClass() == Variable.class) compile((Variable) exp);
        else compile((ProcedureCall) exp);
    }

    /**
     * Compiles the input Writeln statement
     * 
     * @param writeln the input Writeln object to compile
     */
    private void compile(Writeln writeln)
    {
        compile(writeln.getExpression());
        e.emit("move $a0 $v0\t# prints out $v0 followed by a new line char");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0 newLine");
        e.emit("li $v0 4");
        e.emit("syscall");
    }

    /**
     * Compiles the input Block statement
     * 
     * @param block the input Block object to compile
     */
    private void compile(Block block)
    {
        for (Statement stmt : block.getStmts()) compile(stmt);
    }

    /**
     * Compiles the input Assignment statement
     * 
     * @param assignment the input Assignment statement to compile
     */
    private void compile(Assignment assignment)
    {
        compile(assignment.getExp());
        e.emit("sw $v0 var" + assignment.getVar() + "\t# stores $v0 in var" + assignment.getVar());
    }

    /**
     * Compiles the input If statement
     * 
     * @param ifStmt the input If statement to compile
     */
    private void compile(If ifStmt)
    {
        String endLabel = "endif" + e.nextLabelID();
        compile(ifStmt.getCond(), endLabel);
        compile(ifStmt.getStmt());
        e.emit(endLabel + ":");
    }

    /**
     * Compiles the input While statement
     * 
     * @param whileStmt the input While statement to compile
     */
    private void compile(While whileStmt)
    {
        int labelID = e.nextLabelID();
        String beginLabel = "beginwhile" + labelID;
        String endLabel = "endwhile" + labelID;
        e.emit(beginLabel + ":\t# begins the while loop");
        compile(whileStmt.getCond(), endLabel);
        compile(whileStmt.getStmt());
        e.emit("j " + beginLabel + "\t# restarts the while loop");
        e.emit(endLabel + ":");
    }

    private void compile(ProcedureDeclaration procedure)
    {
        e.emit("proc" + procedure.getName() + ":\t# begins the procedure " + procedure.getName());
        compile(procedure.getStmt());
        e.emit("jr $ra");
    }

    /**
     * Compiles the input Number expression
     * 
     * @param num the input Number object to compile
     */
    private void compile(Number num)
    {
        e.emit("li $v0 " + num.getValue() + "\t# loads " + num.getValue() + " into $v0");
    }

    /**
     * Compiles the input BinOp expression
     * 
     * @param binop the input BinOp object to compile
     */
    private void compile(BinOp binop)
    {
        compile(binop.getExp1());
        e.emitPush("$v0");
        compile(binop.getExp2());
        e.emitPop("$t0");
        switch (binop.getOp())
        {
            case "+":
                e.emit("add $v0 $t0 $v0\t# adds $t0 and $v0, stores the result in $v0");
                return;
            case "-":
                e.emit("sub $v0 $t0 $v0\t# does $t0 minus $v0, stores the result in $v0");
                return;
            case "*":
                e.emit("mult $t0 $v0\t# multiplies $t0 and $v0, stores the result in $v0");
                e.emit("mflo $v0");
                return;
            default:
                e.emit("div $t0 $v0\t# divides $t0 by $v0, stores the result in $v0");
                e.emit("mflo $v0");
                return;
        }
    }

    /**
     * Compiles the input Variable expression
     * 
     * @param var the input Variable object to compile
     */
    private void compile(Variable var)
    {
        e.emit("lw $v0 var" + var.getName() + "\t# loads var" + var.getName() + " into $v0");
    }

    private void compile(ProcedureCall procedureCall)
    {
        e.emitPush("$ra");
        for (Expression arg : procedureCall.getArgs())
        {
            compile(arg);
            e.emitPush("$v0");
        }
        e.emit("jal proc" + procedureCall.getName() + "\t# calls " + procedureCall.getName());
        for (int i = 0; i < procedureCall.getArgs().size(); i++) e.emitPop("$t0");
        e.emitPop("$ra");
    }

    /**
     * Compiles the input Condition expression using the input end label as the name of the
     * condition's ending label
     *
     * @param cond the input Condition object to compile
     * @param endLabel the input String object representing the name of the condition's ending label
     */
    private void compile(Condition cond, String endLabel)
    {
        compile(cond.getExp1());
        e.emitPush("$v0");
        compile(cond.getExp2());
        e.emitPop("$t0");
        switch (cond.getRelop())
        {
            case "=":
                e.emit("bne $t0 $v0 " + endLabel + "\t# executes the code below if $t0 " +
                                                   "equals $v0");
                return;
            case "<>":
                e.emit("beq $t0 $v0 " + endLabel + "\t# executes the code below if $t0 " +
                                                   "does not equal $v0");
                return;
            case "<":
                e.emit("bge $t0 $v0 " + endLabel + "\t# executes the code below if $t0 " +
                                                   "is less than $v0");
                return;
            case ">":
                e.emit("ble $t0 $v0 " + endLabel + "\t# executes the code below if $t0 " +
                                                   "is greater than $v0");
                return;
            case "<=":
                e.emit("bgt $t0 $v0 " + endLabel + "\t# executes the code below if $t0 " +
                                                   "is less than or equal to $v0");
                return;
            default:
                e.emit("blt $t0 $v0 " + endLabel + "\t# executes the code below if $t0 " +
                                                   "is greater than or equal to $v0");
                return;
        }
    }
}
