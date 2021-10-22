package parser;

import java.io.*;

import environment.Environment;
import evaluator.Evaluator;
import scanner.Scanner;

/**
 * ParserTester tests the parsing and evaluation portions of the compiler
 * 
 * @author Rohan Thakur
 * @version 10/14/21
 */
public class ParserTester
{
    /**
     * Tests the parsing and evaluation portions of the compiler by repeatedly executing the output
     * of the parseStatement Parser function until the end of the input stream has been reached
     *
     * @param args the command-line input args
     */
    public static void main(String[] args)
    {
        try
        {
            FileInputStream inStream = new FileInputStream(new File("./src/parser" +
                                                                    "/parserTest8.txt"));
            Scanner scanner = new Scanner(inStream);
            Parser parser = new Parser(scanner);
            Environment env = new Environment(null);
            Evaluator evaluator = new Evaluator();

            while (scanner.hasNext())
            {
                evaluator.exec(parser.parseProgram(), env);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
