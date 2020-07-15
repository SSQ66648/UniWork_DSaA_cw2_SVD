/**
 *******************************************************************************
 *
 *  CMP-5014Y Data Structures and Algorithms Coursework 1
 *
 *  Program:        Frequency Distribution Histogram
 *
 *  Class:          WriteFile
 *
 *  Description:    Creates incrementally named txt files and
 *                  writes timing experiment values to them
 *                  (separate class for possible future adapt & re-use)
 *                  additional CSV file output of "averages-of-averages"
 *
 *  Author:         100166648
 *
 *  Last Modified:  26/11/18
 *
 *  Ver. History:   v0.1    15/11/18 (initial framework and functionality)
 *                  v0.2    15/11/18 (method of writing to file revised)
 *                  v0.3    15/11/18 (test harness functionality tests)
 *                  v1.0    19/11/18 (corrected file print errors)
 *                  v1.1    19/11/18 (added CSV output method)
 *                  v1.2    21/11/18 (added try-catch exceptions)
 *                  v1.3    21/11/18 (added periodic writer-flushing)
 *                  v2.0    22/11/18 (finalised CSV file writing)
 *                  v2.0.1  26/11/18 (added flag to change experiment filenames)
 *
 *******************************************************************************
 */
package DSaA_CW2;

//imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class WriteFile {

    //instance variables
    int number;
    int length;
    String fileName;
    FileWriter fileWriter;
    BufferedWriter writerB;
    String printStr;
    StringBuilder build = new StringBuilder("mean values:\r\n");
    int taskFlag;

    //constructor
    //passed current file number and current iteration of length n
    public WriteFile(int num, int flag) throws IOException {
        try {
            number = num;
            taskFlag = flag;
            //get file name and create writers based on flag
            fileName = String.format("Experiment" + flag + "_%02d.txt", number);
            writerB = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            System.out.println("Error constructing WriteFile\n" + e);
        }
    }

    //class methods
    //creation and first addition to file
    public void writeNew(int len) throws IOException {
        try {
            //passes current array length n
            length = len;
            //set up first line printed to file
            printStr = String.format("Run time for length n=%03d:\r\n", length);
            //print initial line
            writerB.append(printStr);
            //periodic writer-flush
            writerB.flush();
            //intentionally not closed
        } catch (Exception e) {
            System.out.println("Error during first write to record file\n" + e);
        }
    }

    //write value of time for current iteration
    public void writeVal(long value) throws IOException {
        try {
            //printStr = String.format("%09d\r\n",value);
            writerB.write(String.valueOf(value) + "\r\n");
        } catch (Exception e) {
            System.out.println("Error writing value to record file\n" + e);
        }
    }

    //write average(mean) of five repeats
    public void writeMean(long mean) throws IOException {
        try {
            printStr = String.format("Average run time: %07d\r\n\r\n", mean);
            writerB.append(printStr);
            //periodic writer-flush
            writerB.flush();
            //add mean to stringbuilder
            build.append(String.format("%d\n", mean));
        } catch (Exception e) {
            System.out.println("Error during writeMean function\n" + e);
        }
    }

    //close file
    public void close() throws IOException {
        try {
            //periodic writer-flush
            writerB.flush();
            writerB.close();
        } catch (Exception e) {
            System.out.println("Error closing file\n" + e);
        }
    }

    //CSV file output
    public static void csvOut(long[] av, int[] nV, int flag) {
        try {
            try (PrintWriter outputfile
                    = new PrintWriter(new File("CSVoutput"+flag+".csv"))) {
                //remove brackets from array output
                String formatAv = Arrays.toString(av)
                        .replace("[", "")
                        .replace("]", "");
                String formatNv = Arrays.toString(nV)
                        .replace("[", "")
                        .replace("]", "");
                outputfile.write(formatNv + "\n");
                outputfile.write(formatAv + "\n");
                outputfile.flush();
            }
        } catch (Exception e) {
            System.out.println("Error writing CSV file\n" + e);
        }
    }

    //test harness
    public static void main(String[] args) throws IOException {
        try {
            WriteFile test = new WriteFile(99, 1);
            int n = 9;
            test.writeNew(n);
            long testValue = 999999999;
            test.writeVal(testValue);
            long testMean = 999000999;
            test.writeMean(testMean);
            test.close();
            long[] av = new long[10];
            int[] nV = new int[10];
            int flag = 9;
            csvOut(av,nV,flag);
        } catch (Exception e) {
            System.out.println("Error during test harness execution\n" + e);
        }
    }

}
