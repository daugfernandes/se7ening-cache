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
public class cshStore {

    private int _capacity;
    private int _maxsize;
    private int _actualsize;

    private java.util.List<cshIndex> _index;
    private java.util.List<cshItem> _items;

    public cshStore(int p_capacity, int p_maxsize) {
        _capacity = p_capacity;
        _maxsize = p_maxsize;
        _actualsize = 0;

        _index = new java.util.ArrayList<cshIndex>();
        _items = new java.util.ArrayList<cshItem>();
    }

    public Object get(String p_key) {
        int i=get_index(p_key.hashCode());
        if(i==-1)
            return null;
        else
            return _items.get(_index.get(i).Index()).Value();
    }

    private int get_index(int p_hashcode) {

        for(int i=0;i<_index.size();i++) {
            if(_index.get(i).Hash()==p_hashcode)
                return i;
        }
        return -1;
    }

    public void add(String p_key, Object p_value, int p_size) {
        int hc = p_key.hashCode();
        int i = get_index(hc);
        if(i==-1) {
            _items.add(new cshItem(p_key, p_value, p_size));
            _index.add(new cshIndex(_index.size(), hc));
        } else {
            int j = _index.get(i).Index();
            _actualsize -= _items.get(j).Size();
            _items.remove(j);
            _index.remove(i);
            add_no_test(p_key,p_value,p_size);
        }
    }

    private void add_no_test(String p_key, Object p_value, int p_size) {
        _items.add(new cshItem(p_key, p_value, p_size));
        _index.add(new cshIndex(_index.size(), p_key.hashCode()));
    }

}
