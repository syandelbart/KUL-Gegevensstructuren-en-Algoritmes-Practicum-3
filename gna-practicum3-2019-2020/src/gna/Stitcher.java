package gna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import libpract.*;

/**
 * Implement the methods stitch, seam and floodfill.
 */
public class Stitcher
{
	/**
	 * Return the sequence of positions on the seam. The first position in the
	 * sequence is (0, 0) and the last is (width - 1, height - 1). Each position
	 * on the seam must be adjacent to its predecessor and successor (if any).
	 * Positions that are diagonally adjacent are considered adjacent.
	 * 
	 * image1 and image2 are both non-null and have equal dimensions.
	 *
	 * Remark: Here we use the default computer graphics coordinate system,
	 *   illustrated in the following image:
	 * 
	 *        +-------------> X
	 *        |  +---+---+
	 *        |  | A | B |
	 *        |  +---+---+
	 *        |  | C | D |
	 *        |  +---+---+
	 *      Y v 
	 * 
	 *   The historical reasons behind using this layout is explained on the following
	 *   website: http://programarcadegames.com/index.php?chapter=introduction_to_graphics
	 * 
	 *   Position (y, x) corresponds to the pixels image1[y][x] and image2[y][x]. This
	 *   convention also means that, when an automated test mentioned that it used the array
	 *   {{A,B},{C,D}} as a test image, this corresponds to the image layout as shown in
	 *   the illustration above.
	 */
	public List<Position> seam(int[][] image1, int[][] image2) {
		int max_column = image1[0].length - 1;
		int max_row = image1.length - 1;
		Set<Pixel> visited_neighbours = new HashSet<Pixel>();
		PixelComparator pixelComparer = new PixelComparator();
		PriorityQueue<Pixel> priority_queue = new PriorityQueue<Pixel>(4,pixelComparer);
		//adding first element
		Pixel initial = new Pixel(0,0,image1,image2);
		priority_queue.add(initial);
		
		while(priority_queue.peek().getColumn() < max_column || priority_queue.peek().getRow() < max_row) {
			//System.out.println("XXX" + priority_queue.peek().getColumn() + "-" + priority_queue.peek().getRow());
			Pixel first_element_of_pq = priority_queue.poll();
			//System.out.println(first_element_of_pq.getRow() + "," + first_element_of_pq.getColumn());
			visited_neighbours.add(first_element_of_pq);
			Collection<Pixel> neighbours_of_fe_pq = first_element_of_pq.neighbors();
			Iterator<Pixel> neighbourIterator = neighbours_of_fe_pq.iterator();
			while(neighbourIterator.hasNext()) {
				Pixel neighbour_cursor = neighbourIterator.next();
				if(!visited_neighbours.contains(neighbour_cursor) && !priority_queue.contains(neighbour_cursor)) {
					//System.out.println("ActuallyAddedNeighbour" + neighbour_cursor.getColumn() + "-" + neighbour_cursor.getRow());
					
					priority_queue.add(neighbour_cursor);

				}
			}
		}
		//System.out.println("THEEND");
		Pixel solution_last_el = priority_queue.peek();
		List<Position> result = new ArrayList<Position>();
		Pixel cursor = solution_last_el;
		while(cursor != null) {
			result.add(new Position(cursor.getRow(),cursor.getColumn()));
			cursor = cursor.getPrev();
		}
		Collections.reverse(result);
		Iterator<Position> positions = result.iterator();
		while(positions.hasNext()) {
			Position resultcursor = positions.next();
		}
		return result;
		
		
	}

	/**
	 * Apply the floodfill algorithm described in the assignment to mask. You can assume the mask
	 * contains a seam from the upper left corner to the bottom right corner. The seam is represented
	 * using Stitch.SEAM and all other positions contain the default value Stitch.EMPTY. So your
	 * algorithm must replace all Stitch.EMPTY values with either Stitch.IMAGE1 or Stitch.IMAGE2.
	 *
	 * Positions left to the seam should contain Stitch.IMAGE1, and those right to the seam
	 * should contain Stitch.IMAGE2. You can run `ant test` for a basic (but not complete) test
	 * to check whether your implementation does this properly.
	 */
	public void floodfill(Stitch[][] mask) {
		for(int y=0;y<mask.length;y++) {
			boolean seam_switch = false;
			for(int x=0;x<mask[0].length;x++) {
				if(mask[y][x] == Stitch.SEAM) {
					if(x>0) {
						if(mask[y][x-1] != Stitch.SEAM) {
							seam_switch = !seam_switch;
						}
					}

				} else if(mask[y][x] == Stitch.EMPTY) {
					if(seam_switch) {
						mask[y][x] = Stitch.IMAGE2;
					} else {
						mask[y][x] = Stitch.IMAGE1;
					}
				}
			}
		}
	}

	/**
	 * Return the mask to stitch two images together. The seam runs from the upper
	 * left to the lower right corner, where in general the rightmost part comes from
	 * the second image (but remember that the seam can be complex, see the spiral example
	 * in the assignment). A pixel in the mask is Stitch.IMAGE1 on the places where
	 * image1 should be used, and Stitch.IMAGE2 where image2 should be used. On the seam
	 * record a value of Stitch.SEAM.
	 * 
	 * ImageCompositor will only call this method (not seam and floodfill) to
	 * stitch two images.
	 * 
	 * image1 and image2 are both non-null and have equal dimensions.
	 */
	public Stitch[][] stitch(int[][] image1, int[][] image2) {
		System.out.println("started seam");
		List<Position> seam = this.seam(image1,image2);
		System.out.println("escaped seam");
		Stitch[][] mask = new Stitch[image1.length][image1[0].length];
		Iterator<Position> seamIterator = seam.iterator();
		for(int y=0;y<image1.length;y++) {
			for(int x=0;x<image1[0].length;x++) {
				mask[y][x] = Stitch.EMPTY;
			}
		}
		while(seamIterator.hasNext()) {
			Position seamCursor = seamIterator.next();
			mask[seamCursor.getY()][seamCursor.getX()] = Stitch.SEAM;
		}
		floodfill(mask);
		return mask;
	}
}


