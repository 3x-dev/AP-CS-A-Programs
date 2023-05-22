import java.util.List;
import java.util.ArrayList;
/**
 *	SortMethods - Sorts an array of Strings in ascending order.
 *
 *	@author Aryan Singhal
 *	@since	December 5, 2022
 */
public class SortMethods
{	
	/** Wrapper method for mergeSort */
	public void mergeSort(List<String> arr)
	{
		mergeSort(arr, 0, arr.size()-1);
	}
	
	/**
	 * Merge sort algorithim for words in ascending order
	 * @param arr		array of String objects to sort
	 * @param l			first index in current array
	 * @param r			last index in current array
	 */
	public void mergeSort(List<String> arr, int l, int r)
	{
		if (l < r)
		{
			// Find the middle point
			int m = (l+r)/2;

			// Sort first and second halves
			mergeSort(arr, l, m);
			mergeSort(arr, m+1, r);

			// Merge the sorted halves
			mergeWord(arr, l, m, r);
		}
	}
	
	/**
	 * Merge sort algorithim for words
	 * @param arr		array of String objects to sort
	 * @param l			first index in current array
	 * @param m			middle index of current array to be sorted
	 * @param r			last index in current array
	 */
	public void mergeWord(List<String> arr, int l, int m, int r)
	{
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		List<String> L = new ArrayList<String>();
		List<String> R = new ArrayList<String>();

		/*Copy data to temp arrays*/
		for (int i=0; i<n1; i++)
			L.add(arr.get(l + i));
		for (int j=0; j<n2; j++)
			R.add(arr.get(m + 1 + j));

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = l;
		while (i < n1 && j < n2)
		{
			if (L.get(i).compareTo(R.get(j)) <= 0)
			{
				arr.set(k, L.get(i));
				i++;
			}
			else
			{
				arr.set(k, R.get(j));
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1)
		{
			arr.set(k, L.get(i));
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2)
		{
			arr.set(k, R.get(j));
			j++;
			k++;
		}
	}
}
