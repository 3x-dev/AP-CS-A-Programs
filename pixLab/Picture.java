import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 * @author Aryan Singhal
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method to set the red and green to 0 */
  public void keepBlue()
  {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  
  /** Method to invert all the rgb values */
  public void negate()
  {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(pixelObj.getRed() * -1 + 255);
        pixelObj.setGreen(pixelObj.getGreen() * -1 + 255);
        pixelObj.setBlue(pixelObj.getBlue() * -1 + 255);
      }
    }
  }
  
  /** Method to set all rgb values on a gray scale */
  public void grayscale()
  {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		int grey = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue())/3;
        pixelObj.setRed(grey);
        pixelObj.setGreen(grey);
        pixelObj.setBlue(grey);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /**
  * Method to show large changes in color 
  * @param edgeDist the distance for finding edges
  */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for(int row = 0; row < pixels.length; row++)
    {
      for(int col = 0; col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if(leftPixel.colorDistance(rightColor) > edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
	/**
	 * Pixelates a picture
	 * @param size Side length of square area to pixelate.
	 */
	public void pixelate(int size)
	{
		int height = this.getHeight();
		int width = this.getWidth();
		Pixel[][] pixels = this.getPixels2D();

		// Iterate over the pixels
		for(int row = 0; row < height; row += size)
		{
			for(int col = 0; col < width; col += size)
			{
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				int count = 0;

				// Get the average red, green, and blue values of the square
				for(int i = row; i < row + size && i < height; i++)
				{
					for(int j = col; j < col + size && j < width; j++)
					{
						Pixel pixel = pixels[i][j];
						sumRed += pixel.getRed();
						sumGreen += pixel.getGreen();
						sumBlue += pixel.getBlue();
						count++;
					}
				}

				// Set the square to the average color
				int averageRed = sumRed / count;
				int averageGreen = sumGreen / count;
				int averageBlue = sumBlue / count;
				Color averageColor = new Color(averageRed, averageGreen, averageBlue);
				for(int i = row; i < row + size && i < height; i++)
				{
					for(int j = col; j < col + size && j < width; j++)
					{
						Pixel pixel = pixels[i][j];
						pixel.setColor(averageColor);
					}
				}
			}
		}
	}
	
	/**
	 * Blurs a picture
	 * @param size Blur size, greater is more blur
	 * @return Blurred picture
	 */
	public Picture blur(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();

		// loop through all the pixels in the original picture
		for(int row = 0; row < pixels.length; row++)
		{
			for(int col = 0; col < pixels[0].length; col++)
			{
			  // calculate the average RGB values of the surrounding pixels
			  // based on the blur size
			  int redTotal = 0;
			  int greenTotal = 0;
			  int blueTotal = 0;
			  int count = 0;
			  for(int i = row - size; i <= row + size; i++)
			  {
				for(int j = col - size; j <= col + size; j++)
				{
				  if(i >= 0 && i < pixels.length && j >= 0 && j < pixels[0].length)
				  {
					redTotal += pixels[i][j].getRed();
					greenTotal += pixels[i][j].getGreen();
					blueTotal += pixels[i][j].getBlue();
					count++;
				  }
				}
			  }
			  // set the average RGB values for the current pixel in the blurred picture
			  resultPixels[row][col].setRed(redTotal / count);
			  resultPixels[row][col].setGreen(greenTotal / count);
			  resultPixels[row][col].setBlue(blueTotal / count);
			}
		}
		return result;
	}
	
	/**
	 * Enhances a picture by getting average Color around a pixel then applies the following formula:
	 * pixelColor <- 2 * currentValue - averageValue
	 *
	 * size is the area to sample for blur.
	 *
	 * @param size Larger means more area to average around pixel and longer compute time.
	 * @return enhanced picture
	 */
	public Picture enhance(int size)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture result = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = result.getPixels2D();

		for(int row = 0; row < pixels.length; row++)
		{
			for(int col = 0; col < pixels[0].length; col++)
			{
				int averageRed = 0;
				int averageGreen = 0;
				int averageBlue = 0;
				int count = 0;

				// get average RGB values of the surrounding pixels
				for(int i = -size/2; i <= size/2; i++)
				{
					for(int j = -size/2; j <= size/2; j++)
					{
						if(row + i >= 0 && row + i < pixels.length &&
							col + j >= 0 && col + j < pixels[0].length)
						{
							 averageRed += pixels[row + i][col + j].getRed();
							 averageGreen += pixels[row + i][col + j].getGreen();
							 averageBlue += pixels[row + i][col + j].getBlue();
							 count++;
						}
					}
				}

				averageRed /= count;
				averageGreen /= count;
				averageBlue /= count;

				// apply the formula to enhance the pixel
				int enhancedRed = 2 * pixels[row][col].getRed() - averageRed;
				int enhancedGreen = 2 * pixels[row][col].getGreen() - averageGreen;
				int enhancedBlue = 2 * pixels[row][col].getBlue() - averageBlue;

				// set the enhanced values to the result picture
				resultPixels[row][col].setRed(Math.min(Math.max(enhancedRed, 0), 255));
				resultPixels[row][col].setGreen(Math.min(Math.max(enhancedGreen, 0), 255));
				resultPixels[row][col].setBlue(Math.min(Math.max(enhancedBlue, 0), 255));
			}
		}

		return result;
	}
	
	/**
	 * Swap the left and right sides of a picture
	 * @return Picture	the swapped picture
	 */
	public Picture swapLeftRight()
    {
      int height = this.getHeight();
      int width = this.getWidth();
      Pixel[][] pixels = this.getPixels2D();
      Picture newPicture = new Picture(this);
      Pixel[][] resultPixels = newPicture.getPixels2D();

      for(int row = 0;  row < height; row++)
      {
          for(int col = 0; col < width; col++)
          {
              Pixel p = pixels[row][col];
              int newCol = (col + width / 2) % width;
              
              while(newCol >= width)
                  newCol -= width;
              resultPixels[row][newCol].setColor(new Color(p.getRed(),
										p.getGreen(), p.getBlue()));
          }
      }
      return newPicture;
	}
	
	/**
	 * Creates stair steps for a picture. Pixels wrap around.
	 * @return Picture	the new picture
	 */
	public Picture stairStep(int shiftCount, int steps)
	{
      int height = this.getHeight();
      int width = this.getWidth();
      
      Pixel[][] pixels = this.getPixels2D();
      Picture newPicture = new Picture(this);
      Pixel[][] resultPixels = newPicture.getPixels2D();
      
      int step = 1;
      int count = 0;
      
      for(int row = 0;  row < height; row++)
      {
          for(int col = 0; col < width; col++)
          {
              if(height/steps == count)
              {
                  step++;
                  count = 0;
              }
              
              Pixel p = pixels[row][col];
              int newCol = col + shiftCount * step;
              
              while(newCol >= width)
                  newCol -= width;
              
              resultPixels[row][newCol].setColor(new Color(p.getRed(),
										p.getGreen(), p.getBlue()));
          }
          count++;
      }
      return newPicture;
	}
	
	/**
	 * Applies a liquid distortion effect to the picture
	 * @param maxHeight		Max height (shift) of curve in pixels
	 * @return Picture		Liquified picture
	 */
	public Picture liquify(int maxHeight)
	{
		int height = this.getHeight();
		int width = this.getWidth();

		Pixel[][] pixels = this.getPixels2D();
		Picture newPicture = new Picture(this);
		Pixel[][] resultPixels = newPicture.getPixels2D();
		
		int bellWidth = 50;

		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				Pixel p = pixels[row][col];
				
				double exponent = Math.pow(row - this.getHeight() / 2.0, 2) / (2.0 * Math.pow(bellWidth, 2));
				int rightShift = (int)(maxHeight * Math.exp(-exponent));
				int newCol = (col + rightShift) % width;
				
				resultPixels[row][newCol].setColor(new Color(p.getRed(),
										p.getGreen(), p.getBlue()));
			}
		}
		return newPicture;
	}
	
	/**
	 * Applies a sinusoidal wavy distortion effect to the picture
	 * @param amplitude		Amplitude of wavy distortion of the sinusoidal function
	 * @return Picture		Wavy picture
	 */
	public Picture wavy(int amplitude)
	{
		int height = this.getHeight();
		int width = this.getWidth();

		Pixel[][] pixels = this.getPixels2D();
		Picture newPicture = new Picture(this);
		Pixel[][] resultPixels = newPicture.getPixels2D();
		
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				Pixel p = pixels[row][col];
				int phi = 0;
				double freq = 1.0/(240);
				int shift = (int)(amplitude * Math.sin(2 * Math.PI * freq * row + phi));
				int newCol = col + shift;
				
				while(newCol >= width)
					newCol -= width;
					
				while(newCol < 0)
					newCol += width;
				
				resultPixels[row][newCol].setColor(new Color(p.getRed(),
										p.getGreen(), p.getBlue()));
			}
		}
		
		return newPicture;
	}
	
	/**
	 * Applies edge detection to an image
	 * @param threshold 	Threshold for RGB color distance
	 * @return Picture 		Edge detected picture
	 */
	public Picture edgeDetectionBelow(int threshold)
	{
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color bottomColor = null;
		Picture newPicture = new Picture(this);
		Pixel[][] resultPixels = newPicture.getPixels2D();
		
		int topPixelRed = 0;
		int topPixelBlue = 0;
		int topPixelGreen = 0;
		
		for(int col = 0; col < pixels[0].length; col++)
		{
		  for(int row = 0; row < pixels.length - 1; row++)
		  {
			topPixel = pixels[row][col];
			bottomPixel = pixels[row + 1][col];
			bottomColor = bottomPixel.getColor();
			
			if(topPixel.colorDistance(bottomColor) > threshold)
				resultPixels[row][col].setColor(Color.BLACK);
			else
				resultPixels[row][col].setColor(Color.WHITE);
			
		  }
		  resultPixels[pixels.length - 1][col].setColor(Color.WHITE);
		  	  
		}
		
		return newPicture;
	}
	
	/**
	 * Greenscreens images by calling superImpose method
	 * @param amplitude 	Amplitude of wavy distortion of the sinusoidal function
	 * @return Picture 		Setting with green screened images
	 */
	public Picture greenScreen()
	{
		Picture puppy = new Picture("images/puppy1GreenScreen.jpg");
		Picture kitten = new Picture("images/kitten2greenScreen.jpg");
		
		superImpose(puppy, 350, 50, 0.8, 80);
		superImpose(kitten, 350, 500, 0.25, 80);

		return this;
	}
	/**
	 * Superimposes images on a background. Removes the green background pixels (greenscreen)
	 * @param image		Image to be superimposed
	 * @param x			X coordinate to place the image on background imagbe
	 * @param y			Y coordinate to place the image on background imagbe
	 * @param f			Factor to scale down image (between 0 - 1)
	 * @param threshold		Threshold for RGB color distance
	 */
	public void superImpose(Picture image, int x, int y, double factor, int threshold)
	{
		int height = this.getHeight();
		int width = this.getWidth();
		
		Pixel[][] imagePixels = image.getPixels2D();
		Pixel greenP = imagePixels[0][0];
		Pixel[][] backPixels = this.getPixels2D();
		
		Color BACK_GREEN = new Color(greenP.getRed(), greenP.getGreen(), greenP.getBlue());
		
		if(x > height || y > width)
			return;
		
		for(int row = 0; row < image.getHeight() * factor; row++)
		{
			for(int col = 0; col < image.getWidth() * factor; col++)
			{
				Pixel imageP = imagePixels[(int)(row/factor)][(int)(col/factor)];
				if(imageP.colorDistance(BACK_GREEN) > threshold)
				{
					Pixel backP = backPixels[row + x][col + y];
					backP.setColor(imageP.getColor());
				}
			}
		}
	}
	
	/**
	 * Rotates images by theta
	 * @param theta		amount to rotate in radians
	 * @return Picture	rotated picture
	 */
	public Picture rotate(double theta)
	{
		int height = this.getHeight();
		int width = this.getWidth();

		Pixel[][] pixels = this.getPixels2D();
		Picture newPicture = new Picture(this);
		Pixel[][] resultPixels = newPicture.getPixels2D();
		
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				resultPixels[row][col].setColor(Color.BLACK);
			}
		}
		
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				Pixel p = pixels[row][col];
				
				int x1 = (int)(row * Math.cos(theta) - col * Math.sin(theta));
				int y1 = (int)(row * Math.sin(theta) + col * Math.cos(theta));
				
				if((x1 >= 0 && x1 < height) && (y1 >= 0 && y1 < width))
				{
					resultPixels[x1][y1].setColor(new Color(p.getRed(),
										p.getGreen(), p.getBlue()));
				}
			}
		}
		
		return newPicture;
	}

	/* Main method for testing - each class in Java can have a main 
	* method 
	*/
	public static void main(String[] args)
	{
		Picture beach = new Picture("images/beach.jpg");
		beach.explore();		
	}
} // this } is the end of class Picture, put all new methods before this
