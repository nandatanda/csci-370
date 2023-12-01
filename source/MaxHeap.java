import java.util.ArrayList;

public class MaxHeap {
    private ArrayList<Node> heap;

    public MaxHeap() {
        heap = new ArrayList<>();
    }

    // Insert an element into the heap
    public void insert(Node node) {
        heap.add(node);
        heapifyUp();
    }

    // Restore heap property after insertion
    private void heapifyUp() {
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            // compare gini impurity
//            if (heap.get(node) <= heap.get(parentIndex)) {
//                break; // Heap property is satisfied
//            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Extract the maximum element from the heap
    public Node removeMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        Node max = heap.get(0);
        Node lastElement = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown();
        }

        return max;
    }

    // Restore heap property after extraction
    private void heapifyDown() {
        int index = 0;
        int size = heap.size();

        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int largest = index;

            //compare gini impurity
//            if (leftChild < size && heap.get(leftChild) > heap.get(largest)) {
//                largest = leftChild;
//            }
            // compare gini impurity
//            if (rightChild < size && heap.get(rightChild) > heap.get(largest)) {
//                largest = rightChild;
//            }

            if (largest == index) {
                break; // Heap property is satisfied
            }

            swap(index, largest);
            index = largest;
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

