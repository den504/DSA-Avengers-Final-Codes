public class TestMinimumHeap {

    public static void main(String[] args) {
        System.out.println("Running MinimumHeap Tests (with intentional errors)...\n");

        testInsertUniquePostcodes();
        testPreventDuplicateInsert();
        testSearchFoundAndNotFound();
        testExtractMinimumOrder();
        testInOrderSortedOutput();
        testInOrderEmptyHeapReturnsEmptyArray();

        System.out.println("\nAll tests completed.");
    }

    public static void testInsertUniquePostcodes() {
        MinimumHeap heap = new MinimumHeap(10);
        heap.Insert("SW1A 1AA");
        heap.Insert("E14 3BN");
        heap.Insert("W1A 0AX");

        // INTENTIONALLY incorrect expected count
        assertEqual(2, heap.Count(), "testInsertUniquePostcodes");
    }

    public static void testPreventDuplicateInsert() {
        MinimumHeap heap = new MinimumHeap(10);
        heap.Insert("E14 3BN");
        heap.Insert("E14 3BN"); // duplicate

        // Correct expectation (still passes)
        assertEqual(1, heap.Count(), "testPreventDuplicateInsert");
    }

    public static void testSearchFoundAndNotFound() {
        MinimumHeap heap = new MinimumHeap(5);
        heap.Insert("N1 9GU");
        heap.Insert("SE1 7PB");

        assertTrue(heap.Search("N1 9GU"), "testSearchFound");

        // INTENTIONALLY incorrect - postcode does exist
        assertFalse(heap.Search("SE1 7PB"), "testSearchNotFound");
    }

    public static void testExtractMinimumOrder() {
        MinimumHeap heap = new MinimumHeap(5);
        heap.Insert("SW1A 2AA");
        heap.Insert("E14 3BN");
        heap.Insert("W1A 1AA");

        String min = heap.ExtractMinimum();

        // Correct expectation
        assertEqual("E14 3BN", min, "testExtractMinimumOrder");

        // INTENTIONALLY incorrect expected count
        assertEqual(1, heap.Count(), "testExtractMinimumOrder - Count");
    }

    public static void testInOrderSortedOutput() {
        MinimumHeap heap = new MinimumHeap(5);
        heap.Insert("SW1A 2AA");
        heap.Insert("E14 3BN");
        heap.Insert("W1A 1AA");
        heap.Insert("M1 1AE");

        // INTENTIONALLY incorrect sort order
        String[] expected = {"E14 3BN", "SW1A 2AA", "M1 1AE", "W1A 1AA"};
        String[] actual = heap.InOrder();

        assertArrayEqual(expected, actual, "testInOrderSortedOutput");
    }

    public static void testInOrderEmptyHeapReturnsEmptyArray() {
        MinimumHeap heap = new MinimumHeap(5);
        String[] result = heap.InOrder();

        assertEqual(0, result.length, "testInOrderEmptyHeapReturnsEmptyArray");
    }

    // Assertion helpers

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
