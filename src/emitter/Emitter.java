package emitter;

import java.io.*;
// TODO: javadoc this (ESPECIALLY the class header)
/**
 * Emitter does stuff
 * 
 * @author Ms. Datar and Rohan Thakur
 * @version 11/15/21
 */
public class Emitter
{
    private PrintWriter out;

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
    }

    //prints one line of code to file (with non-labels indented)
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    //closes the file.  should be called after all calls to emit.
    public void close()
    {
        out.close();
    }

    public void emitPush(String reg)
    {
        this.emit("sub $sp $sp 4");
        this.emit("sw " + reg + " ($sp)");
    }

    public void emitPop(String reg)
    {
        this.emit("lw " + reg + " ($sp)");
        this.emit("add $sp $sp 4");
    }
}
