package emitter;

import java.io.*;

import ast.ProcedureDeclaration;

/**
 * Emitter emits lines of MIPS Assembly code to an output file
 * 
 * @author Ms. Datar and Rohan Thakur
 * @version 12/13/21
 */
public class Emitter
{
    private PrintWriter out;
    private int counter;
    private ProcedureDeclaration context;
    private int excessStackHeight;

    /**
     * Emitter constructor for the construction of an Emitter that writes to a new file with a given
     * name
     *
     * @param outputFileName the name for the new file
     */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        counter = 0;
        context = null;
        excessStackHeight = 0;
    }

    /**
     * Prints one line of code to file (with non-labels indented)
     * 
     * @param code the line of code to print to this Emitter object's file
     */
    public void emit(String code)
    {
        if (!code.contains(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * Closes the file, should be called after all calls to emit
     */
    public void close()
    {
        out.close();
    }

    /**
     * Remembers the input ProcedureDeclaration as the current procedure context, resets the stored
     * excess stack height counter to zero
     * 
     * @param proc the input ProcedureDeclaration
     */
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        this.context = proc;
        this.excessStackHeight = 0;
    }

    /**
     * Clears the current procedure context (i.e. stores null as the current procedure context)
     */
    public void clearProcedureContext()
    {
        this.context = null;
    }

    /**
     * Tells whether the input variable is a local variable or not
     * 
     * @param varName the input variable name
     * @return true if the input variable is a local variable, false otherwise
     */
    public boolean isLocalVariable(String varName)
    {
        if (this.context != null)
        {
            if (varName.equals(this.context.getName())) return true;
            for (String var : this.context.getParams()) if (varName.equals(var)) return true;
            for (String var : this.context.getLocalVars()) if (varName.equals(var)) return true;
        }
        return false;
    }

    /**
     * Gets the amount by which the stack pointer needs to be offset to access the input variable's
     * value
     *
     * @param localVarName the name of the input variable
     * @precondition localVarName is the name of a local variable for the procedure currently being
     * compiled
     * @return the amount by which the stack pointer must be offset to access the input variable's
     * value in the output MIPS code
     */
    public int getOffset(String localVarName)
    {
        for (int i = 0; i < context.getLocalVars().size(); i++)
            if (localVarName.equals(context.getLocalVars().get(i)))
                return 4 * (context.getLocalVars().size() - 1 - i) + excessStackHeight;

        for (int i = 0; i < context.getParams().size(); i++)
            if (localVarName.equals(context.getParams().get(i)))
                return 4 * (context.getParams().size() + context.getLocalVars().size() - i) + 
                    excessStackHeight;

        assert(localVarName.equals(context.getName()));
        return excessStackHeight;
    }

    /**
     * Outputs lines of code that push the given register onto the stack
     * 
     * @param reg the name of the register to push onto the stack
     */
    public void emitPush(String reg)
    {
        this.emit("sub $sp $sp 4\t# pushes " + reg + " onto the stack");
        this.emit("sw " + reg + " ($sp)");
        this.excessStackHeight += 4;
    }

    /**
     * Outputs lines of code that pop the top of the stack into the given register
     * 
     * @param reg the name of the register into which the top of the stack will be popped
     */
    public void emitPop(String reg)
    {
        this.emit("lw " + reg + " ($sp)\t# pops the top of the stack into " + reg);
        this.emit("add $sp $sp 4");
        this.excessStackHeight -= 4;
    }

    /**
     * Generates the next label ID to use for label names
     *
     * @return 0 when first called. After this method's first call for this Emitter object, one
     * greater than the value this method returned when it was last called
     */
    public int nextLabelID()
    {
        return counter++;
    }
}
