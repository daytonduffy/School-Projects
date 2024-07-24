//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: TestAdventureStory
// Files: AdventureStory.java TestAdventureStory.java
// Course: CS 200 Spring 2019
//
// Author: Marc Renault
// Email: mrenault@cs.wisc.edu
// Lecturer's Name: self
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

/**
 * This class contains a few methods for testing methods in the AdventureStory class as they are
 * developed. These methods are private since they are only intended for use within this class.
 * 
 * @author Marc Renault
 * @author Dayton Duffy
 *
 */
public class TestAdventureStory {

    /**
     * This is the main method that runs the various tests.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {
        // Milestone 1 Tests
        // testPromptInt();
        // testPromptChar();
        // testPromptString();
        // testGetRoomIndex();
        // testGetRoomDetails();

        // Milestone 2 Tests
        // testParseStory();

        // Milestone 3 Tests
        testParseBookmark();
        // testProbTrans();
    }

    /**
     * This runs some tests on the promptInt method.
     */
    private static void testPromptInt() {
        boolean error = false;

        {// tests generaly
            Scanner in = new Scanner("8\n");
            int expected = 8;
            int result = AdventureStory.promptInt(in, "Enter integer: ", 5, 15);
            if (expected != result) {
                System.out.println("1) testPromptInt expected: " + expected + " result: " + result);
                error = true;
            }
        }

        {// tests maximum is included
            Scanner in = new Scanner("8\n");
            int expected = 8;
            int result = AdventureStory.promptInt(in, "Enter integer: ", 5, 8);
            if (expected != result) {
                System.out.println("2) testPromptInt expected: " + expected + " result: " + result);
                error = true;
            }
        }

        if (error) {
            System.out.println("testPromptInt failed");
        } else {
            System.out.println("testPromptInt passed");
        }
    }

    /**
     * This runs some tests on the promptString method.
     */
    private static void testPromptString() {
        boolean error = false;

        {
            Scanner in = new Scanner("foobar\n");
            String expected = "foobar";
            String result = AdventureStory.promptString(in, "Enter: ");
            if (!expected.equals(result)) {
                System.out
                    .println("1) testPromptString expected: " + expected + " result: " + result);
                error = true;
            }
        }

        {// tests if whitespace is ignored on edges
            Scanner in = new Scanner("    fo  oba r   ");
            String expected = "fo  oba r";
            String result = AdventureStory.promptString(in, "Enter: ");
            if (!expected.equals(result)) {
                System.out
                    .println("2) testPromptString expected: " + expected + " result: " + result);
                error = true;
            }
        }

        if (error) {
            System.out.println("testPromptString failed");
        } else {
            System.out.println("testPromptString passed");
        }
    }

    /**
     * This runs some tests on the promptChar method.
     */
    private static void testPromptChar() {
        boolean error = false;

        {
            Scanner in = new Scanner("  foobar\n");
            char expected = 'f';
            char result = AdventureStory.promptChar(in, "Enter: ");
            if (expected != result) {
                System.out
                    .println("1) testPromptChar expected: " + expected + " result: " + result);
                error = true;
            }
        }


        {// tests trimming and lower case forcing
            Scanner in = new Scanner("  FeR  Abc");
            char expected = 'f';
            char result = AdventureStory.promptChar(in, "Enter: ");
            if (expected != result) {
                System.out
                    .println("2) testPromptChar expected: " + expected + " result: " + result);
                error = true;
            }
        }

        if (error) {
            System.out.println("testPromptChar failed");
        } else {
            System.out.println("testPromptChar passed");
        }
    }

    /**
     * This runs some tests on the getRoomIndex method.
     */
    private static void testGetRoomIndex() {
        boolean error = false;

        {// tests for -1 returned if not there
         // Assuming normal Config.java
            ArrayList<String[]> in =
                new ArrayList<>(Arrays.asList(new String[][] {{"id1", "", ""}, {"id2", "", ""}}));
            int expected = -1;
            int result = AdventureStory.getRoomIndex(new String("id3"), in);
            if (expected != result) {
                System.out
                    .println("1) testGetRoomIndex expected: " + expected + " result: " + result);
                error = true;
            }
        }

        {// tests that the right index is returned
         // Assuming normal Config.java
            ArrayList<String[]> in =
                new ArrayList<>(Arrays.asList(new String[][] {{"id1", "", ""}, {"id2", "", ""}}));
            int expected = 1;
            int result = AdventureStory.getRoomIndex(new String("id2"), in);
            if (expected != result) {
                System.out
                    .println("2) testGetRoomIndex expected: " + expected + " result: " + result);
                error = true;
            }
        }

        if (error) {
            System.out.println("testGetRoomIndex failed");
        } else {
            System.out.println("testGetRoomIndex passed");
        }
    }

