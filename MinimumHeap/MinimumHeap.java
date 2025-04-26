package MinimumHeap;

public class MinimumHeap {
    private String[] Heap;
    private int HeapSize;
    private int HeapIndex;
    private boolean heapFullLogged = false; // Tracks if the "Heap is full" message has been logged

    public MinimumHeap(int MaxNumber) {
        this.Heap = new String[MaxNumber];
        this.HeapSize = MaxNumber;
        this.HeapIndex = 0;
    }

    public int Count() {
        return HeapIndex;
    }


    public void Insert(String PostCode) {
        PostCode = PostCode.trim().toUpperCase();
        if (HeapIndex > 0) {
            if (SearchMiniHeap(PostCode)){
                return;
            }
        }
        if (HeapIndex == HeapSize) {
            if (!heapFullLogged) {
                System.out.println("Heap is full. Further insertions will be ignored.");
                heapFullLogged = true;
            }
            return;
        }
        Heap[HeapIndex] = PostCode;
        HeapIndex++;
        HeapOperations.HeapifyUpwards(Heap, HeapIndex - 1);
    }

    public String ExtractMinimum() {
        if (HeapIndex <= 0) {
            System.out.println("Extraction not required, Heap is empty");
            return null;
        } else {
            String CurrentRoot = Heap[0];
            Heap[0] = Heap[HeapIndex - 1]; 
            Heap[HeapIndex - 1] = null; 
            HeapIndex--;
            HeapOperations.HeapifyDownwards(Heap, 0, HeapIndex);
            return CurrentRoot;
        }
    }


    public boolean SearchMiniHeap(String PostCode) {
        PostCode = PostCode.trim().toUpperCase();
        for (int i = 0; i < HeapIndex; i++) {
            if (Heap[i].equals(PostCode)) {
                return true;
            }
        }
        return false;
    }

    public String[] InOrder() {
        MinimumHeap heapCopy = this.copy(); // Create a copy of the heap
        int heapSize = heapCopy.Count();
    
        if (heapSize == 0) {
            return new String[0]; // Edge case: empty heap
        }
    
        String[] sorted = new String[heapSize];
        for (int i = 0; i < heapSize; i++) {
            sorted[i] = heapCopy.ExtractMinimum(); // This must return original root
        }
    
        return sorted;
    }
    

    public String Delete(String PostCode) {
        int indexToDelete = -1;

        PostCode = PostCode.trim().toUpperCase();

        // Step 1: Find the index of the postcode
        for (int i = 0; i < HeapIndex; i++) {
            if (Heap[i].equals(PostCode)) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            return "Postcode '" + PostCode + "' not found in the heap.";
        }

        // Step 2: Replace with the last element in the heap with index to delete
        Heap[indexToDelete] = Heap[HeapIndex - 1];
        Heap[HeapIndex - 1] = null; // Clear the last element
        HeapIndex--;// Decrease the size of the heap
        if (indexToDelete > 0 &&
                Heap[indexToDelete].compareTo(Heap[HeapOperations.getParentHeapIndex(indexToDelete)]) < 0) {
            HeapOperations.HeapifyUpwards(Heap, indexToDelete);
        } else {
            HeapOperations.HeapifyDownwards(Heap, indexToDelete, HeapIndex);
        }

        return "Postcode '" + PostCode + "' deleted successfully.";
    }

    public void printAll() {
        if (HeapIndex == 0) {
            System.out.println("The heap is empty.");
            return;
        }

        System.out.println("Postcodes in the heap:");
        for (int i = 0; i < HeapIndex; i++) {
            System.out.println("[" + i + "] " + Heap[i]);
        }
    }
    public MinimumHeap copy() {
        MinimumHeap newHeap = new MinimumHeap(this.HeapSize);
        newHeap.HeapIndex = this.HeapIndex;
    
        for (int i = 0; i < this.HeapIndex; i++) {
            newHeap.Heap[i] = this.Heap[i]; // Copy values
        }
    
        return newHeap;
    }
    

}

