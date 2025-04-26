import java.util.Arrays;

public class MinimumHeap {
    private String[] heap;
    private int capacity;
    private int size;

    // MinimumHeap(int MaxSize) - creates empty min-heap with specified capacity
    public MinimumHeap(int MaxSize) {
        this.capacity = MaxSize;
        this.heap = new String[MaxSize];
        this.size = 0;
    }

    // int Count() - returns number of postcodes in heap
    public int Count() {
        return size;
    }

    // void Insert(String) - inserts value with sift-up operation
    public void Insert(String value) {
        if (size >= capacity) {
            System.out.println("Heap is full");
            return;
        }

        heap[size] = value;
        siftUp(size);
        size++;
    }

    // String ExtractMinimum() - removes and returns minimum value
    public String ExtractMinimum() {
        if (size == 0)
            return null;

        String min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    // bool Search(String) - linear search through heap array
    // O(n)
    public boolean Search(String value) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    // String[] InOrder() - returns sorted array while emptying heap
    public String[] InOrder() {
        String[] result = new String[size];
        int originalSize = size;
        String[] copy = Arrays.copyOf(heap, size);

        for (int i = 0; i < originalSize; i++) {
            result[i] = ExtractMinimum();
        }

        // Restore original state from copy
        // These copying and restoring the heap operations will heavily effect the
        // InOrder's time complexity
        heap = Arrays.copyOf(copy, capacity);
        size = originalSize;

        return result;
    }

    // Helper methods for heap operations
    // Bubble up
    private void siftUp(int index) {
        // while index is not root yet
        while (index > 0) {
            int parent = (index - 1) / 2;
            // if child < parent
            if (heap[index].compareTo(heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }
        if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }

        // if smallest == index,
        // either -> last element has arrived to the highest level/depth
        // or -> last element < both left and right of next level
        if (smallest != index) {
            swap(index, smallest);
            siftDown(smallest);
        }
    }

    private void swap(int i, int j) {
        String temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // for visualization
    public String[] getHeap() {
        return Arrays.copyOf(heap, size); // Return only used portion
    }
}