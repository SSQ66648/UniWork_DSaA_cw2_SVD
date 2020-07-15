/**
 * *****************************************************************************
 *
 *  CMP-5014Y Data Structures and Algorithms Coursework 1
 *  /Reused for CMP-5014Y Coursework 2
 *
 *  Title:          DSaA_CW2
 *
 *  Class:          Stopwatch.java
 *
 *  Description:    Used for algorithm timing experiments.
 *                  Contains methods to record system nanotime and return
 *                  duration elapsed.
 *
 *  Author:         100166648
 *
 *  Last Modified:  190312
 *
 *  Ver. History:   v0.1    181115  initial framework and functionality
 *                  v1.0    181115  functionality testing in main confirmed
 *                  v1.1    190312  reformatted to currently preferred layout
 *
 *******************************************************************************
 */
package DSaA_CW2;
//Imports
//n/a

public class Stopwatch {

//Instance variables
    long startTime;
    long endTime;
    long elapsed;

//Constructors
    //default
    public Stopwatch() {
        startTime = 0;
        endTime = 0;
        elapsed = 0;
    }

//Accessor methods
    public long getStart() {
        return startTime;
    }

    public long getEnd() {
        return endTime;
    }

    public long getElapsed() {
        return elapsed;
    }

//Mutator methods
    // n/a
//Class Methods
    //record current system nanotime
    public void start() {
        startTime = System.nanoTime();
    }

    //record 2nd system nanotime and return difference
    public long stop() {
        endTime = System.nanoTime();
        elapsed = endTime - startTime;
        return elapsed;
    }

//Test harness
    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        //create object
        Stopwatch watch = new Stopwatch();
        //begin timer
        watch.start();
        //create some delay (create int, increment, repeat 1k)
        for (int i = 0; i < 1000; i++) {
            int a = 0;
            a++;
        }
        //get elapsed time
        watch.stop();
        //print
        System.out.println("Start time: " + watch.startTime);
        System.out.println("End time: " + watch.endTime);
        System.out.println("Elapsed: " + watch.elapsed);
    }
}
