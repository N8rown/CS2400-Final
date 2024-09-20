//
// Name: Brown, Nathan 
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// An implementation of a grapht ADT to map airports along with an alorgithm that finds the shortest path between said airports.
//
import java.util.Iterator;
import java.util.NoSuchElementException;
public class Dictionary<K, V> implements DictionaryInterface<K, V> {
    private int collisions = 0;
    //Dictionary
    private int numEntries;
    private static final int DEFAULT_CAPACITY = 64;
    private static final int MAX_CAPACITY = 256;
    //Hash table
    private Entry<K, V>[] hashTable;
    private int tableSize;
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    //private static final double MAX_LOAD_FACTOR = 0.5;  //not used
    protected final Entry<K, V> AVAILABLE = new Entry<>(null, null);

    public Dictionary()
    {
        this(DEFAULT_CAPACITY); // Call next constructor
    } // end default constructor

    public Dictionary(int initialCapacity)
    {
        //initialCapacity = checkCapacity(initialCapacity);
        numEntries = 0;
        tableSize = getNextPrime(initialCapacity);

        @SuppressWarnings("unchecked")
        Entry<K, V>[] temp = (Entry<K,V>[])new Entry[tableSize];
        hashTable = temp;
    }
 
    /**Gets the number of collisions that occur when adding entries to the dictionary. A collision being when 
     * their is another entry with a different key that is at the index of the entry being added
     * @retrun The number of collisons that occurs when hashing */
    @Override
    public int getCollisionCount()
    {
        return collisions;
    }

    /**A method that adds new etries into the dictionary which store a key and a value
     * @param key the search key/word that is being stored in the dictionary 
     * @param value the related data to the key, in this case the number of times the key appears in the constiturion
     * @return the value that was stored by the key before adding, returning null when the key is new
    */
    @Override
    public V add(K key, V value)
    {
        V oldValue = null;
        int index = getHashIndex(key);
        if ((key == null) || (value == null))
            throw new IllegalArgumentException();
        else if (!isNumeric((String)key))
        { 
            index = linearProbe(index, key); //checking if index is free and finding index
            assert (index >= 0) && (index < hashTable.length);
            if (hashTable[index] == null)
            {

                hashTable[index] = new Entry<>(key, value);
                numEntries++;
                oldValue = null;
            }
            else
            {
                oldValue = hashTable[index].getValue();
                hashTable[index].setValue(value);
            }
            return oldValue;
        }
        else
            return oldValue;
        
    }

    /**Finding and returning the value that is stored with the given key
     * @param key the key word/ object that is searched for in the dictionary
     * @return the value that is stored and associated with the key value, in this case the number of times it appears in the constitution */
    @Override
    public V getValue(K key)
    {
        V result = null;
        int index = getHashIndex(key);
        index = locate(index, key);
        if(index != -1)
            result = hashTable[index].getValue();
        return result;
    }
    /**UNIMPLEMENTED
     * Would check if the dictionary contains an entry with a key
     * @param key the keyword/ object that is searched for in the dictionary
     * @return whether or not the key exists in the current dictionary
     */
    @Override
    public boolean contains(K key)
    {
        return false;
    }
    /**Creates and returns a keyIterator 
     * @return an iterator that can traverese the keys in the dictionary */
    @Override
    public Iterator<K> getKeyIterator()
    {
        Iterator<K> keyIt = new KeyIterator();
        return keyIt;
    }
    @Override
    public Iterator<V> getValueIterator()
    {
        Iterator<V> valIt = new ValueIterator();
        return valIt;
    }
    /**UNIMPLEMENTED
     * @return whether or not the dictionary is empty or not
     */
    @Override
    public boolean isEmpty()
    {
        return false;
    }
    /**Gets the size of the dictionary
     * @retutn the total size of the dictionary, the max number of entries possible in the table */
    @Override
    public int getSize()
    {
        return hashTable.length;
    }
    /**gets the number of entries in dictionary
     * @return the number of entries that have been added into the dictionary */
    @Override
    public int getEntries()
    {
        return numEntries;
    }
    /**UNIMPLEMENTED
     * deletes every entry from dictionary
     */
    @Override
    public void clear()
    {

    }
    /**UNIMPLEMENTED
     * deletes an entry from dictionary
     * @param key the keyword of the entry to be deleted
     * @return the value of the deleted key
     */
    @Override
    public V remove(K key)
    {
        return null;
    }


