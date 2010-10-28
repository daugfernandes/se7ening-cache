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

    teste::cshItem.java

 */
/**
 *
 * @author david
 */
public class cshItem {

    private int _hashcode;
    private String _key;
    private Object _value;
    private int _size;
    private int _hits;
    private long _expiration;

    cshItem(String p_key, Object p_value, int p_size, int p_expire) {

        _hashcode = p_key.hashCode();
        _value = p_value;
        _size = p_size;
        _key = p_key;
        _hits = 0;

        // if 0, never expires => one year
        if(p_expire==0) p_expire=60*60*24*365;

        _expiration=System.currentTimeMillis()+1000*p_expire;
    }

    /**
     * Selector
     * @return The hashcode of the Item.
     */
    public int Hash() {
        return _hashcode;
    }

    /**
     * Selector
     * @return The key of the Item.
     */
    public String Key() {
        return _key;
    }

    /**
     * Selector
     * @return The value of the Item.
     */
    public Object Value() {
        return _value;
    }

    /**
     * Selector
     * @return Size of the Item's value.
     */
    public int Size() {
        return _size;
    }

    /**
     * Selector for the number of successfull hits.
     * @return
     */
    public int Hits() {
        return _hits;
    }

    /**
     * Selector
     * @return Expiration date/time in absolute milliseconds.
     */
    public long Expire() {
        return _expiration;
    }
    /**
     * Increments the hit counter.
     */
    protected void Hit() {
        _hits += 1;
    }
}
