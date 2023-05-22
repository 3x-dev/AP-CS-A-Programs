public class IntArrayWorker
{
  /** two dimensional matrix */
  private int[][] matrix = null;
  
  /** set the matrix to the passed one
    * @param theMatrix the one to use
    */
  public void setMatrix(int[][] theMatrix)
  {
    matrix = theMatrix;
  }
  
  /**
   * Method to return the total 
   * @return the total of the values in the array
   */
  public int getTotal()
  {
    int total = 0;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        total = total + matrix[row][col];
      }
    }
    return total;
  }
  
  /**
   * Method to return the total using a nested for-each loop
   * @return the total of the values in the array
   */
  public int getTotalNested()
  {
    int total = 0;
    for (int[] rowArray : matrix)
    {
      for (int item : rowArray)
      {
        total = total + item;
      }
    }
    return total;
  }
  
  /**
   * Method to fill with an increasing count
   */
  public void fillCount()
  {
    int numCols = matrix[0].length;
    int count = 1;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < numCols; col++)
      {
        matrix[row][col] = count;
        count++;
      }
    }
  }
  
  /**
   * print the values in the array in rows and columns
   */
  public void print()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print( matrix[row][col] + " " );
      }
      System.out.println();
    }
    System.out.println();
  }
  
  
  /** 
   * fill the array with a pattern
   */
  public void fillPattern1()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; 
           col++)
      {
        if (row < col)
          matrix[row][col] = 1;
        else if (row == col)
          matrix[row][col] = 2;
        else
          matrix[row][col] = 3;
      }
    }
  }
  
  /**
  * returns	the	count of the number	of times a passed integer value is found in the	matrix
  * @param target	counts the number of occurrences of target in the 2D array matrix.
  * @return			the	count of the number	of times a passed integer value is found in the	matrix
  */
  public int getCount(int target)
  {
     int count = 0;
     for(int i = 0; i < matrix.length; i++)
     {
		 for(int j = 0; j < matrix[i].length; j++)
		 {
			 if(matrix[i][j] == target)
				count++;
		 }
     }
     return count;
  }
  
	/**
	* returns the largest value in the matrix
	* @return the largest value in the matrix
	*/
	public int getLargest()
	{
		int largest = Integer.MIN_VALUE;
		for (int row = 0; row < matrix.length; row++)
		{
			for (int col = 0; col < matrix[0].length; col++)
			{
				if (matrix[row][col] > largest)
					largest = matrix[row][col];
			}
		}
		return largest;
	}
	
	/**
	* returns the total of all the integers in the specified column of the matrix
	* @param col the column to find the total of
	* @return the total of all the integers in the specified column of the matrix
	*/
	public int getColTotal(int col)
	{
		int total = 0;
		for (int row = 0; row < matrix.length; row++)
			total = total + matrix[row][col];
		return total;
	}
	
	/**
	 * Reverses the integers in each row.
	 * Swaps the last integer with the first, the second-to-last with the second, and so forth.
	 */
	public void reverseRows()
	{
		for(int[] row : matrix)
		{
			int left = 0;
			int right = row.length - 1;
			while (left < right)
			{
				int temp = row[left];
				row[left] = row[right];
				row[right] = temp;
				left++;
				right--;
			}
		}
	}
}
