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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author david
 */
public class cshStoreTest {

    public cshStoreTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class cshStore.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        String p_key = "a1";
        cshStore instance = new cshStore(100, 2000);
        Object result = instance.get(p_key);
        assertEquals(result, null);
    }

    /**
     * Test of add method, of class cshStore.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        cshStore instance = new cshStore(10, 2000);

        instance.add("a1", "david", 5);
        instance.add("a2", "davidfernandes", 14);

        Object result = instance.get("a1");
        assert(result==null );

    }

}