public class HeapSort {

	protected static Student[] queue;

	protected static int size = 0;

	public static void Sort(Student[] a) {
		queue = a;
		int length = queue.length;
		size = length;

		// Convert queue into a heap:
		for (int i = (size >> 1) - 1; i >= 0; i--)
			siftDown(i, queue[i]);

		Student x;
		for (int i = 0; i < length; i++) {
			x = queue[--size];
			queue[size] = queue[0];
			siftDown(0, x);
		}
		for (int i = 0; i < length / 2; i++) {
			x = queue[i];
			queue[i] = queue[length - i - 1];
			queue[length - i - 1] = x;
		}
	} // method heapSort

	protected static void siftDown(int k, Student x) {
		int half = size >>> 1; // loop while a non-leaf
		while (k < half) {
			int child = (k << 1) + 1; // assume left child is least
			Student c = queue[child];
			int right = child + 1;
			if (right < size && (c).compareTo(queue[right]) > 0)
				c = queue[child = right];
			if (x.compareTo(c) <= 0)
				break;
			queue[k] = c;
			k = child;
		} // while
		queue[k] = x;
	}
}