    /**A class that is used to store data together in the dictionary
     * Stores keys and value with getters and setters
     */
    private static class Entry<K, V>
    {
        private K key;
        private V value;
        private States state;
        private enum States{CURRENT, REMOVED}
        private Entry(K searchKey, V dataValue)
        {
            key = searchKey;
            value = dataValue;
            state = States.CURRENT;
        } 
        private K getKey()
        {
            return key;
        } 
        private V getValue()
        {
            return value;
        } 
        private void setValue(V dataValue)
        {
            value = dataValue;
        }
    }

    /** creats an iterator for keys allowing the traversal of the dictionary */
    private class KeyIterator implements Iterator<K>
    {
        private int currentIndex;
        private int numberLeft;
        private KeyIterator()
        {
            currentIndex = 0;
            numberLeft = numEntries;
        }
        public boolean hasNext()
        {
            return (numberLeft > 0);
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public K next()
        {
            K result = null;
            if(hasNext())
            {
                while((hashTable[currentIndex] == null) || hashTable[currentIndex] == AVAILABLE)
                {
                    currentIndex++;
                }
                result = hashTable[currentIndex].getKey();
                numberLeft--;
                currentIndex++;
            }
            else
                throw new NoSuchElementException();

            return result;
        }

    }
    private class ValueIterator implements Iterator<V>
    {
        private int currentIndex;
        private int numberLeft;
        private ValueIterator()
        {
            currentIndex = 0;
            numberLeft = numEntries;
        }
        public boolean hasNext()
        {
            return (numberLeft > 0);
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public V next()
        {
            V result = null;
            if(hasNext())
            {
                while((hashTable[currentIndex] == null) || hashTable[currentIndex] == AVAILABLE)
                {
                    currentIndex++;
                }
                result = hashTable[currentIndex].getValue();
                numberLeft--;
                currentIndex++;
            }
            else
                throw new NoSuchElementException();

            return result;
        }

    }
    /**Is used to create an custom index for each keyword to reliably stor entries
     * @param key the word that is taken and hashed into a hashcide index
     * @return the hashcode index where the entry will be stored
     */
    private int getHashIndex(K key)
    {
        int hashIndex = key.hashCode()%hashTable.length;
        if (hashIndex< 0)
            hashIndex = hashIndex + hashTable.length;
        return hashIndex;
    }
    /**Finds the next prime number given an number
     * @param input the integer that is used as a startpoint to find the closest larger prime
     * @return the next prime int
     */
    public  static int getNextPrime(int input) {
        for(int i=2;i<input;i++) {
            if(input % i ==0  ) {
                input++;
                i=2;
            }
            else{
                continue;
            }
        }
        return input;
    }
    /** Checks if a string is a number
     * @param str string that is evaluated
     * @return true if the string is a number, false if not
    */
    private static boolean isNumeric(String str) {
        try 
        {
            Integer.parseInt(str);
            return true;
        } 
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    /** A code that is used to find the index for an entry given the hashcode index and handles collisions and re-indexing 
    */
    private int linearProbe(int index, K key)
    {
        if(hashTable[index] != null)
        {   collisions++;}
        boolean found = false;
        int availableStateIndex = -1;
        while (!found && (hashTable[index] !=null))
        {
            if(hashTable[index] != AVAILABLE)
            {
                if(key.equals(hashTable[index].getKey()))
                    found = true;
                else
                    index = (index + 1)%hashTable.length;
            }
            else
            {
                if(availableStateIndex == -1)
                    availableStateIndex = index;
                index = (index + 1) % hashTable.length;
            }
        }
        if (found || (availableStateIndex ==-1))
            return index;
        else
            return availableStateIndex;
    }
    /** A function that is used to locate the index of key in the instance that it was moved to a different index due to a collision*/
    private int locate(int index, K key) {
        boolean found = false;
        while (!found && (hashTable[index] != null)) {
            if (key.equals(hashTable[index].getKey()))
                found = true; // Key found
            else // Follow probe sequence
                index = (index + 1) % hashTable.length; // Linear probing
        } 
        int result = -1;
        if (found)
            result = index;
        return result;
    }
}
