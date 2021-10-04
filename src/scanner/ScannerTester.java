package scanner;

import java.io.*;

/**
 * ScannerTester tests the Scanner class
 * 
 * @author Rohan Thakur
 * @version 9/28/21
 */
public class ScannerTester
{

    /**
     * Runs test() to test the Scanner class
     * 
     * @param args the command-line input args
     */
    public static void main(String[] args)
    {
        test();
    }

    /**
     * Tests the Scanner class
     */
    private static void test()
    {
        try
        {
            FileInputStream inStream = new FileInputStream(new File("./src/scanner" +
                                                                    "/ScannerTestAdvanced.txt"));
            Scanner lex = new Scanner(inStream);
            while (lex.hasNext())
            {
                try
                {
                    System.out.println(lex.nextToken());
                }
                catch (ScanErrorException e)
                {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
