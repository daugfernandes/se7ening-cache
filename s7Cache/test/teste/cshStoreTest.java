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
 * Unit teste for cshStore
 * @author David Fernandes
 */
public class cshStoreTest {

    /**
     *
     */
    public cshStoreTest() {
    }

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test set/get of a bunch of cshItems. Creates a big store and test-get some random keys.
     */
    @Test
    public void testRandomGet() {

        int number_of_elements = 1000000;
        int number_of_tests = 200000;

        System.out.println("testeRandomGet");
        System.out.println("==============");
        System.out.printf("%d %s\n",number_of_elements, "elementos");
        System.out.printf("%d %s\n",number_of_tests, "testes");

        // no limit testes
        // every add with result in a, de facto, add
        cshStore cs=new cshStore(number_of_elements, 0);

        // autogen 10000000 strings
        for(int i=0;i<number_of_elements;i++) {
            // add_no_test doesn't verify pre-existance of a given key
            // add, even is there exist one
            cs.add_no_test("a-".concat(Integer.toString(i)),"iuyiuyiuyiuyiuyiuy:".concat(Integer.toString(i)),10);
        }

        // aply sorting to the _indexes List
        // in order to use binary search
        cs.sort();

        String s0="";
        String sV="";
        Object o;

        java.util.Random gen = new java.util.Random();

        for(int k = 0; k < number_of_tests ; k++) {
            double r = gen.nextDouble() * number_of_elements;

            // select a random element's key
            s0=cs.getItems().get((int)r).Key();

            // ... and the corresponding value
            sV=((String)cs.getItems().get((int)r).Value());
            
            // try to get the value of that key
            o = cs.get(s0);

            // teste for NULL; should not be null
            assertNotNull(o);

            // teste for not equal; should be equal
            assert(!o.equals(sV));
        }
    }

}