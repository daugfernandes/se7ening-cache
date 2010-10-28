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

    teste::cshIndex.java

 */
/**
 * An index class for linked list management
 * @author David Fernandes
 */
public class cshIndex implements Comparable<cshIndex> {

    private int _i;
    private int _hashcode;

    /**
     * Constructor
     * @param p_i Pointing index to a linked list
     * @param p_hash HashCode for sorting. Refer to compareTo method.
     */
    public cshIndex(int p_i, int p_hash) {
        _i = p_i;
        _hashcode = p_hash;
    }

    /**
     * Selector
     * @return
     */
    public int Hash() {
        return _hashcode;
    }

    /**
     * Selector
     * @return
     */
    public int Index() {
        return _i;
    }

    /**
     * Compare function needed for Collection.sort
     * @param o
     * @return -1 if ASC, 1 for DESC, 0 for EQUAL
     */
    public int compareTo(cshIndex o) {
        if(this._hashcode<o._hashcode) return -1;
        if(this._hashcode>o._hashcode) return 1;
        return 0;
    }
}
