// Package: MinimumHeap
package MinimumHeap;
// import java.util.HashSet;

public class HeapOperations {

    // private static HashSet<String> uniqueElements = new HashSet<>(); 

    public static int getParentHeapIndex(int HeapIndex) {
        return (HeapIndex - 1) / 2;
    }

    public static int getLeftChildHeapIndex(int HeapIndex) {
        return (2 * HeapIndex) + 1;
    }

    public static int getRightChildHeapIndex(int HeapIndex) {
        return (2 * HeapIndex) + 2;
    }

    // public static int HeapifyUpwards(String[] Heap, int HeapIndex) {
    //     //check for duplicates
    //     // if (!uniqueElements.add(Heap[HeapIndex])) {
    //     //     return HeapIndex; // Return the index of the duplicate
    //     // }


    //     // root node is at index 0
    //     int CurrentHeapIndex = HeapIndex;


    //     while (CurrentHeapIndex > 0) {
    //         int ParentIndex = getParentHeapIndex(CurrentHeapIndex);



    //         if (Heap[CurrentHeapIndex].compareTo(Heap[ParentIndex]) < 0) {
    //             Reposition(Heap, CurrentHeapIndex, ParentIndex);
    //             CurrentHeapIndex = ParentIndex;
    //         } else {
    //             break;
    //         }
    //     }
    //     return CurrentHeapIndex;
    // }
    public static int HeapifyUpwards(String[] heap, int index) {
        int currentIndex = index;
    
        while (currentIndex > 0) {
            int parentIndex = getParentHeapIndex(currentIndex);
    
            // Compare current with parent
            if (heap[currentIndex].compareTo(heap[parentIndex]) < 0) {
                Reposition(heap, currentIndex, parentIndex);
                currentIndex = parentIndex; // Move up the tree
            } else {
                break; // Stop if current is bigger than parent
            }
        }
    
        return currentIndex;
    }
    

    public static void Reposition(String[] Heap, int HeapIndex, int ParentIndex) {
        String temp = Heap[HeapIndex];
        Heap[HeapIndex] = Heap[ParentIndex];
        Heap[ParentIndex] = temp;
    }

    public static void HeapifyDownwards(String[] Heap, int StartingIndex, int HeapIndex) {
        int CurrentIndex = StartingIndex;
        boolean HeapRestored = false;
        // assumes insertion took place in correctv order

        while (!HeapRestored) {
            int SmallestIndex = CurrentIndex; // starts with sallest and current been the same
            int LeftChildIndex = getLeftChildHeapIndex(SmallestIndex);
            int RightChildIndex = getRightChildHeapIndex(SmallestIndex);
            // smallest index to change if first two if statements work
            if (LeftChildIndex < HeapIndex &&
                    Heap[LeftChildIndex].compareTo(Heap[SmallestIndex]) < 0) {
                SmallestIndex = LeftChildIndex;
            }
            if (RightChildIndex < HeapIndex &&
                    Heap[RightChildIndex].compareTo(Heap[SmallestIndex]) < 0) {
                SmallestIndex = RightChildIndex;
            }
            // only break left and right child are not less than the smallest index
            // i.e Neither if conditions worked
            if (SmallestIndex == CurrentIndex) {
                HeapRestored = true;
            } else {
                Reposition(Heap, CurrentIndex, SmallestIndex);
                CurrentIndex = SmallestIndex;
            }

        }

    }
}
