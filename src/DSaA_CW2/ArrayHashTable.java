/**
 * *****************************************************************************
 *
 *  CMP-5014Y Data Structures and Algorithms Coursework 1
 *  /Reused for CMP-5014Y Coursework 2
 *
 *  Title:          DSaA_CW2
 *
 *  Class:          ArrayHashTable.java
 *
 *  Description:    Extends HashTable.java
 *                  Implementation of a hash table with chaining using arrays
 *                  to store chains
 *
 *  Author:         100166648
 *
 *  Last Modified:
 *
 *  Ver. History:   v0.1    190316  initial framework and functionality
 *                  v1.0    190316  finished implementation and testing of 
 *                                  inherited methods and helper classes
 *                                  (getBucket,extendChain,countUpdate,advance)
 *
 *******************************************************************************
 */
package DSaA_CW2;
//Imports

import java.util.Arrays;

public class ArrayHashTable extends HashTable {
//instance variables
    //2D array to store data

    Object[][] table;
    //default chain-storage array size
    int chainSize = 5;
    //number of elements at loction of hash
    int[] counts;

//Constructor
    //Default
    public ArrayHashTable() {
        //set default capacity as per instructions
        this.capacity = 10;
        //set table to 'array of arrays size capacity' as per instructions
        table = new Object[capacity][];
        //initialise count array to zero 
        //(default of 0 guaranteed by the language spec)
        counts = new int[capacity];
    }

//Helper methods
    //return bucket index from hashcode of passed object
    public int getBucket(Object ob) {
        int hash = ob.hashCode();
        //hashcode modded by capacity to return index in range
        return hash % capacity;
    }

    //doubles length of passed Object array
    public Object[] extendChain(Object[] obArr) {
        //deep copy into larger array
        Object[] longer = new Object[obArr.length * 2];
        for (int i = 0; i < obArr.length; i++) {
            longer[i] = obArr[i];
        }
        return longer;
    }

    //copies elements to previous index beginning at passed index+1
    //this also handles the 'removal' of element at passed index of array
    public Object[] advance(Object[] arr, int index) {
        //loop through remaining array elements
        for (int i = index + 1; i < arr.length; i++) {
            //copy element to previous position
            arr[i - 1] = arr[i];
        }
        return arr;
    }

    //updates chain length counter
    public void countUpdate() {
        //loop through table buckets
        for (int i = 0; i < table.length; i++) {
            //check bucket contains chain
            if (table[i] != null) {
                //update counts value to be the number of objects in the chain
                counts[i] = table[i].length;
            }else{
                //no chain present. set count to 0 to reflect null
                counts[i]= 0;
            }
        }
    }
    
    

//Inherited methods
    @Override
    boolean add(Object obj) {
        //get index
        int bucketIndex = getBucket(obj);
        //check if chain already present
        if (table[bucketIndex] == null) {
            //no existing chain. create chain array
            table[bucketIndex] = new Object[chainSize];
            //add obj as first element of chain
            table[bucketIndex][0] = obj;
            //addition confirmed. update counts
            countUpdate();
            //addition complete. return success
            return true;
        } else {
            //loop to add to array if full
            while (true) {
                //loop to end of chain and add object
                for (int i = 0; i < table[bucketIndex].length; i++) {
                    //element exists
                    if (table[bucketIndex][i] != null) {
                        //check for existing copy of object
                        if (table[bucketIndex][i].equals(obj)) {
                            //identical object already in array. return failure
                            return false;
                        }
                    } else {
                        //no element present, insert object
                        table[bucketIndex][i] = obj;
                        //addition confirmed. update counts
                        countUpdate();
                        //object added, return success
                        return true;
                    }
                }
                //no space in array. extend and retry insert
                table[bucketIndex] = extendChain(table[bucketIndex]);
            }
        }
    }

    @Override
    //checks entire hash table for passed object
    boolean contains(Object obj) {
        //loop through buckets
        for (int i = 0; i < table.length; i++) {
            //check chain is present
            if (table[i] != null) {
                //check each bucket element
                for (int j = 0; j < table[i].length; j++) {
                    //check object present at array index
                    if (table[i][j] != null) {
                        if (table[i][j].equals(obj)) {
                            //identical object found. return success
                            return true;
                        }
                    }
                }
            }
        }
        //object not found. return falure
        return false;
    }

