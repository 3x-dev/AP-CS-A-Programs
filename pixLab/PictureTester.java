/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Method to test keepBlue */
  public static void testKeepOnlyBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.keepBlue();
    beach.explore();
  }
  
  /** Method to test negate */
  public static void testNegate()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.negate();
    beach.explore();
  }
  
  /** Method to test greyscale */
  public static void testGrayscale()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.grayscale();
    beach.explore();
  }
  
  /** Method to test pixelate */
  public static void testPixelate()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.pixelate(15);
    beach.explore();
  }
  
  /** Method to test pixelate */
  public static void testBlur()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
	Picture blurredImage = beach.blur(5);
	blurredImage.explore();
  }
  
  /** Method to test enhance */
  public static void testEnhance()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
	Picture enhancedImage = beach.enhance(20);
	enhancedImage.explore();
  }
  
  /** Method to test swapLeftRight */
  public static void testSwap()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
	Picture swappedImage = beach.swapLeftRight();
	swappedImage.explore();
  }
  
  /** Method to test stairStep */
  public static void testStair()
  {
    Picture motor = new Picture("images/redMotorcycle.jpg");
    motor.explore();
	Picture stairImage = motor.stairStep(10, 10);
	stairImage.explore();
  }
  
  /** Method to test liquify */
  public static void testLiquify()
  {
    Picture swan = new Picture("images/swan.jpg");
    swan.explore();
	Picture liquidImage = swan.liquify(150);
	liquidImage.explore();
  }
  
  /** Method to test wavy */
  public static void testWavy()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
	Picture wavyImage = beach.wavy(100);
	wavyImage.explore();
  }
  
  /** Method to test edgeDetectionBelow */
  public static void testEdge()
  {
    Picture swan = new Picture("images/swan.jpg");
    swan.explore();
	Picture edgeImage = swan.edgeDetectionBelow(10);
	edgeImage.explore();
  }
  
  /** Method to test greenScreen */
  public static void testGreenScreen()
  {
	Picture background = new Picture("images/IndoorJapeneseRoomBackground.jpg");
    Picture greenScreen = background.greenScreen();
    greenScreen.explore();

  }
  
  /** Method to test rotate */
  public static void testRotate()
  {
	Picture beach = new Picture("images/beach.jpg");
    beach.explore();
	Picture rotateImage = beach.rotate(-Math.PI/6);
	rotateImage.explore();
  }
  
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test and comment out the ones you don't want to run
    
    //testZeroBlue();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
    //testPixelate();
    //testBlur();
    //testEnhance();
    //testSwap();
    //testStair();
    //testLiquify();
    //testWavy();
    //testEdge();
    testGreenScreen();
    //testRotate();
  }
}
