import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static final String TEST_DIR = "./";
    private static final String[] FILE_NAMES = {
            "1000_London_Postcodes.txt", "2000_London_Postcodes.txt",
            "4000_London_Postcodes.txt", "8000_London_Postcodes.txt",
            "16000_London_Postcodes.txt"
    };
    // Insert will do first, so we search and delete this same postcode
    private static final String TARGET_POSTCODE = "ZZ ZZZ";
    private static List<List<String>> loadedDatasets = new ArrayList<>();
    private static List<BinarySearchTree> bstTrees;
    private static List<AVLTree> avlTrees;
    private static List<MinimumHeap> heaps;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Binary Search Tree Menu");
            System.out.println("2. AVL Tree Menu");
            System.out.println("3. Minimum Heap Menu");
            System.out.println("4. Benchmark All Data Structures");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showBSTMenu(scanner);
                    break;
                case 2:
                    showAVLMenu(scanner);
                    break;
                case 3:
                    showHeapMenu(scanner);
                    break;
                case 4:
                    runBenchmark(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Original BST Menu
    private static void showBSTMenu(Scanner scanner) {
        BinarySearchTree bst = new BinarySearchTree();
        while (true) {
            System.out.println("\n--- BST Postcode Manager ---");
            System.out.println("1. Count postcodes");
            System.out.println("2. Search postcode");
            System.out.println("3. Add postcode");
            System.out.println("4. Delete postcode");
            System.out.println("5. Save to file");
            System.out.println("6. Return to Main Menu");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Count: " + bst.Count());
                    break;
                case 2:
                    handleSearch(bst, scanner);
                    break;
                case 3:
                    handleInsert(bst, scanner);
                    break;
                case 4:
                    handleDelete(bst, scanner);
                    break;
                case 5:
                    saveToFile(bst.InOrder());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Original AVL Menu
    private static void showAVLMenu(Scanner scanner) {
        AVLTree avl = new AVLTree();
        while (true) {
            System.out.println("\n--- AVL Tree Manager ---");
            System.out.println("1. Count postcodes");
            System.out.println("2. Search postcode");
            System.out.println("3. Add postcode");
            System.out.println("4. Delete postcode");
            System.out.println("5. Save to file");
            System.out.println("6. Return to Main Menu");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Count: " + avl.Count());
                    break;
                case 2:
                    handleSearch(avl, scanner);
                    break;
                case 3:
                    handleInsert(avl, scanner);
                    break;
                case 4:
                    handleDelete(avl, scanner);
                    break;
                case 5:
                    saveToFile(avl.InOrder());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Heap-specific Menu
    private static void showHeapMenu(Scanner scanner) {
        MinimumHeap heap = new MinimumHeap(100);

        while (true) {
            System.out.println("\n--- Min Heap Manager ---");
            System.out.println("1. Count postcodes");
            System.out.println("2. Search postcode");
            System.out.println("3. Add postcode");
            System.out.println("4. Extract Minimum");
            System.out.println("5. Save to file");
            System.out.println("6. Return to Main Menu");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Count: " + heap.Count());
                    break;
                case 2:
                    System.out.print("Enter postcode: ");
                    System.out.println("Found: " + heap.Search(scanner.nextLine()));
                    break;
                case 3:
                    System.out.print("Enter postcode: ");
                    heap.Insert(scanner.nextLine());
                    break;
                case 4:
                    String min = heap.ExtractMinimum();
                    System.out.println(min != null ? "Extracted: " + min : "Heap empty!");
                    break;
                case 5:
                    saveToFile(heap.InOrder());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void runBenchmark(Scanner scanner) {

        // Preload all datasets not to interrupt with actual timing
        // System.out.println("Preloading datasets...");
        preloadAllDatasets();
        bstTrees = new ArrayList<>();
        avlTrees = new ArrayList<>();
        heaps = new ArrayList<>();
        for (int i = 0; i < loadedDatasets.size(); i++) {
            List<String> postcodes = loadedDatasets.get(i);
            bstTrees.add(new BinarySearchTree());
            avlTrees.add(new AVLTree());
            // some extra spaces for insertion benchmark
            heaps.add(new MinimumHeap(postcodes.size() + 10));
            for (String pc : postcodes) {
                bstTrees.get(i).Insert(pc);
                avlTrees.get(i).Insert(pc);
                heaps.get(i).Insert(pc);
            }
        }

        // Insertion benchmark
        System.out.println("\nBenchmark Results for Insert (ms):");
        System.out.println("Data Structure |    1000 |    2000 |    4000 |    8000 |    16000");
        System.out.println("---------------------------------------------------------------");

        System.out.print("BST            ");
        for (BinarySearchTree bst : bstTrees) {
            long start = System.nanoTime();
            bst.Insert(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();

        System.out.print("AVL            ");
        for (AVLTree avl : avlTrees) {
            long start = System.nanoTime();
            avl.Insert(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();

        System.out.print("HEAP           ");
        for (MinimumHeap heap : heaps) {
            long start = System.nanoTime();
            heap.Insert(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();

        // Search benchmark
        System.out.println("\nBenchmark Results for Search (ms):");
        System.out.println("Data Structure |    1000 |    2000 |    4000 |    8000 |    16000");
        System.out.println("---------------------------------------------------------------");
        System.out.print("BST            ");
        for (BinarySearchTree bst : bstTrees) {
            long start = System.nanoTime();
            bst.Search(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.print("AVL            ");
        for (AVLTree avl : avlTrees) {
            long start = System.nanoTime();
            avl.Search(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.print("HEAP           ");
        for (MinimumHeap heap : heaps) {
            long start = System.nanoTime();
            heap.Search(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();

        // Deletion benchmark
        System.out.println("\nBenchmark Results for Delete/ExtractMin (ms):");
        System.out.println("Data Structure |    1000 |    2000 |    4000 |    8000 |    16000");
        System.out.println("---------------------------------------------------------------");
        System.out.print("BST            ");
        for (BinarySearchTree bst : bstTrees) {
            long start = System.nanoTime();
            bst.Delete(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.print("AVL            ");
        for (AVLTree avl : avlTrees) {
            long start = System.nanoTime();
            avl.Delete(TARGET_POSTCODE);
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.print("HEAP           ");
        for (MinimumHeap heap : heaps) {
            long start = System.nanoTime();
            heap.ExtractMinimum();
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();

        // Warm up
        bstTrees.get(0).InOrder();
        avlTrees.get(0).InOrder();
        heaps.get(0).InOrder();

        // InOrder benchmark
        System.out.println("\nBenchmark Results for InOrder (ms):");
        System.out.println("Data Structure |    1000 |    2000 |    4000 |    8000 |    16000");
        System.out.println("---------------------------------------------------------------");
        System.out.print("BST            ");
        for (BinarySearchTree bst : bstTrees) {
            long start = System.nanoTime();
            bst.InOrder();
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.print("AVL            ");
        for (AVLTree avl : avlTrees) {
            long start = System.nanoTime();
            avl.InOrder();
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.print("HEAP           ");
        for (MinimumHeap heap : heaps) {
            long start = System.nanoTime();
            heap.InOrder();
            long time = System.nanoTime() - start;
            System.out.printf("| %7.2f ", time / 1e6);
        }
        System.out.println();
        System.out.println("\n--- Benchmark Complete ---");

        System.out.println("Press any key to return to Main Menu");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine();
        if (choice != null) {
            return;
        }
    }

    // Shared utility methods
    private static List<String> loadPostcodes(String path) throws IOException {
        List<String> postcodes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                postcodes.add(line.trim());
            }
        }
        return postcodes;
    }

    private static void preloadAllDatasets() {
        loadedDatasets.clear();
        try {
            for (String filename : FILE_NAMES) {
                loadedDatasets.add(loadPostcodes(TEST_DIR + File.separator + filename));
            }
        } catch (IOException e) {
            System.err.println("Fatal error loading datasets: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void saveToFile(String[] postcodes) {
        try (FileWriter writer = new FileWriter("postcodes.txt")) {
            for (String pc : postcodes)
                writer.write(pc + "\n");
            System.out.println("Saved to postcodes.txt");
        } catch (IOException e) {
            System.out.println("Save failed!");
        }
    }

    private static void handleSearch(BinarySearchTree tree, Scanner scanner) {
        System.out.print("Enter postcode: ");
        System.out.println("Found: " + tree.Search(scanner.nextLine()));
    }

    private static void handleInsert(BinarySearchTree tree, Scanner scanner) {
        System.out.print("Enter postcode: ");
        tree.Insert(scanner.nextLine());
        System.out.println("Added!");
    }

    private static void handleDelete(BinarySearchTree tree, Scanner scanner) {
        System.out.print("Enter postcode: ");
        System.out.println(tree.Delete(scanner.nextLine()) ? "Deleted!" : "Not found!");
    }

    // Overloaded methods for AVL
    private static void handleSearch(AVLTree tree, Scanner scanner) {
        System.out.print("Enter postcode: ");
        System.out.println("Found: " + tree.Search(scanner.nextLine()));
    }

    private static void handleInsert(AVLTree tree, Scanner scanner) {
        System.out.print("Enter postcode: ");
        tree.Insert(scanner.nextLine());
        System.out.println("Added!");
    }

    private static void handleDelete(AVLTree tree, Scanner scanner) {
        System.out.print("Enter postcode: ");
        System.out.println(tree.Delete(scanner.nextLine()) ? "Deleted!" : "Not found!");
    }
}