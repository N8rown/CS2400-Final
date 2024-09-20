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
public interface DictionaryInterface<K, V> {
    public V add(K key, V value);
    public V remove(K key);
    public V getValue(K key);
    public boolean contains(K key);

    public Iterator<K> getKeyIterator();
    public Iterator<V> getValueIterator();
    public boolean isEmpty();
    public int getSize();
    public int getEntries();
    public void clear();
    public int getCollisionCount();

}
