import java.util.ArrayList;

public class MinHeap {
    private final ArrayList<Node> heap;

    public MinHeap() {
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

            if (heap.get(index).getGiniImpurity() >= heap.get(parentIndex).getGiniImpurity()) {
                break; // Heap property is satisfied
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Extract the maximum element from the heap
    public Node removeMin() {
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
            int smallest = index;

//            compare by gini impurity
            if (leftChild < size && heap.get(leftChild).getGiniImpurity() < heap.get(smallest).getGiniImpurity()) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild).getGiniImpurity() < heap.get(smallest).getGiniImpurity()) {
                smallest = rightChild;
            }

            if (smallest == index) {
                break; // Heap property is satisfied
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

