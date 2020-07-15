/**
 * *****************************************************************************
 *
 *  CMP-5014Y Data Structures and Algorithms Coursework 2
 *
 *  Title:          DSaA_CW2
 *
 *  Class:          Algo.java
 *
 *  Description:    Contains algorithms for parts 1_1 to 1_3
 *
 *  Author:         100166648
 *
 *  Last Modified:  190312
 *
 *  Ver. History:   v0.1    190312  initial framework and functionality
 *                  v1.0    190312  moved algorithms to own class (this)
 *                  v1.1    190313  abstracted all prints from algorithms
 *
 *******************************************************************************
 */
package DSaA_CW2;
//Imports

import java.util.Arrays;
import static java.util.Arrays.sort;
import java.util.concurrent.ThreadLocalRandom;

public class Algo {

    //quadratic implementation of SVD algorithm
    public static int part1_1(int[] arr) {
        //populate array to scan with variables:
        int[] part1array;
        part1array = arr;

        //counter for potential SVD
        int svdCount = 0;
        //value storage for potential SVD
        int svdVal = 0;

        //iterate through array n times and record the i-th element's frequency 
        for (int i = 0; i < part1array.length; i++) {
            //temp to compare to current potential SVD
            int contenderCount = 0;

            //repeat and check each potential against current SVD
            for (int j = 0; j < part1array.length; j++) {
                if (part1array[j] == part1array[i]) {
                    contenderCount++;
                }
            }

            //compare curent contender against running SVD
            if (contenderCount > svdCount) {
                svdCount = contenderCount;
                svdVal = part1array[i];
            }
        }

        //check potential SVD exceeds half of array size
        if (svdCount > (part1array.length / 2)) {
            return svdVal;
        } else {
            return 0;
        }
    }

    //O(n log(n)) implementation of algorithm (utilising java .sort)
    public static int part1_2(int[] arr) {
        int[] part2array = arr;
        //array to hold frequency counters
        int[] freq = new int[part2array.length];
        //populate all to 1
        for (int i = 0; i < freq.length; i++) {
            freq[i] = 1;
        }
        //external freq counter
        int freqPos = 0;
        //SVD value and frequency 
        int svdVal = 0;
        int svdCount = 0;

        //sort array
        sort(part2array, 0, part2array.length);
//informtion print commented from algorithm to prevent skewed timing results
/*
        //user notification print(s)
        System.out.println("sorted:");
        System.out.println(Arrays.toString(part2array));
        System.out.println("Array of freqency before: ");
        System.out.println(Arrays.toString(freq));
*/
        //compare element,element+1
        for (int i = 0; i < part2array.length - 1; i++) {
            if (part2array[i] == part2array[i + 1]) {
                //increment freq counter
                freq[freqPos]++;
            } else {
                //check if current count is potential SVD
                if (freq[freqPos] > svdCount) {
                    svdCount = freq[freqPos];
                    svdVal = part2array[i];
                }
                //move freq position to next i loop
                freqPos = i + 1;
                //set to freq val (incremented in nexct loop if match found)
                freq[freqPos] = 1;
            }
        }

        //check potential SVD exceeds half of array length 
        if (svdCount >= (part2array.length / 2) + 1) {
            return svdVal;
        } else {
            return 0;
        }
    }

    //part1_3 linear (Boyer-Moore implementation)
    public static int part1_3(int[] arr) {
        //find candidate
        int candidate = findCand(arr);
        //check candidate is majority
        if (isMaj(arr, candidate)) {
            return candidate;
        } else {
            return 0;
        }
    }

//part1_3 helper-methods
    //find candidate for SVD
    public static int findCand(int[] arr) {
        //container variables
        int SVDindex = 0;
        int counter = 1;

        //loop array
        for (int i = 0; i < arr.length; i++) {
            //check if element is same as next element
            if (arr[SVDindex] == arr[i]) {
                //matching element
                counter++;
            } else {
                //non matching element
                counter--;
            }
            if (counter == 0) {
                //set comparison element to that of element being compared
                SVDindex = i;
                //reset counter to one
                counter = 1;
            }
        }
        //return candidate element
        return arr[SVDindex];
    }

