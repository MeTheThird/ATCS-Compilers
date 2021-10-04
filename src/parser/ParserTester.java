package parser;

import java.io.*;
import scanner.Scanner;

/**
 * ParserTester tests the Parser class
 * 
 * @author Rohan Thakur
 * @version 9/29/21
 */
public class ParserTester
{
    /**
     * Tests the Parser class by repeatedly calling the parseStatement Parser function until the end
     * of the input stream has been reached
     * 
     * @param args the command-line input args
     */
    public static void main(String[] args)
    {
        try
        {
            FileInputStream inStream = new FileInputStream(new File("./src/parser" +
                                                                    "/parserTest4.txt"));
            Scanner lex = new Scanner(inStream);
            Parser parser = new Parser(lex);
            while (lex.hasNext())
            {
                parser.parseStatement();
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
