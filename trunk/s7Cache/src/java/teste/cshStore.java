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

    private int _capacity=0;
    private int _maxsize=0;
    private int _actualsize=0;
    private boolean _indexSorted=true;

    private java.util.List<cshIndex> _index;
    private java.util.List<cshItem> _items;

    /**
     * Constructor
     * @param p_capacity Maximum number of elements supported
     * @param p_maxsize Maximum total capacity of the store (in bytes)
     */
    public cshStore(int p_capacity, int p_maxsize) {
        _capacity = p_capacity;
        _maxsize = p_maxsize;
        _actualsize = 0;

        _index = new java.util.ArrayList<cshIndex>();
        _items = new java.util.ArrayList<cshItem>();
    }

    /**
     * Geter of the indexes list
     * @return List<cshIndexes)
     */
    public java.util.List<cshIndex> getIndexes() {
        return _index;
    }

/**
     * Geter of the items list
     * @return List<cshItems)
     */
    public java.util.List<cshItem> getItems() {
        return _items;
    }

    /**
     * Try to get the Item with teh specified key
     * @param p_key Key to get
     * @return cshItem
     */
    public Object get(String p_key) {
        int i=get_index(_index, p_key.hashCode());
        if(i==-1)
            return null;
        else
            return _items.get(_index.get(i).Index()).Value();
    }

    /**
     * Try to get the Idnex for a specified hashcode. Uses a binary recursive search.
     * @param l
     * @param p_hashcode
     * @return Index for the Items list
     */
    private int get_index(java.util.List<cshIndex> l, int p_hashcode) {

        int ls = l.size();

        if(ls == 0) return -1;

        if(ls == 1) {
            return l.get(0).Hash()==p_hashcode ? 0 : -1;
        }

        int mid = ls/2;
        int k;

        if(p_hashcode == l.get(mid).Hash()) return mid;

        if(p_hashcode<l.get(mid).Hash())
        {
            k = get_index(l.subList(0, mid), p_hashcode);
            if(k==-1) return -1;
            return k;
        } else {
            k = get_index(l.subList(mid, ls), p_hashcode);
            if(k==-1) return -1;
            return mid+k;
        }
    }

    /**
     * Sorts the Indexes list
     */
    public void sort() {
         java.util.Collections.sort(_index);
    }

    /**
     *
     * @param p_key
     * @param p_value
     * @param p_size
     */
    public void add(String p_key, Object p_value, int p_size) {

        int hc = p_key.hashCode();
        int i = get_index(_index, hc);

        if(i>-1) {
            int j = _index.get(i).Index();
            _actualsize -= _items.get(j).Size();
            _items.remove(j);
            _index.remove(i);
        }

        add_no_test(p_key,p_value,p_size);

        if(_index.size()>1 && hc < _index.get(_index.size()-2).Hash())
            sort();
    }

    /**
     *
     * @param p_key
     * @param p_value
     * @param p_size
     */
    public void add_no_test(String p_key, Object p_value, int p_size) {
        _items.add(new cshItem(p_key, p_value, p_size));
        _index.add(new cshIndex(_index.size(), p_key.hashCode()));
        _actualsize += p_size;
    }

}