    @Override
    //removes passed object from table if present
    boolean remove(Object obj) {
        //get bucket index from objects hashcode
        int target = getBucket(obj);

        //check bucket contains chain
        if (table[target] != null) {
            //loop through chain array
            for (int i = 0; i < table[target].length; i++) {
                //check element exists
                if (table[target][i] != null) {
                    if (table[target][i].equals(obj)) {
                        //target found. begin move of remaining array.
                        table[target] = advance(table[target], i);
                        //removal confirmed. update counts
                        countUpdate();
                        //removal complete. return success
                        return true;
                    }
                } else {
                    //found null element. end of chain. not present.
                    return false;
                }
            }
            //entire chain full but does not contain target object
            return false;
        } else {
            //target bucket is empty. return failure
            return false;
        }
    }
    //Test harness
    //--------------------------------------------------------------------------

    public static void main(String[] args) {

        ArrayHashTable testH = new ArrayHashTable();

        System.out.println("testing default constructor");
        System.out.println("capacity: " + testH.capacity);
        System.out.println("chain size: " + testH.chainSize);
        System.out.println("counts: " + Arrays.toString(testH.counts));
        System.out.println("table:" + Arrays.toString(testH.table));

        System.out.println("");
        System.out.println("testing hash");
        Object testOb = new Object();
        Object testOb2 = new Object();
        int testHc = testOb.hashCode();
        System.out.println("test object hashcode: " + testHc);
        System.out.println("testing getBucket method:" 
                + testH.getBucket(testOb));
        System.out.println("");

        System.out.println("testing extendChain method");
        Object[] testChain = new Object[testH.chainSize];
        System.out.println("beginning chainsize: " + testChain.length);
        testChain = testH.extendChain(testChain);
        System.out.println("extended chainsize: " + testChain.length);

        System.out.println("testing add method");
        System.out.println(testH.add(testOb));
        System.out.println("test object added to table");
        System.out.println(Arrays.toString(testH.table));
        System.out.println("checking bucket in detail");
        System.out.println(
                Arrays.toString(testH.table[testH.getBucket(testOb)]));
        System.out.println("attempting to add same object. result: ");
        System.out.println(testH.add(testOb));
        System.out.println("adding second object");
        System.out.println(testH.add(testOb2));
        System.out.println(Arrays.toString(testH.table));
        System.out.println("adding multiple objects to test add method use of "
                + "extendChain");
        for (int i = 0; i < 100; i++) {
            Object multi = new Object();
            testH.add(multi);
        }
        System.out.println("printing each bucket:");
        for (int i = 0; i < testH.table.length; i++) {
            System.out.println(Arrays.toString(testH.table[i]));
        }
        System.out.println("");

        System.out.println("testing contains method");
        System.out.println("table contains test object 1: "
                + testH.contains(testOb));
        System.out.println("check for outerloop nullpointers:\n"
                + "setting table[2] to null");
        testH.table[2] = null;
        System.out.println(Arrays.toString(testH.table));
        System.out.println("repeating contains(): "
                + testH.contains(testOb));

        Object conTest = new Object();
        System.out.println("testing contains newly created object: "
                + testH.contains(conTest));
        System.out.println("");

        System.out.println("testing remove method");
        System.out.println("confirming chain containing target object");
        System.out.println(
                Arrays.toString(testH.table[testH.getBucket(testOb)]));
        System.out.println("target object: " + testOb);
        System.out.println("removing target object: " + testH.remove(testOb));
        System.out.println("confirming removal and advance of remaining "
                + "elements");
        System.out.println(
                Arrays.toString(testH.table[testH.getBucket(testOb)]));
        System.out.println("");
        
        System.out.println("testing updateCount()");
        System.out.println("testing successful updating in methods");
        System.out.println("(0 before implementation of method-calling)");
        System.out.println(Arrays.toString(testH.counts));
        System.out.println("count array forced to zeros "
                + "(for pre-implementation testing)");
        for (int i = 0; i < testH.counts.length; i++) {
            testH.counts[i] = 0;
        }
        System.out.println("zero-count array: ");
        System.out.println(Arrays.toString(testH.counts));
        testH.countUpdate();
        System.out.println("counts array after method: ");
        System.out.println(Arrays.toString(testH.counts));
        System.out.println("adding another loop of objects to table");
        for (int i = 0; i < 100; i++) {
            Object multi = new Object();
            testH.add(multi);
        }
        System.out.println("rechecking counts array");
        System.out.println(Arrays.toString(testH.counts));
        System.out.println("");
        
    }

}
