import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

    private T ds;

    protected abstract T createInstance();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {}

    @AfterAll
    static void tearDownAfterClass() throws Exception {}

    @BeforeEach
    void setUp() throws Exception {
        ds = createInstance();
    }

    @AfterEach
    void tearDown() throws Exception {
        ds = null;
    }

    // tests if empty ds has size 0
    @Test
    void test00_empty_ds_size() {
        if (ds.size() != 0)
            fail("data structure should be empty, with size=0, but size=" + ds.size());
    }

    // tests if size=1 after a single insert
    @Test
    void test01_insert_one() {
        try {
            String key = "1";
            String value = "one";
            ds.insert(key, value);
            assert (ds.size() == 1);
            if (ds.size() != 1) {
                fail("data structure size should be one, actually size=" + ds.size());
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException");
        }

    }

    // tests if size=0 after a single insert and remove
    @Test
    void test02_insert_remove_one_size_0() {
        try {
            String key = "1";
            String value = "one";
            ds.insert(key, value);
            assert (ds.remove(key));
            if (ds.size() != 0) {
                fail("data structure should be empty, with size=0, but size=" + ds.size());
            } else if (ds.contains(key)) {
                fail("data structure still contains key after remove");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException");
        }
    }


    // tests weather an exception is thrown when a duplicate is inserted
    @Test
    void test03_duplicate_exception_thrown() {
        try {
            String key = "1";
            String value = "one";
            ds.insert("1", "one");
            ds.insert("2", "two");
            try {
                ds.insert(key, value);
                fail("duplicate exception not thrown");
            } catch (RuntimeException re) {
            }
            assert (ds.size() == 2);
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if remove returns false when expected
    @Test
    void test04_remove_returns_false_when_key_not_present() {
        try {
            String key = "1";
            String value = "one";
            ds.insert(key, value);
            if (ds.remove("2")) {// remove non existing key
                fail("remove non existent key did not return false. Should have returned false.");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if insert returns true when expected
    @Test
    void test05_insert_remove_one() {
        try {
            String key = "1";
            String value = "one";
            ds.insert(key, value);
            if (!ds.remove("1")) {// remove existing key
                fail("remove existing key returned false. Should have returned true.");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests size after multiple inputs
    @Test
    void test06_insert_many_size() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            ds.insert("3", "three");
            ds.insert("4", "four");
            ds.insert("5", "five");
            ds.insert("6", "six");
            ds.insert("7", "seven");
            if (ds.size() != 7) {
                fail("size doesn't equal seven, actual size=" + ds.size());
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if a duplicate can be put in after the first copy was removed
    @Test
    void test07_no_duplicates() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            ds.insert("3", "three");
            ds.remove("3");
            ds.insert("4", "four");
            ds.insert("5", "five");
            ds.insert("6", "six");
            ds.insert("7", "seven");

            if (ds.size() != 6) {
                fail("size should be 6, size=" + ds.size());
            }

            try {
                ds.insert("3", "three");
            } catch (RuntimeException re) {
                fail("Duplicate exception thrown where it shouldn't.");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if key insert will throw the expected exception
    @Test
    void test08_key_null() {
        try {
            ds.insert(null, "one");
            fail("insert didn't throw an illegal argument exception with key=null");
        } catch (IllegalArgumentException e) {
        }
    }

    // tests if value can be null
    @Test
    void test09_value_null() {
        try {
            ds.insert("1", null);
        } catch (IllegalArgumentException e) {
            fail("insert threw an illegal argument exception with value=null");
        } catch (RuntimeException e) {
            fail("Program sholdn't throw RuntimeException");
        }
    }

    // tests if the array will grow properly
    @Test
    void test10_will_grow() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            ds.insert("3", "three");;
            ds.insert("4", "four");
            ds.insert("5", "five");
            ds.insert("6", "six");
            ds.insert("7", "seven");
            ds.insert("8", "one");
            ds.insert("9", "two");
            ds.insert("10", "three");;
            ds.insert("11", "four");
            ds.insert("12", "five");
            ds.insert("13", "six");
            ds.insert("14", "seven");
            ds.insert("15", "seven");
            ds.insert("16", "one");
            ds.insert("17", "two");
            ds.insert("18", "three");;
            ds.insert("19", "four");
            ds.insert("20", "five");
            ds.insert("21", "six");
            ds.insert("22", "seven");
            if (ds.size() != 22) {
                fail("size should be 22, size=" + ds.size());
            } else if (!ds.contains("13")) {
                fail("data structure lost data while growing, key=13");
            } else if (!ds.contains("2")) {
                fail("data structure lost data while growing, key=2");
            } else if (!ds.contains("22")) {
                fail("data structure lost data while growing, key=22");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if remove throws the expected exception
    @Test
    void test11_remove_null_exception() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            try {
                ds.remove(null);
                fail("remove didn't throw exception when given key=null");
            } catch (IllegalArgumentException e) {

            } catch (NullPointerException e) {
                fail("shouldn't throw NullPointerExcpetion, should throw IllegalArgumentException");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if get throws the expected exception
    @Test
    void test12_get_null_exception() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            try {
                ds.get(null);
                fail("get didn't throw exception when given key=null, returns: " + ds.get(null));
            } catch (IllegalArgumentException e) {
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }


    // tests if gift returns null when expected
    @Test
    void test13_get_cant_find() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            if (ds.get("3") != null) {
                fail("get doesn't return null when it cannot find key, returns: " + ds.get("3"));
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if get returns the correct value
    @Test
    void test14_get_returns_value() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            if (!ds.get("1").equals("one")) {
                fail("get does not return the correct value, key=1 value=" + ds.get("1"));
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if contains returns true or false when expected
    @Test
    void test15_finds_key_true() {
        try {
            ds.insert("1", "one");
            ds.insert("2", "two");
            if (!ds.contains("1")) {
                fail("contains returns false when key=1 exists");
            } else if (ds.contains("3")) {
                fail("contains returns true when key=3 doesn't exist");
            } else if (ds.contains(null)) {
                fail("contains returns true when key=null");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests if the data structure can store 1000 elements
    @Test
    void test16_large_insert() {
        try {
            String value = "constant";
            int limit = 1000;

            for (Integer i = 0; i < limit; ++i) {
                ds.insert(Integer.toString(i), value);
            }
            if (ds.size() != 1000) {
                fail("size should be 10, size=" + ds.size());
            } else if (!ds.contains("589")) {
                fail("data structure is missing keys, key=549");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests a larger number of removes to push the program
    @Test
    void test17_many_removes() {
        try {
            int limit = 15;
            String value = "constant";

            for (Integer i = 0; i < limit; ++i) {
                ds.insert(Integer.toString(i), value);
            }
            for (Integer i = 0; i < limit; ++i) {
                ds.remove(Integer.toString(i));
            }

            if (ds.size() != 0) {
                fail("size should be zero, size=" + ds.size());
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // tests the insertion and removal of 1000 elements
    @Test
    void test18_all_in_all_out() {
        try {
            String value = "constant";
            int limit = 1000;

            for (Integer i = 0; i < limit; ++i) {
                ds.insert(Integer.toString(i), value);
            }
            for (Integer i = 0; i < limit; ++i) {
                ds.remove(Integer.toString(i));
            }
            if (ds.size() != 0) {
                fail("size should be 0, size=" + ds.size());
            }
            for (Integer i = 0; i < limit; ++i) {
                if (ds.contains(Integer.toString(i))) {
                    fail("should not contain any keys key=" + i);
                }
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

    // test for many different commands at once
    @Test
    void test19_too_many_cmds() {
        try {
            String value = "constant";
            int limit = 100;

            for (Integer i = 0; i < limit; ++i) {
                ds.insert(Integer.toString(i), value);
            }

            for (Integer i = 5; i < 25; ++i) {
                if (!ds.remove(Integer.toString(i))) {
                    fail("remove returns false for key=" + i);
                }
            }
            for (Integer i = 52; i < 82; ++i) {
                if (!ds.remove(Integer.toString(i))) {
                    fail("remove returns false for key=" + i);
                }
            }

            if (ds.size() != 50) {
                fail("Size should be 50, size=" + ds.size());
            } else if (ds.contains("64")) {
                fail("data structure contains key=64 when it should be removed");
            } else if (!ds.contains("49")) {
                fail("data structure doesnt conaitn key=49 when it should exist");
            } else if (!ds.get("1").equals("constant")) {
                fail("data structure key=1, get doesn't return constant");
            }
        } catch (RuntimeException e) {
            fail("Program should not throw RuntimeException at this point");
        }
    }

}