    //check candidate element appears more than n/2 times
    public static boolean isMaj(int[] arr, int candidate) {
        int counter = 0;
        //count number of times candidate appears in array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == candidate) {
                counter++;
            }
        }
        //return result of SVD check
        if (counter > (arr.length / 2)) {
            return true;
        } else {
            return false;
        }
    }

    //prints result of algorithm
    public static void printResult(int res) {
        if (res != 0) {
            System.out.println("SVD found: " + res);
        } else {
            System.out.println("No SVD found.");
        }
    }

    //test harness
    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        //arrays to test parts 1_1 - 1_3

        //n = 5, no SVD.
        int[] arr1 = new int[]{5, 7, 6, 5, 9};
        //n = 11, SVD: 5: freq 6/11
        int[] arr2 = new int[]{5, 7, 5, 5, 6, 5, 5, 2, 4, 5, 9};
        //n = 8, no SVD
        int[] arr3 = new int[]{5, 7, 6, 3, 2, 2, 6, 9};
        //n = 9, SVD: 9: freq 5/9
        int[] arr4 = new int[]{5, 9, 6, 5, 9, 9, 2, 9, 9};

        System.out.println("");
        System.out.println("testing part 1_1:");
        System.out.println("arr1: " + Arrays.toString(arr1));
        //run arrays through method:
        printResult(part1_1(arr1));
        System.out.print("\t");
        System.out.println("arr2: " + Arrays.toString(arr2));
        System.out.print("\t");
        printResult(part1_1(arr2));
        System.out.println("arr3: " + Arrays.toString(arr3));
        System.out.print("\t");
        printResult(part1_1(arr3));
        System.out.println("arr4: " + Arrays.toString(arr4));
        System.out.print("\t");
        printResult(part1_1(arr4));

        //output format
        System.out.println("\n--------------------\n");

        //test method with random values to study output:
        System.out.println("testing with random values:\n");
        //get five iterations of method using random length array (3-25)
        for (int i = 0; i < 5; i++) {
            int n = ThreadLocalRandom.current().nextInt(4, 25);
            //create array n long and populate random values
            int[] toPop = new int[n];
            for (int j = 0; j < n; j++) {
                toPop[j] = ThreadLocalRandom.current().nextInt(1, 10);
            }
            //run method on random array
            printResult(part1_1(toPop));
        }

        //conclusion of random numbers
        System.out.println("Testing conclusion: inconclusive.");
        System.out.println("Random numbers create an SVD too infrequently to "
                + "reliably test");
        //repeat of random testing using 'seeded' arrays:
        System.out.println("\n--------------------\n");
        //test method with random values to study output:
        System.out.println("Re-testing '3-seeded' random values:");
        System.out.println("(To increase chance of SVD being present,\n"
                + " first 3 elements of 'random' arrays are deliberately seeded"
                + " to value: 3)\n");

        //get five iterations of method using random length array (3-25)
        for (int i = 0; i < 5; i++) {
            int n = ThreadLocalRandom.current().nextInt(4, 25);
            //create array n long and populate random values (post index 3)
            int[] toPop = new int[n];
            //seed index 0-3
            for (int j = 0; j < 4; j++) {
                toPop[j] = 3;
            }
            for (int j = 4; j < n; j++) {
                toPop[j] = ThreadLocalRandom.current().nextInt(1, 10);
            }
            System.out.println("seeded array: ");
            System.out.println(Arrays.toString(toPop));
            System.out.print("\t");
            //run method on random array
            printResult(part1_1(toPop));
        }

        System.out.println("\n--------------------\n");

        //testing part1.2
        System.out.println("testing 1.2\n");
        int[] two = new int[]{3, 5, 4, 3, 4, 4, 4, 4, 3};

        System.out.println("Array to sort: ");
        System.out.println(Arrays.toString(two));
        System.out.print("\t");
        printResult(part1_2(two));

        System.out.println("\n--------------------\n");

        //testing part1_3
        System.out.println("testing part 1_3\n");

        //repeat known arrays from part1_1
        System.out.println("Using array 1: \n"
                + Arrays.toString(arr1));
        System.out.println("expected outcome: not found");
        System.out.print("\t");
        printResult(part1_3(arr1));
        System.out.println("");

        System.out.println("Using array 2: \n"
                + Arrays.toString(arr2));
        System.out.println("expected outcome SVD: 5");
        System.out.print("\t");
        printResult(part1_3(arr2));
        System.out.println("");

        System.out.println("Using array 3: \n"
                + Arrays.toString(arr3));
        System.out.println("expected outcome: not found");
        System.out.print("\t");
        printResult(part1_3(arr3));
        System.out.println("");

        System.out.println("Using array 4: \n"
                + Arrays.toString(arr4));
        System.out.println("expected outcome SVD: 9");
        System.out.print("\t");
        printResult(part1_3(arr4));

        System.out.println("\n--------------------\n");

    }

}
