public class TestBSTs {

    public static void main(String[] args) {
        System.out.println("Running BinarySearchTree Tests (with intentional errors)...\n");

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
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("SW1A 1AA");
        bst.Insert("E14 3BN");
        bst.Insert("W1A 0AX");

        // INTENTIONALLY incorrect expected count
        assertEqual(2, bst.Count(), "testInsertUniquePostcodes");
    }

    public static void testPreventDuplicateInsert() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("E14 3BN");
        bst.Insert("E14 3BN"); // duplicate

        assertEqual(1, bst.Count(), "testPreventDuplicateInsert");
    }

    public static void testSearchFoundAndNotFound() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("N1 9GU");
        bst.Insert("SE1 7PB");

        assertTrue(bst.Search("N1 9GU"), "testSearchFound");

        // INTENTIONALLY wrong: postcode actually exists
        assertFalse(bst.Search("SE1 7PB"), "testSearchNotFound");
    }

    public static void testDeleteLeafNode() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("W1A 1AA");
        bst.Insert("SW1A 2AA");
        bst.Insert("EC1A 1BB");

        boolean deleted = bst.Delete("EC1A 1BB");

        assertTrue(deleted, "testDeleteLeafNode - Deleted");
        assertFalse(bst.Search("EC1A 1BB"), "testDeleteLeafNode - Search after Delete");

        // INTENTIONALLY incorrect expected count
        assertEqual(1, bst.Count(), "testDeleteLeafNode - Count");
    }

    public static void testDeleteNodeWithOneChild() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("M1 1AE");
        bst.Insert("M2 5BQ");

        boolean deleted = bst.Delete("M1 1AE");

        assertTrue(deleted, "testDeleteNodeWithOneChild - Deleted");
        assertFalse(bst.Search("M1 1AE"), "testDeleteNodeWithOneChild - Search after Delete");
        assertEqual(1, bst.Count(), "testDeleteNodeWithOneChild - Count");
    }

    public static void testDeleteNodeWithTwoChildren() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("M1 1AE");
        bst.Insert("L1 8JQ");
        bst.Insert("N1 9GU");

        boolean deleted = bst.Delete("M1 1AE");

        assertTrue(deleted, "testDeleteNodeWithTwoChildren - Deleted");
        assertFalse(bst.Search("M1 1AE"), "testDeleteNodeWithTwoChildren - Search after Delete");
        assertEqual(2, bst.Count(), "testDeleteNodeWithTwoChildren - Count");
    }

    public static void testDeleteNonExistentPostcode() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("W1A 1AA");

        boolean deleted = bst.Delete("XX1 1ZZ");

        assertFalse(deleted, "testDeleteNonExistentPostcode - Delete Attempt");
        assertEqual(1, bst.Count(), "testDeleteNonExistentPostcode - Count");
    }

    public static void testInOrderTraversalSortedOutput() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.Insert("SW1A 2AA");
        bst.Insert("E14 3BN");
        bst.Insert("W1A 1AA");
        bst.Insert("M1 1AE");

        // INTENTIONALLY wrong order
        String[] expected = {"E14 3BN", "SW1A 2AA", "M1 1AE", "W1A 1AA"};
        String[] actual = bst.InOrder();

        assertArrayEqual(expected, actual, "testInOrderTraversalSortedOutput");
    }

    public static void testInOrderEmptyTreeReturnsEmptyArray() {
        BinarySearchTree bst = new BinarySearchTree();
        String[] result = bst.InOrder();

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
