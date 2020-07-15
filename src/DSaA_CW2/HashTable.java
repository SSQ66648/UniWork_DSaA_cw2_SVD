/**
 * *****************************************************************************
 *
 *  CMP-5014Y Data Structures and Algorithms Coursework 2
 *
 *  Title:          DSaA_CW2
 *
 *  Class:          HashTable.java
 *
 *  Description:    Provided abstract class to extend in ArrayHashTable class.
 *
 *  Author:         100166648
 *
 *  Last Modified:  190316
 *
 *  Ver. History:   v0.0    190316  copied provided class into package
 *
 *******************************************************************************
 */
package DSaA_CW2;

/**
 *
 * @author ajb
 */
public abstract class HashTable {
    int capacity=100;
    int size=0;
    
    public int size(){ return size;}
    
/**
 * Adds the specified element to this hash table if it is not already present 
 * 
 * @param obj
 * @return true if the element is successfully added
 */    
   abstract boolean add(Object obj);
    
/**
 * 
 * @param obj
 * @return  true if this hash table contains the specified element
 */    
   abstract boolean contains(Object obj);
/**
 * 
 * @param obj
 * @return Removes the specified element from this set if it is present 
 */
  abstract  boolean remove(Object obj);
    
}
