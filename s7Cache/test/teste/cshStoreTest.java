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

    static tmtEvent _life;

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
        System.out.println("setUpClass");
        _life = new tmtEvent("cshStoreTest", null);
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("tearDownClass");

        _life.Stop();
        System.out.println(_life.toString());
    }

    /**
     *
     */
    @Before
    public void setUp() {
        System.out.println("setUp");
        
        _life.addEvent(new tmtEvent("setUp",null));

    }

    /**
     *
     */
    @After
    public void tearDown() {
        System.out.println("tearDown");

        _life.Stop();
    }

    /**
     * Test set/get of a bunch of cshItems. Creates a big store and test-get some random keys.
     */
    @Test
    public void testRandomGet() {

        tmtEvent thisevent=_life.LastEvent();

        thisevent.addEvent(new tmtEvent("testRandomGet", null));

        int number_of_elements = 2000000;
        int number_of_tests = 200000;

        System.out.println("testeRandomGet");
        System.out.println("==============");
        System.out.printf("%d %s\n",number_of_elements, "elements");
        System.out.printf("%d %s\n",number_of_tests, "tests");

        // no limit tests
        // every add will result in a, de facto, add
        cshStore cs=new cshStore(number_of_elements, 0);

        thisevent.LastEvent().addEvent(new tmtEvent("add 1000000 string",null));

        // autogen 10000000 strings
        for(int i=0;i<number_of_elements;i++) {
            // add_no_test doesn't verify pre-existance of a given key
            // add, even is there exists one
            String newKey="a-".concat(Integer.toString(i));
            Object newObject="iuyiuyiuyiuyiuyiuy:".concat(Integer.toString(i));
            cs.add_no_test(newKey, newObject,10);
        }

        thisevent.LastEvent().StopLastEvent();

        // aply sorting to the _indexes List
        // in order to use binary search
        cs.sort();

        java.util.Random gen = new java.util.Random();

        thisevent.LastEvent().addEvent(new tmtEvent("get ".concat(Integer.toString(number_of_tests).concat(" elements")), gen));

        for(int k = 0; k < number_of_tests ; k++) {
            double r = gen.nextDouble() * number_of_elements;

            // select a random element's key
            String s0=cs.getItems().get((int)r).Key();

            // ... and the corresponding value
            String sV=((String)cs.getItems().get((int)r).Value());
            
            // try to get the value of that key
            Object o = cs.get(s0);

            // teste for NULL; should not be null
            assertNotNull(o);

            // teste for equal; should be equal
            assertEquals(o.equals(sV), true);
        }
        
        thisevent.LastEvent().StopLastEvent();
        thisevent.StopLastEvent();

    }

}