    /**
     * This runs some tests on the getRoomIndex method.
     */
    private static void testGetRoomDetails() {
        boolean error = false;

        {// tests working as expected
         // Assuming normal Config.java
            ArrayList<String[]> in =
                new ArrayList<>(Arrays.asList(new String[][] {{"id1", "", ""}, {"id2", "", ""}}));
            String[] expected = in.get(0);
            String[] result = AdventureStory.getRoomDetails(new String("id1"), in);
            if (!Arrays.equals(expected, result)) {
                System.out.println("1) testGetRoomDetails expected: " + Arrays.toString(expected)
                    + " result: " + Arrays.toString(result));
                error = true;
            }
        }
        {// tests null case
         // Assuming normal Config.java
            ArrayList<String[]> in =
                new ArrayList<>(Arrays.asList(new String[][] {{"id1", "", ""}, {"id2", "", ""}}));
            String[] expected = null;
            String[] result = AdventureStory.getRoomDetails(new String("id3"), in);
            if (!Arrays.equals(expected, result)) {
                System.out.println("2) testGetRoomDetails expected: " + Arrays.toString(expected)
                    + " result: " + Arrays.toString(result));
                error = true;
            }
        }

        if (error) {
            System.out.println("testGetRoomDetails failed");
        } else {
            System.out.println("testGetRoomDetails passed");
        }
    }

