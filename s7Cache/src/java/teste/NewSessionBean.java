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

import javax.ejb.Singleton;

/**
 *
 * @author david
 */
@Singleton
public class NewSessionBean implements NewSessionBeanRemote, NewSessionBeanLocal {

    private cshStore _store;

    public NewSessionBean() {
        _store = new cshStore(10, 2000);
    }
    
    public Object Get(String p_key) {
        return _store.get(p_key);
    }

    public void Add(String p_key, Object p_value, int p_size) {
        _store.add(p_key, p_value, p_size);
    }
 
}
