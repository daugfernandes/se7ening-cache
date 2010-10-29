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
public class cshItem implements Comparable<cshItem> {

    private int _hashcode;
    private String _key;
    private Object _value;
    private int _size;
    private int _hits;
    private long _expiration;
    private long _born;

    cshItem(String p_key, Object p_value, int p_size, int p_expire) {

        _key = p_key;
        _hashcode = p_key.hashCode();
        _born = System.currentTimeMillis();

        setValue(p_value);
        setSize(p_size);
        setExpire(p_expire);

        _hits = 0;

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
     * @return The object in store.
     */
    public Object Value() {
        return _value;
    }

    /**
     * Modifier
     * @param p_value Object to store.
     */
    public void setValue(Object p_value) {
        _value = p_value;
        _hits = 0;
    }

    /**
     * Selector
     * @return Size of object.
     */
    public int Size() {
        return _size;
    }

    /**
     * Modifier
     * @param p_size Size of object.
     */
    public void setSize(int p_size) {
        _size = p_size;
    }

    /**
     * Selector
     * @return Number og successfull hits.
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
     * Modifier.
     * @param p_expire_in_seconds Total time (in seconds) to expiration. Zero if never.
     */
    public void setExpire(int p_expire_in_seconds) {
        // if 0, never expires => one year
        if(p_expire_in_seconds==0) p_expire_in_seconds=60*60*24*365;
        _expiration=System.currentTimeMillis()+1000*p_expire_in_seconds;
    }

    /**
     * Increments the hit counter.
     */
    protected void Hit() {
        _hits += 1;
    }
   /**
     * Compare function needed for Collection.sort
     * @param o
     * @return -1 if ASC, 1 for DESC, 0 for EQUAL
     */
    public int compareTo(cshItem o) {
        if(this.Hits()>o.Hits())
            return -1;
        else
            if(this.Hits()<o.Hits())
                return 1;
            else
                if(this._born>o._born)
                    return -1;
                else
                    if(this._born<o._born)
                        return 1;
                    else
                        return 0;
    }
}
