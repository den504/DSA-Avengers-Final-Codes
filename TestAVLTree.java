public class TestAVLTree {

    public static void main(String[] args) {
        System.out.println("Running AVLTree Tests (with intentional errors)...\n");

        testInsertUniquePostcodes();
        testPreventDuplicateInsert();
        testSearchFoundAndNotFound();
        testDeleteLeafNode();
        testDeleteNodeWithOneChild();
        testDeleteNodeWithTwoChildren();
        testDeleteNonExistentPostcode();
        testInOrderTraversalSortedOutput();
        testInOrderEmptyTreeReturnsEmptyArray();

        System.out.println("\nAll tests completed.");
    }

    public static void testInsertUniquePostcodes() {
        AVLTree avl = new AVLTree();
        avl.Insert("SW1A 1AA");
        avl.Insert("E14 3BN");
        avl.Insert("W1A 0AX");

        // INTENTIONALLY incorrect expected count
        assertEqual(2, avl.Count(), "testInsertUniquePostcodes");
    }

    public static void testPreventDuplicateInsert() {
        AVLTree avl = new AVLTree();
        avl.Insert("E14 3BN");
        avl.Insert("E14 3BN"); // duplicate

        assertEqual(1, avl.Count(), "testPreventDuplicateInsert");
    }

    public static void testSearchFoundAndNotFound() {
        AVLTree avl = new AVLTree();
        avl.Insert("N1 9GU");
        avl.Insert("SE1 7PB");

        assertTrue(avl.Search("N1 9GU"), "testSearchFound");

        // INTENTIONALLY wrong: postcode actually exists
        assertFalse(avl.Search("SE1 7PB"), "testSearchNotFound");
    }

    public static void testDeleteLeafNode() {
        AVLTree avl = new AVLTree();
        avl.Insert("W1A 1AA");
        avl.Insert("SW1A 2AA");
        avl.Insert("EC1A 1BB");

        boolean deleted = avl.Delete("EC1A 1BB");

        assertTrue(deleted, "testDeleteLeafNode - Deleted");
        assertFalse(avl.Search("EC1A 1BB"), "testDeleteLeafNode - Search after Delete");

        // INTENTIONALLY incorrect expected count
        assertEqual(1, avl.Count(), "testDeleteLeafNode - Count");
    }

    public static void testDeleteNodeWithOneChild() {
        AVLTree avl = new AVLTree();
        avl.Insert("M1 1AE");
        avl.Insert("M2 5BQ");

        boolean deleted = avl.Delete("M1 1AE");

        assertTrue(deleted, "testDeleteNodeWithOneChild - Deleted");
        assertFalse(avl.Search("M1 1AE"), "testDeleteNodeWithOneChild - Search after Delete");
        assertEqual(1, avl.Count(), "testDeleteNodeWithOneChild - Count");
    }

    public static void testDeleteNodeWithTwoChildren() {
        AVLTree avl = new AVLTree();
        avl.Insert("M1 1AE");
        avl.Insert("L1 8JQ");
        avl.Insert("N1 9GU");

        boolean deleted = avl.Delete("M1 1AE");

        assertTrue(deleted, "testDeleteNodeWithTwoChildren - Deleted");
        assertFalse(avl.Search("M1 1AE"), "testDeleteNodeWithTwoChildren - Search after Delete");
        assertEqual(2, avl.Count(), "testDeleteNodeWithTwoChildren - Count");
    }

    public static void testDeleteNonExistentPostcode() {
        AVLTree avl = new AVLTree();
        avl.Insert("W1A 1AA");

        boolean deleted = avl.Delete("XX1 1ZZ");

        assertFalse(deleted, "testDeleteNonExistentPostcode - Delete Attempt");
        assertEqual(1, avl.Count(), "testDeleteNonExistentPostcode - Count");
    }

    public static void testInOrderTraversalSortedOutput() {
        AVLTree avl = new AVLTree();
        avl.Insert("SW1A 2AA");
        avl.Insert("E14 3BN");
        avl.Insert("W1A 1AA");
        avl.Insert("M1 1AE");

        // INTENTIONALLY wrong order
        String[] expected = {"E14 3BN", "SW1A 2AA", "M1 1AE", "W1A 1AA"};
        String[] actual = avl.InOrder();

        assertArrayEqual(expected, actual, "testInOrderTraversalSortedOutput");
    }

    public static void testInOrderEmptyTreeReturnsEmptyArray() {
        AVLTree avl = new AVLTree();
        String[] result = avl.InOrder();

        assertEqual(0, result.length, "testInOrderEmptyTreeReturnsEmptyArray");
    }

    // Helper assertion methods

    public static void assertEqual(int expected, int actual, String testName) {
        if (expected != actual) {
            System.out.printf("ERROR: %s: Expected %d, got %d%n", testName, expected, actual);
        } else {
            System.out.printf("SUCCESS: %s passed%n", testName);
        }
    }

    public static void assertEqual(String expected, String actual, String testName) {
        if (!expected.equals(actual)) {
            System.out.printf("ERROR: %s: Expected \"%s\", got \"%s\"%n", testName, expected, actual);
        } else {
            System.out.printf("SUCCESS: %s passed%n", testName);
        }
    }

    public static void assertTrue(boolean condition, String testName) {
        if (!condition) {
            System.out.printf("ERROR: %s: Expected true, got false%n", testName);
        } else {
            System.out.printf("SUCCESS: %s passed%n", testName);
        }
    }

    public static void assertFalse(boolean condition, String testName) {
        if (condition) {
            System.out.printf("ERROR: %s: Expected false, got true%n", testName);
        } else {
            System.out.printf("SUCCESS: %s passed%n", testName);
        }
    }

    public static void assertArrayEqual(String[] expected, String[] actual, String testName) {
        if (expected.length != actual.length) {
            System.out.printf("ERROR: %s: Array length mismatch%n", testName);
            return;
        }
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(actual[i])) {
                System.out.printf("ERROR: %s: Mismatch at index %d (%s != %s)%n", testName, i, expected[i], actual[i]);
                return;
            }
        }
        System.out.printf("SUCCESS: %s passed%n", testName);
    }
}
