package emitter;

import java.io.*;

/**
 * Emitter emits lines of MIPS Assembly code to an output file
 * 
 * @author Ms. Datar and Rohan Thakur
 * @version 11/19/21
 */
public class Emitter
{
    private PrintWriter out;
    private int counter;

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
    }

    /**
     * Prints one line of code to file (with non-labels indented)
     * 
     * @param code the line of code to print to this Emitter object's file
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
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
     * Outputs lines of code that push the given register onto the stack
     * 
     * @param reg the name of the register to push onto the stack
     */
    public void emitPush(String reg)
    {
        this.emit("sub $sp $sp 4 \t # pushes " + reg + " onto the stack");
        this.emit("sw " + reg + " ($sp)");
    }

    /**
     * Outputs lines of code that pop the top of the stack into the given register
     * 
     * @param reg the name of the register into which the top of the stack will be popped
     */
    public void emitPop(String reg)
    {
        this.emit("lw " + reg + " ($sp) \t # pops the top of the stack into " + reg);
        this.emit("add $sp $sp 4");
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
