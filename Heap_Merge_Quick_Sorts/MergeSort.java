public class MergeSort {

	public static void Sort(Student[] a) {
		Student aux[] = a.clone();
		Sort1(aux, a, 0, a.length);
	}

	private static void Sort1(Student src[], Student dest[], int low, int high) {
		int length = high - low;

		if (length < 7) {
			for (int i = low; i < high; i++)
				for (int j = i; j > low && (dest[j - 1]).compareTo(dest[j]) > 0; j--)
					swap(dest, j, j - 1);

			return;
		}
		int mid = (low + high) >> 1;
		Sort1(dest, src, low, mid);
		Sort1(dest, src, mid, high);

		if ((src[mid - 1]).compareTo(src[mid]) <= 0) {
			System.arraycopy(src, low, dest, low, length);
			return;
		}

		for (int i = low, p = low, q = mid; i < high; i++)
			if ((q >= high) || (p < mid && (src[p]).compareTo(src[q]) <= 0))
				dest[i] = src[p++];
			else
				dest[i] = src[q++];
	}

	public static void swap(Student[] x, int a, int b) {
		Student t = x[a];
		x[a] = x[b];
		x[b] = t;
	} // method swap
}
