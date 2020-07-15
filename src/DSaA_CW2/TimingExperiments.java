/**
 * *****************************************************************************
 *
 *  CMP-5014Y Data Structures and Algorithms Coursework 2
 *
 *  Title:          DSaA_CW2
 *
 *  Class:          TimingExperiments.java
 *
 *  Description:    Contains methods to analyse average time taken for an
 *                  algorithm to execute.
 *                  Uses Stopwatch.java and WriteFile.java
 *
 *  Author:         100166648
 *
 *  Last Modified:  190316
 *
 *  Ver. History:   v0.1    190312  initial framework and functionality
 *                  v1.0    190316  class complete and tested.
 *                  v2.0    190316  added timing experiment modified to hashSet
 *                                  -v- ArrayHashTable experiments.
 *
 * Notes:           Only two versions of the timing experiment are written here.
 *                  (for algorithms and for hashSet v arrayHashTable)
 *                  polymorphism was intended to be used to utilise the same
 *                  single experiment code, but due to time constraints (not
 *                  helped by the hours it takes to run on part1_1) it was opted
 *                  to edit the hardcoded Algorithm or hashTables between
 *                  experiments.
 *                      This is functionality that may be added later or in
 *                  future revisions of this experiment.
 *
 *******************************************************************************
 */
package DSaA_CW2;
//Imports

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class TimingExperiments {
//Instance variables

    Stopwatch watch = new Stopwatch();

//Class Methods
    //timing experiment for aglorithms on random element array of length 1k-100k
    //repeating each length 50 times, and repeating the whole process 100 times
    public static void timingExperiment() throws IOException {

        //boolean 'drop': disregard first 10 result-set (eliminate cold-start)
        boolean drop = true;
        Stopwatch watch = new Stopwatch();
        long[] av = new long[100];

        for (int i = 0; i < 100; i++) {

            //after 10 repetitions reset loop(i) and array(av), toggle boolean
            if (i == 10 && drop == true) {
                i = 0;
                av = new long[100];
                drop = false;
            }

            //user notification of progress 
            //(will reset to 0 after first 10 loops are dropped)
            System.out.println("Complete: " + i + "%");

            //create filewriter (log experiment results to txt file)
            //use (i) as fileName number 
            WriteFile write = new WriteFile(i, 1);

            //array length (n) 1000 to 100k (1k increment)
            for (int n = 1000; n < 100001; n += 1000) {
                //keep sum of time for length n (reset each loop)
                long sum = 0;

                //open/create and add first line to log
                write.writeNew(n);

                //repeat timing each length of n
                for (int j = 0; j < 50; j++) {
                    //create random-element array of length n
                    int[] arr = new int[n];
                    for (int k = 0; k < arr.length; k++) {
                        arr[k] = random1k();
                    }

                    //variable to hold result of algorithm 
                    //(for printing after .stop)
                    int result = 0;

                    //begin stopwatch
                    watch.start();
                    //run algorithm
                    result = Algo.part1_1(arr);
                    //get elapsed time
                    watch.stop();

                    //print result to screen (commented out to declutter output)
                    //Algo.printResult(result);
                    //print value to file
                    write.writeVal(watch.elapsed);
                    //add current time to sum
                    sum = sum + watch.elapsed;
                }

                //get average of inner-repetitions of current length of n
                long mean = sum / 50;
                //print average to record file
                write.writeMean(mean);

                //add mean value to final 'average-of-averages' CSV ouput
                averageCSV(mean, n, av);
            }

            //reduce summed mean values to average for CSV file
            finaliseCSV(av);
            //initialise values of n (nV)
            int[] nV = nVinit();
            //ouput CSV file and close writeFile object (1 as flag for filename)
            write.csvOut(av, nV, 1);
            //class method avoids direct bufferedWriter access to close
            write.close();
        }
    }

    //duplication of above timing experiment (modified for ArrayHashTable tests)
    public static void ahtTest() throws IOException {

        //boolean 'drop': disregard first 10 result-set (eliminate cold-start)
        boolean drop = true;
        Stopwatch watch = new Stopwatch();
        long[] av = new long[100];

        for (int i = 0; i < 10; i++) {

            //after 2 repetitions reset loop(i) and array(av), toggle boolean
            if (i == 2 && drop == true) {
                i = 0;
                av = new long[100];
                drop = false;
            }

            //user notification of progress 
            //(will reset to 0 after first 10 loops are dropped)
            System.out.println("Complete: " + i * 10 + "%");

            //create filewriter (log experiment results to txt file)
            //use (i) as fileName number 
            WriteFile write = new WriteFile(i, 1);

            //array length (n) 1000 to 50k (1k increment)
            for (int n = 1000; n < 100001; n += 1000) {
                //keep sum of time for length n (reset each loop)
                long sum = 0;

                //open/create and add first line to log
                write.writeNew(n);

                //repeat timing each length of n
                for (int j = 0; j < 50; j++) {
                    //create random-element array of length n
                    int[] arr = new int[n];
                    for (int k = 0; k < arr.length; k++) {
                        arr[k] = random1k();
                    }

                    //create HashSet object
                    HashSet aht = new HashSet();

//                    //add array contents to AHT (comment out for insert test)
//                    for (int k = 0; k < arr.length; k++) {
//                        aht.add(arr[k]);
//                    }
                    //begin stopwatch
                    watch.start();

                    //add array contents to AHT (comment out for remove testing)
                    for (int k = 0; k < arr.length; k++) {
                        aht.add(arr[k]);
                    }

//                    //remove all added elements (comment out for insert test)
//                    for (int k = 0; k < arr.length; k++) {
//                        aht.remove(arr[k]);
//                    }
                    //get elapsed time
                    watch.stop();

                    //print value to file
                    write.writeVal(watch.elapsed);
                    //add current time to sum
                    sum = sum + watch.elapsed;
                }

                //get average of inner-repetitions of current length of n
                long mean = sum / 50;
                //print average to record file
                write.writeMean(mean);

                //add mean value to final 'average-of-averages' CSV ouput
                averageCSV(mean, n, av);
            }

            //reduce summed mean values to average for CSV file
            finaliseCSV(av);
            //initialise values of n (nV)
            int[] nV = nVinit();
            //ouput CSV file and close writeFile object (1 as flag for filename)
            write.csvOut(av, nV, 1);
            //class method avoids direct bufferedWriter access to close
            write.close();
        }
    }

//support methods    
    //sum elapsed mean from each of the 100 values for n
    public static void averageCSV(long mean, int n, long[] av) {
        //reduce n to element of 'av' array (-1 compensate for 0-indexing)
        int len = (n / 1000) - 1;
        //sum passed value of mean to corresponding element of 'av'
        av[len] += mean;
    }

    //divides summed mean values into 2nd average
    public static void finaliseCSV(long[] av) {
        for (int i = 0; i < av.length; i++) {
            av[i] = (av[i] / 10);
        }
    }

    //get list of n-values for csv output 1k-100k
    public static int[] nVinit() {
        int[] nV = new int[100];
        for (int i = 0; i < nV.length; i++) {
            nV[i] = (i + 1) * 1000;
        }
        return nV;
    }

    //returns random int number between 1-1k (user legibility)
    private static int random1k() {
        int num = ThreadLocalRandom.current().nextInt(1, 1001);
        return num;
    }

//test harness
//------------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
        //method testing
        System.out.println("teting method: random100");
        int testRand = random1k();
        System.out.println(testRand);
        System.out.println("");

        System.out.println("testing method: nVinit");
        int[] testVinit = nVinit();
        System.out.println(Arrays.toString(testVinit));
        System.out.println("");

        System.out.println("testing method: finaliseCSV");
        //crate array of long to test
        long[] testLong = new long[]
            {100, 70, 140, 210, 550, 103, 70, 899, 998676, 103333};
        finaliseCSV(testLong);
        System.out.println(Arrays.toString(testLong));
        System.out.println("");

        System.out.println("testing method: averageCSV");
        //create variables to test (use long array above
        long tLong = 500000;
        int testN = 10;
        averageCSV(1000, 1000, testLong);
        System.out.println(Arrays.toString(testLong));
        System.out.println("");

        //run alg timing experiment (commented out for testing other methods)
//        timingExperiment();
        //run aht timing experiment (commented out for testing)
//        ahtTest();

    }
}