    /**
     * This runs some tests on the parseStory method.
     */
    private static void testParseStory() {
        boolean error = false;

        {
            Scanner testSc = new Scanner("#!STORY\n"
                + "R1: Room 1\nRoom 1 description\n;;;\n: Transition 1 -> 1\n: Transition 2 -> 2\n"
                + "R2: Room 2 \n Room 2 description \n;;;\n =) \n");
            ArrayList<String[]> arrRooms = new ArrayList<String[]>();
            ArrayList<ArrayList<String[]>> arrTrans = new ArrayList<ArrayList<String[]>>();
            String[] curRoom = new String[1];
            boolean passed = true;
            if (!AdventureStory.parseStory(testSc, arrRooms, arrTrans, curRoom)) {
                System.out.println("parseStory 1: returned false instead of true.");
                passed = false;
            }
            // Assuming normal Config.java
            // Expected ArrayList of room details:
            ArrayList<String[]> expRooms = new ArrayList<String[]>(Arrays.asList(new String[][] {
                {"1", "Room 1", "Room 1 description"}, {"2", "Room 2", "Room 2 description"}}));
            if (!compareArrayListsArrays(arrRooms, expRooms)) {
                System.out.println("parseStory 1: \nrooms ArrayList returned: \n"
                    + Arrays.deepToString(arrRooms.toArray()) + "\nExpected: \n"
                    + Arrays.deepToString(expRooms.toArray()) + "\n");
                passed = false;
            }
            // Expected ArrayList of ArrayList of transition details:
            ArrayList<String[]> room1Trans = new ArrayList<String[]>(Arrays
                .asList(new String[][] {{"Transition 1", "1", null}, {"Transition 2", "2", null}}));
            ArrayList<String[]> room2Trans =
                new ArrayList<String[]>(Arrays.asList(new String[][] {{"=)", null, null}}));
            ArrayList<ArrayList<String[]>> expTrans = new ArrayList<ArrayList<String[]>>();
            expTrans.add(room1Trans);
            expTrans.add(room2Trans);
            if (!compare2dArrayLists(arrTrans, expTrans)) {
                System.out.println("parseStory 1: \ntransition ArrayList returned: \n"
                    + toString2dArrayLists(arrTrans) + "\nExpected: \n"
                    + toString2dArrayLists(expTrans) + "\n");
                passed = false;
            }
            if (passed)
                System.out.println("parseStory 1: passed");

        }
        {
            boolean passed = true;

            String[] story1Array = {"#!STORY", "# Eroneous comment", "# And another, good sir!",
                "# And yet a third.", "R1: Home", "Home is where the heart is...", ";;;",
                ": Stay home -> 4", ": Visit market -> 2", "R2: Deep Sea", "How'd we end up here?",
                ";;;", ": Swim home -> 4", ": Visit market -> 3", "R3:    Market under the sea",
                "You find no market, but are now lost to wander forever", ";;;", Config.FAIL,
                "R4:Nothing left",
                "You're home, but there's little food remaining. Oh well, you can always go shopping tomorrow.",
                ";;;", Config.SUCCESS};
            String story1 = String.join("\n", story1Array);

            ArrayList<String[]> arrRooms = new ArrayList<String[]>();
            ArrayList<ArrayList<String[]>> arrTrans = new ArrayList<ArrayList<String[]>>();
            String[] curRoom = new String[1];
            if (!AdventureStory.parseStory(new Scanner(story1), arrRooms, arrTrans, curRoom)) {
                System.out.println("parseStoryZyBooks story1: returned false instead of true.");
                passed = false;
            }

            // Expected ArrayList of room details:
            ArrayList<String[]> expRooms = new ArrayList<String[]>(Arrays.asList(new String[][] {
                {"1", "Home", "Home is where the heart is..."},
                {"2", "Deep Sea", "How'd we end up here?"},
                {"3", "Market under the sea",
                    "You find no market, but are now lost to wander forever"},
                {"4", "Nothing left",
                    "You're home, but there's little food remaining. Oh well, you can always go shopping tomorrow."}}));

            // Expected ArrayList of ArrayList of transition details:
            ArrayList<String[]> room1Trans = new ArrayList<String[]>(Arrays
                .asList(new String[][] {{"Stay home", "4", null}, {"Visit market", "2", null}}));
            ArrayList<String[]> room2Trans = new ArrayList<String[]>(Arrays
                .asList(new String[][] {{"Swim home", "4", null}, {"Visit market", "3", null}}));
            ArrayList<String[]> room3Trans =
                new ArrayList<String[]>(Arrays.asList(new String[][] {{Config.FAIL, null, null}}));
            ArrayList<String[]> room4Trans = new ArrayList<String[]>(
                Arrays.asList(new String[][] {{Config.SUCCESS, null, null}}));

            ArrayList<ArrayList<String[]>> expTrans = new ArrayList<ArrayList<String[]>>();
            expTrans.add(room1Trans);
            expTrans.add(room2Trans);
            expTrans.add(room3Trans);
            expTrans.add(room4Trans);

            if (!compareArrayListsArrays(arrRooms, expRooms)) {
                System.out.println("parseStoryZyBooks story1: \nrooms ArrayList returned: \n"
                    + Arrays.deepToString(arrRooms.toArray()) + "\nExpected: \n"
                    + Arrays.deepToString(expRooms.toArray()) + "\n");
                passed = false;
            }

            if (!compare2dArrayLists(arrTrans, expTrans)) {
                System.out.println("parseStoryZyBooks story1: \ntransition ArrayList returned: \n"
                    + toString2dArrayLists(arrTrans) + "\nExpected: \n"
                    + toString2dArrayLists(expTrans) + "\n");
                passed = false;
            }
            if (passed)
                System.out.println("parseStoryZyBooks story1: passed");
        }

        {
            boolean passed2 = true;

            String[] story2Array = {"#!STORY", "# Eroneous comment", "# And another, good sir!",
                "R1: Welcome to Skyrim", "You sit atop a carriage, trundling across a small road.",
                "# You can look around, but not move",
                "You are shackled, and you hear other prisoners talking, discussing their origins.",
                "One of the men seems to be Ulfrik Stormcloak, whom you've heard tell of.",
                "People say that he shouted the emperor to death.",
                "Regardless of present company, you slowly ride into a small town by the name of Helgen",
                "Though the guard inspecting you says \"This one's not on the list,\" but there's nothing you can do.",
                "Another guard pushes you forward towards the block, whispering, \"I'm sorry, prisoner.\"",
                "As you are pushed onto the block, you here faint sounds of a distant cry.",
                "Now without hope, you lay on the block and point your head towards the sky as best you can.",
                "From out of nowhere, you see a massive dragon slam down atop the nearby guard tower!",
                "Its shout deafens you and shakes the entire town with magical power.",
                "Under its mighty power, you struggle to stay awake...", ";;;", "# MORE COMMENTS",
                ": You are the Dragonborn -> 2 ? 1", ": You are normal -> 5 ? 3", "R2: Dragonborn",
                "You are the mighty Dragonborn, hero of Skyrim.",
                "Little can stand in your way as you escape Helgen and embark on a mighty, epic journey!",
                "The only thing which prevents you from completing your destiny is an endless series of sidequests.",
                ";;;", Config.SUCCESS, "# I JUST LOVE COMMENTS", "R5: Goodnight", // Yeah, we
                                                                                  // skipped 3 & 4!
                "Your mortal might cannot hold against the power of the Dragon and you fall unconscious,",
                "never to awaken...", ";;;",

                Config.FAIL};
            String story2 = String.join("\n", story2Array);

            ArrayList<String[]> arrRooms = new ArrayList<String[]>();
            ArrayList<ArrayList<String[]>> arrTrans = new ArrayList<ArrayList<String[]>>();
            String[] curRoom = new String[1];
            if (!AdventureStory.parseStory(new Scanner(story2), arrRooms, arrTrans, curRoom)) {
                System.out.println("parseStoryZyBooks story2: returned false instead of true.");
                passed2 = false;
            }

            final String SKYRIM = String.join("\n", new String[] {
                "You sit atop a carriage, trundling across a small road.",
                "# You can look around, but not move",
                "You are shackled, and you hear other prisoners talking, discussing their origins.",
                "One of the men seems to be Ulfrik Stormcloak, whom you've heard tell of.",
                "People say that he shouted the emperor to death.",
                "Regardless of present company, you slowly ride into a small town by the name of Helgen",
                "Though the guard inspecting you says \"This one's not on the list,\" but there's nothing you can do.",
                "Another guard pushes you forward towards the block, whispering, \"I'm sorry, prisoner.\"",
                "As you are pushed onto the block, you here faint sounds of a distant cry.",
                "Now without hope, you lay on the block and point your head towards the sky as best you can.",
                "From out of nowhere, you see a massive dragon slam down atop the nearby guard tower!",
                "Its shout deafens you and shakes the entire town with magical power.",
                "Under its mighty power, you struggle to stay awake..."});

            final String DRAGONBORN = String.join("\n", new String[] {
                "You are the mighty Dragonborn, hero of Skyrim.",
                "Little can stand in your way as you escape Helgen and embark on a mighty, epic journey!",
                "The only thing which prevents you from completing your destiny is an endless series of sidequests."});

            // Expected ArrayList of room details:
            ArrayList<String[]> expRooms = new ArrayList<String[]>(Arrays.asList(new String[][] {
                {"1", "Welcome to Skyrim", SKYRIM}, {"2", "Dragonborn", DRAGONBORN},
                {"5", "Goodnight",
                    "Your mortal might cannot hold against the power of the Dragon and you fall unconscious,\n"
                        + "never to awaken..."}}));

            // Expected ArrayList of ArrayList of transition details:
            ArrayList<String[]> room1Trans = new ArrayList<String[]>(Arrays.asList(new String[][] {
                {"You are the Dragonborn", "2", "1"}, {"You are normal", "5", "3"}}));
            ArrayList<String[]> room2Trans = new ArrayList<String[]>(
                Arrays.asList(new String[][] {{Config.SUCCESS, null, null}}));
            ArrayList<String[]> room5Trans =
                new ArrayList<String[]>(Arrays.asList(new String[][] {{Config.FAIL, null, null}}));

            ArrayList<ArrayList<String[]>> expTrans = new ArrayList<ArrayList<String[]>>();
            expTrans.add(room1Trans);
            expTrans.add(room2Trans);
            expTrans.add(room5Trans);

            if (!compareArrayListsArrays(arrRooms, expRooms)) {
                System.out.println("parseStoryZyBooks story2: \nrooms ArrayList returned: \n"
                    + Arrays.deepToString(arrRooms.toArray()) + "\nExpected: \n"
                    + Arrays.deepToString(expRooms.toArray()) + "\n");
                passed2 = false;
            }

            if (!compare2dArrayLists(arrTrans, expTrans)) {
                System.out.println("parseStoryZyBooks story2: \ntransition ArrayList returned: \n"
                    + toString2dArrayLists(arrTrans) + "\nExpected: \n"
                    + toString2dArrayLists(expTrans) + "\n");
                passed2 = false;
            }
            if (passed2)
                System.out.println("parseStoryZyBooks story2: passed");

        }

    }

