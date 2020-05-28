package gna;
import java.util.Comparator;

public class PixelComparator implements Comparator<Pixel> {
	@Override
	public int compare(Pixel pixel1,Pixel pixel2) {
		int pixel1Cost = pixel1.getTotalCost();
		int pixel2Cost = pixel2.getTotalCost();
		if(pixel1Cost > pixel2Cost) {
			return 1;
		} else if(pixel1Cost < pixel2Cost) {
			return -1;
		} else {
			return 0;
		}
	}
}
