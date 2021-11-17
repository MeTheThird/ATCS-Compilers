package parser;

import java.io.*;

import compiler.Compiler;
import environment.Environment;
import evaluator.Evaluator;
import scanner.Scanner;

/**
 * ParserTester tests the parsing and evaluation portions of the compiler
 * 
 * @author Rohan Thakur
 * @version 11/17/21
 */
public class ParserTester
{
    /**
     * Tests the parsing and evaluation portions of the compiler by executing the output
     * of the parseProgram Parser function
     *
     * @param args the command-line input args
     */
    public static void main(String[] args)
    {
        try
        {
            FileInputStream inStream = new FileInputStream(new File("./src/parser" +
                                                                    "/parserTest0.txt"));
            Scanner scanner = new Scanner(inStream);
            Parser parser = new Parser(scanner);
            Environment env = new Environment(null);
            Evaluator evaluator = new Evaluator();
            Compiler compiler = new Compiler();

            compiler.compile(parser.parseProgram(), "output.asm");
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
