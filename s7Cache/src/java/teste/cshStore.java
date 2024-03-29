/*
 *
 *     Copyright (C) 2010  David Fernandes
 *
 *                         Rua da Quinta Amarela, 60
 *                         4475-663 MAIA
 *                         PORTUGAL
 *
 *                         <daugfernandes@aim.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package teste;

/*

    teste::cshStore.java

 */
/**
 * The cache storage
 * @author David Fernandes
 */
public class cshStore {

    // TODO: onAdd check if there is space and/or capacity

    int _capacity = 0;
    int _maxSize = 0;
    int _actualSize = 0;
    boolean _indexSorted = true;
    int _totalGets = 0;
    int _totalHits = 0;
    int _sizeRemoved = 0;

    // register Life
    private tmtEvent _life;

    private java.util.List<cshIndex> _index;
    private java.util.List<cshItem> _items;

    /**
     * Constructor
     * @param p_capacity Maximum number of elements supported
     * @param p_maxsize Maximum total capacity of the store (in bytes)
     */
    public cshStore(int p_capacity, int p_maxsize) {
        _capacity = p_capacity;
        _maxSize = p_maxsize;
        _actualSize = 0;
        _totalGets = 0;
        _totalHits = 0;
        _sizeRemoved = 0;

        _index = new java.util.ArrayList<cshIndex>();
        _items = new java.util.ArrayList<cshItem>();

        _life = new tmtEvent("cshStore starting", null);
    }

    /**
     * Selector
     * @return Indexes list.
     */
    public java.util.List<cshIndex> getIndexes() {
        return _index;
    }

/**
     * Selector
     * @return Items list.
     */
    public java.util.List<cshItem> getItems() {
        return _items;
    }

    /**
     * Try to get the Item with the specified key
     * @param p_key Key to get
     * @return cshItem
     */
    public Object get(String p_key) {

        // for activity mesaure

        _totalGets += 1;

        // event logging

        _life.addEvent(new tmtEvent("get:".concat(p_key), null));

        // search de Indexes
        
        int i=get_index(_index, p_key.hashCode());

        _life.LastEvent().Stop();
        
        if(i==-1)
            return null;
        else {

            // the index was foundm get's it's corresponding Item

            cshItem csI = _items.get(_index.get(i).Index());
            
            // increments Item's number of hits

            csI.Hit();

            _totalHits += 1;

            return csI;
        }
    }
    
    /**
     * Selector.
     * @return A tmtEvent with the 'life' of the Store
     */
    public tmtEvent Life() {
        return _life;
    }
    
    /**
     * Try to get the Index for a specified hashcode. Uses a binary recursive
     * search on cshIndex[] that should be ordered by HashCode.
     * @param l List of cshIndex's.
     * @param p_hashcode Hashcode to find.
     * @return Index poiting to the list of cshItem's.
     */
    private int get_index(java.util.List<cshIndex> l, int p_hashcode) {

        // there's nothing to do wwith a null list

        if(l.size() == 0) return -1;

        int ls = l.size();

        // one element one ([0]) it's it ou it isn't it :O

        if(ls == 1) return l.get(0).Hash()==p_hashcode ? 0 : -1;

        // splits the list in half (sort of)

        int mid = ls/2;
        int k;

        // tries the splitter. if so, good, that's it, that's done.

        if(p_hashcode == l.get(mid).Hash()) return mid;

        // select left or right side of the list

        if(p_hashcode<l.get(mid).Hash()) {

            k = get_index(l.subList(0, mid), p_hashcode);
            
            // there is no need to test the case k == -1 as the return would
            // be the same
            //               if(k==-1) return -1;

            return k;

        } else {

            k = get_index(l.subList(mid, ls), p_hashcode);
            if(k==-1) return -1;

            // when selected the right side, the sublist indexing should be taken
            // as relative to the index of the splitting

            return mid+k;
        }
    }

    /**
     * Sorts the Indexes list.
     */
    public void sort() {
        // log event for debug purpose only
        _life.addEvent(new tmtEvent("sort",null));
        
        // uses java build in Collections.sort method
        // please refer to csgIndex class for the @Overrided compareTo method
        java.util.Collections.sort(_index);
        _indexSorted = true;

        // log event for debug purpose only
        _life.LastEvent().Stop();
    }

    /**
     * Adds a new object to the cache with pre-existance validation, e.g., if an
     * object with the same kay already exists, that will be removed and this
     * new one should be addedd to the end of the cache.
     * @param p_key Key of the object to add.
     * @param p_value Object to add.
     * @param p_size Estimated, if not known, size of the Object (bytes).
     * @param p_expire_in_seconds Relative time of expiration in seconds.
     */
    public void add(String p_key, Object p_value, int p_size,
                    int p_expire_in_seconds) {

        int hc = p_key.hashCode();
        int i = get_index(_index, hc);

        // test for existance. If so, the element will be replaced

        if(i > -1) {

            // TODO: test for max_size

            int j = _index.get(i).Index();
            int sz = _items.get(j).Size();

            _actualSize += p_size - sz;
            _sizeRemoved += sz;
            
            cshItem item = _items.get(j);
            item.setSize(p_size);
            item.setValue(p_value);

            _indexSorted = false;

        } else {

            // _maxSize will be exceded
            if(_maxSize<_actualSize+p_size)
                while(_index.size()>0 && _maxSize<_actualSize+p_size)
                    remove(0);
            else
                // capacity exceded
                if(_items.size()>0 && _capacity <= _items.size())
                    remove(_index.size()-1);

            add_no_test(p_key, p_value, p_size, p_expire_in_seconds);

            // as new elements are always added to the end of the Indexes list
            // we try here to avoid the sort operation when the hashcode of the
            // new element is greater than the hashcode of the last element on
            // the list, which is the greatest hashcode existing.

            if(_index.size() > 1 && hc < _index.get(_index.size() - 2).Hash())
                sort();
        }
    }

    public boolean remove(String p_key) {

        int hc = p_key.hashCode();
        int i = get_index(_index, hc);

        if(i > -1)
            return remove(i);
        else
            return false;
    }


    public boolean remove(int p_index_index) {

        if(p_index_index > -1) {

            int j = _index.get(p_index_index).Index();
            _actualSize -= _items.get(j).Size();

            _items.remove(j);
            _index.remove(p_index_index);

            // only readjust indexes if the removed index isn't the last one
            if(p_index_index<_index.size())
                for (cshIndex cI : _index)
                    if (cI.Index() > j) cI.setIndex(cI.Index()-1);

            return true;

        } else
            return false;
    }

    /**
     * Adds a new object to the cache with no pre-existance validation. Doesn't
     * matter if an object with the same kay already exists; there will be 2.
     * @param p_key Key of the object to add.
     * @param p_value Object to add.
     * @param p_size Estimated, if not known, size of the Object (bytes).
     * @param p_expire_in_seconds Relative time of expiration in seconds.
     */
    public void add_no_test(String p_key, Object p_value, int p_size, 
                            int p_expire_in_seconds) {

        _items.add(new cshItem(p_key, p_value, p_size, p_expire_in_seconds));
        _index.add(new cshIndex(_index.size(), p_key.hashCode()));
        _actualSize += p_size;
        _indexSorted = false;

    }

}