    /**
     * This runs some tests on the parseBookmark method.
     */
    private static void testParseBookmark() {
        {// tests that the method fills the other structures correctly
            Scanner testSc = new Scanner("Goldilocks.story\n" + "7");
            ArrayList<String[]> arrRooms = new ArrayList<String[]>();
            ArrayList<ArrayList<String[]>> arrTrans = new ArrayList<ArrayList<String[]>>();
            String[] curRoom = new String[1];
            boolean passed = true;
            // if (!AdventureStory.parseBookmark(testSc, arrRooms, arrTrans, curRoom)) {
            // System.out.println("parseBookmark 1: returned false instead of true.");
            // passed = false;
            // }
            if (!AdventureStory.parseBookmark(testSc, arrRooms, arrTrans, curRoom)) {
                System.out.println("parseBookmark 1: returned false instead of true.");
                passed = false;
            }


            if (!arrRooms.get(2)[Config.ROOM_TITLE].equals("Cottage Sitting Room")) {
                System.out.println("parseBookmark 1: returned false instead of true.");
                passed = false;
            }
            if (!arrTrans.get(2).get(1)[Config.TRAN_ROOM_ID].equals("5")) {
                System.out.println("parseBookmark 1: returned false instead of true.");
                passed = false;
            }
            if (passed) {
                System.out.println("parseBookmark 1: passed");
            }
        }
        {// Tests the base method parseBookMark works correctly
            Scanner testSc = new Scanner("Goldilocks.story\n" + "7");
            ArrayList<String[]> arrRooms = new ArrayList<String[]>();
            ArrayList<ArrayList<String[]>> arrTrans = new ArrayList<ArrayList<String[]>>();
            String[] curRoom = new String[1];
            boolean passed = true;
            if (!AdventureStory.parseBookmark(testSc, arrRooms, arrTrans, curRoom)) {
                System.out.println("parseBookmark 2: returned false instead of true.");
                passed = false;
            }
            if (!curRoom[0].equals("7")) {
                System.out.println("parseBookmark 2: returned false instead of true.");
                passed = false;
            }

            if (passed) {
                System.out.println("parseBookmark 2: passed");
            }
        }
    }

