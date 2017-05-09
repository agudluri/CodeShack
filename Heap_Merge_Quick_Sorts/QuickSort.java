public class QuickSort {

	public static void quickSort(Student[] a) {
		quicksort1(a, 0, a.length - 1);
	}

	public static void quicksort1(Student[] a, int left, int right) {
		if (left < right) {
			int pi = (left + right) / 2;
			int newPivotIndex = partition(a, left, right, pi);
			quicksort1(a, left, newPivotIndex - 1);
			quicksort1(a, newPivotIndex + 1, right);
		}
	}

	public static int partition(Student[] a, int left, int right, int pivotIndex) {
		Student pivot;
		Student tmp;
		int i, storeIndex;

		pivot = a[pivotIndex];

		tmp = a[pivotIndex]; /* Swap a[pivotIndex] and a[right] */
		a[pivotIndex] = a[right];
		a[right] = tmp;

		storeIndex = left;
		for (i = left; i < right; i++) {
			if ((a[i]).compareTo(pivot) < 0) {
				tmp = a[storeIndex]; /* Swap a[i] and a[storeIndex] */
				a[storeIndex] = a[i];
				a[i] = tmp;

				storeIndex++;
			}
		}

		tmp = a[right]; /* Swap a[storeIndex] and a[right] */
		a[right] = a[storeIndex]; /* Moves pivot to its final place */
		a[storeIndex] = tmp;

		return storeIndex;
	}
}