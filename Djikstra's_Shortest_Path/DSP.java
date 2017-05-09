
import java.io.*;
import java.util.*;

public class DSP {

	public static void main(String[] args) {

		List<List<Integer>> matrix;

		int source = 0;
		Scanner on = new Scanner(System.in);
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter the path ");

		String fileName = in.nextLine();

		matrix = readFileIntoMatrix(fileName);

		List<Object[]> resultList = extractShortestPath(matrix);
		
		System.out.println("Enter a start vertex:");
		source = on.nextInt();
		performSourceNodeOperations(source, resultList);

	}

	private static List<List<Integer>> readFileIntoMatrix(String fileName) {

		List<List<Integer>> matrix = null;
		try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			matrix = new ArrayList<List<Integer>>();
			String curLineString = br.readLine();

			while (null != curLineString) {

				String[] distanceArray = curLineString.split(" ");

				List<Integer> row = new ArrayList<Integer>();
				for (String value : distanceArray) {
					row.add(Integer.valueOf(value));
				}
				matrix.add(row);
				curLineString = br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return matrix;
	}

	private static void performSourceNodeOperations(int source, List<Object[]> resultList) {
		Object[] resultObj = resultList.get(source - 1);
		// read path from object
		int[] distance = (int[]) resultObj[0];
		String[] path = (String[]) resultObj[1];
		System.out.println("Start Vertex: " + source);
		for (int i = 1; i <= path.length; i++) {
			String interfac;
			String[] pathVal = path[i - 1].split(",");

			if (pathVal.length == 1) {
				interfac = pathVal[0];
			} else {
				interfac = pathVal[1];
			}
			if (distance[i - 1] == 0) {
				distance[i - 1] = 0;
			}

			System.out.println(source + "-->" + i + ":   Cost is   " + distance[i - 1] + ",   Path is   "
					+ path[i - 1].replaceAll(",", "-->"));
		}
	}

	private static Object[] findShortestPathForGivenSource(int[][] arr, int size, int source, int maxVal) {

		boolean[] visited = new boolean[size];

		int[] distance = new int[size];
		String[] path = new String[size];
		int min;
		int nextNode = 0;
		String prevPath;
		source--;

		for (int i = 0; i < size; i++) {
			visited[i] = false;
			distance[i] = arr[source][i];
		}
		distance[source] = 0;

		for (int i = 0; i < size; i++) {
			path[i] = String.valueOf(source + 1) + "," + String.valueOf(i + 1);
			if (distance[i] == maxVal) {
				path[i] = String.valueOf(source + 1);
			}
		}
		path[source] = "-";
		visited[source] = true;

		for (int i = 0; i < size; i++) {
			min = maxVal;

			for (int j = 0; j < size; j++)
				if (min > distance[j] && visited[j] != true) {
					min = distance[j];
					nextNode = j;
				}

			visited[nextNode] = true;
			prevPath = path[nextNode];

			for (int k = 0; k < size; k++) {
				if (visited[k] != true) {
					if (min + arr[nextNode][k] < distance[k]) {
						distance[k] = min + arr[nextNode][k];
						path[k] = prevPath + "," + String.valueOf(k + 1);
					}
				}
			}
		}
		for (int i = 0; i < path.length; i++) {
			if (distance[i] == 999) {
				path[i] = "-";
				distance[i] = 0;
			}
		}
		Object[] obj = new Object[2];
		obj[0] = distance;
		obj[1] = path;
		return obj;
	}

	private static List<Object[]> extractShortestPath(List<List<Integer>> matrix) {
		List<Object[]> resultList = new ArrayList<Object[]>();
		int size = matrix.size();
		int[][] arr = new int[size][size];
		int maxVal = 999;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				arr[i][j] = matrix.get(i).get(j);
				if (arr[i][j] <= 0)
					arr[i][j] = maxVal;
			}
		}
		for (int i = 0; i < matrix.size(); i++) {
			Object[] resultObj = findShortestPathForGivenSource(arr, size, i + 1, maxVal);
			resultList.add(resultObj);
		}
		return resultList;
	}

}