    /**
     * This runs some tests on the probTrans method.
     */
    private static void testProbTrans() {
        {
            Random rand = new Random(Config.SEED);
            boolean passed = true;
            ArrayList<String[]> curTrans =
                new ArrayList<String[]>(Arrays.asList(new String[][] {{"Transition 1", "1", "1"},
                    {"Transition 2", "2", "2"}, {"Transition 3", "3", "2"}}));

            String result = AdventureStory.probTrans(rand, curTrans);

            if (!result.equals("2")) {
                System.out.println("probTrans 1: returned false instead of true.");
                passed = false;
            }
            if (passed) {
                System.out.println("probTrans 1: passed");
            }
        }
        {
            Random rand = new Random(Config.SEED);
            boolean passed = true;
            ArrayList<String[]> curTrans =
                new ArrayList<String[]>(Arrays.asList(new String[][] {{"Transition 1", "1", "4"},
                    {"Transition 2", "2", "3"}, {"Transition 3", "3", "5"}}));

            String result = AdventureStory.probTrans(rand, curTrans);

            if (!result.equals("3")) {
                System.out.println("probTrans 2: returned false instead of true.");
                passed = false;
            }
            if (passed) {
                System.out.println("probTrans 2: passed");
            }
        }
    }


    private static String toString2dArrayLists(ArrayList<ArrayList<String[]>> arrL1) {
        String toRet = "[\n";
        for (int i = 0; i < arrL1.size(); ++i) {
            toRet += "\t" + Arrays.deepToString(arrL1.get(i).toArray());
            if (i < arrL1.size() - 1)
                toRet += ",";
            toRet += "\n";
        }
        toRet += "]";
        return toRet;
    }

    private static boolean compareArrayListsArrays(ArrayList<String[]> arrL1,
        ArrayList<String[]> arrL2) {
        if (arrL1.size() != arrL2.size())
            return false;
        for (int i = 0; i < arrL1.size(); ++i) {
            if (!Arrays.deepEquals(arrL1.get(i), arrL2.get(i)))
                return false;
        }
        return true;
    }

    private static boolean compare2dArrayLists(ArrayList<ArrayList<String[]>> arrL1,
        ArrayList<ArrayList<String[]>> arrL2) {
        if (arrL1.size() != arrL2.size())
            return false;
        for (int i = 0; i < arrL1.size(); ++i) {
            if (!compareArrayListsArrays(arrL1.get(i), arrL2.get(i)))
                return false;
        }
        return true;
    }

}
