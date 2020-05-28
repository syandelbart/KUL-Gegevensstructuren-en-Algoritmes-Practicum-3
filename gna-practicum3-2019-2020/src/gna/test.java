package gna;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import libpract.Position;
import libpract.Stitch;


	public class test {
	  @Test
	  public void test01CallSeam() {
	    int[][] arrayOfInt1 = { { 1, 2 ,3 }, { 4, 8, 6 }, { 7, 8 ,9 } };
	    int[][] arrayOfInt2 = { { 9, 8 ,7 }, { 6, 5, 4 }, { 3, 2 ,1 } };
	    String str1 = "{{1,1}, {50,1}} en {{1,1}, {99,1}}";
	    Stitcher stitcher = new Stitcher();
	    List<Position> list = stitcher.seam(arrayOfInt1, arrayOfInt2);
	    String str2 = String.format("Bij images %s is de lengte van je seam %d. Dit is onmogelijk want zoveel pixels zijn er niet.", new Object[] { str1, Integer.valueOf(list.size()) });
	    Assert.assertTrue(str2, (list.size() <= 4));
	  }
	  
//	  @Test(timeout = 1000L)
//	  public void test02CallFloodfill() {
//	    Stitch[][] arrayOfStitch = { { Stitch.SEAM, Stitch.EMPTY }, { Stitch.EMPTY, Stitch.SEAM } };
//	    String str1 = "{{Stitch.SEAM, Stitch.EMPTY}, {Stitch.EMPTY, Stitch.SEAM}}";
//	    Stitcher stitcher = new Stitcher();
//	    stitcher.floodfill(arrayOfStitch);
//	    String str2 = String.format("Bij floodfill op mask=%s heb je de SEAM op mask[0][0] overschreven.", new Object[] { str1 });
//	    Assert.assertTrue(str2, (arrayOfStitch[0][0] == Stitch.SEAM));
//	    str2 = String.format("Bij floodfill op mask=%s heb je de SEAM op mask[1][1] overschreven.", new Object[] { str1 });
//	    Assert.assertTrue(str2, (arrayOfStitch[1][1] == Stitch.SEAM));
//	  }
//	  
//	  @Test(timeout = 1000L)
//	  public void test03CallSitch() {
//	    int[][] arrayOfInt1 = { { 1, 1 }, { 50, 1 } };
//	    int[][] arrayOfInt2 = { { 1, 1 }, { 99, 1 } };
//	    String str1 = "{{1,1}, {50,1}} en {{1,1}, {99,1}}";
//	    Stitcher stitcher = new Stitcher();
//	    Stitch[][] arrayOfStitch = stitcher.stitch(arrayOfInt1, arrayOfInt2);
//	    String str2 = String.format("Je stitch op %s geeft een mask waarbij mask[0][0] niet gelijk is aan SEAM.", new Object[] { str1 });
//	    Assert.assertTrue(str2, (arrayOfStitch[0][0] == Stitch.SEAM));
//	    str2 = String.format("Je stitch op %s geeft een mask waarbij mask[1][1] niet gelijk is aan SEAM.", new Object[] { str1 });
//	    Assert.assertTrue(str2, (arrayOfStitch[1][1] == Stitch.SEAM));
//	  }


}
