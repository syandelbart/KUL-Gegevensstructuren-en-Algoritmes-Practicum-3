package gna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import libpract.Position;

public class Pixel {
	private int cost;
	private int column;
	private int row;
	private int[][] image1;
	private int[][] image2;
	private Pixel prev = null;
	private int count;
	
	@Override
	public boolean equals(Object y)
	{
		if ( !(y instanceof Pixel) )
			return false;

		Pixel other = (Pixel)y;
		return this.getColumn() == other.getColumn() && this.getRow() == other.getRow();
	}

	// Since we override equals(), we must also override hashCode(). When two objects are
	// equal according to equals() they must return the same hashCode. More info:
	// - http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java/27609#27609
	// - http://www.ibm.com/developerworks/library/j-jtp05273/
    @Override
    public int hashCode()
	{
    	int result = 17;
    	result = result * 7 + this.getColumn();
    	result = result * 3 + this.getRow();
    	return result;
	}
	
	
	public Pixel(int column,int row,int[][] image1, int[][] image2) {
		this.column = column;
		this.row = row;
		this.image1 = image1;
		this.image2 = image2;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int[][] getImage1() {
		return this.image1;
	}
	
	public int[][] getImage2() {
		return this.image2;
	}
	
	
	//since total cost is relative to previous, we can calculate this when we set the previous
	public void setPrev(Pixel prev) {
		this.prev = prev;
		this.cost = ImageCompositor.pixelSqDistance(this.getImage1()[this.getRow()][this.getColumn()],this.getImage2()[this.getRow()][this.getColumn()]) + prev.getTotalCost();
	}
	
	public Pixel getPrev() {
		return this.prev;
	}
	
	
	public int getTotalCost() {
		return this.cost;
	}
 	
	
	
	public List<Pixel> neighbors() {
		List<Pixel> neighbours = new ArrayList<Pixel>();
		int row = this.getRow();
		int column = this.getColumn();
		Pixel cursor = null;
		//check neighbours individually
		//x-left
		if(column - 1 >= 0) {
			cursor = new Pixel(column-1,row,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		//x-right
		if(column + 1 <= this.getImage1()[0].length-1) {
			cursor = new Pixel(column+1,row,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		//y-top
		if(row - 1 >= 0) {
			cursor = new Pixel(column,row-1,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		//y-bot
		if(row + 1 <= this.getImage1().length-1) {
			cursor = new Pixel(column,row+1,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}	
		
		//since we have to consider diagonal pixels as neighbours, we shall also check these
		//lefttop
		if(column - 1 >= 0 && row - 1 >= 0) {
			cursor = new Pixel(column-1,row-1,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		//righttop
		if(column + 1 <= this.getImage1()[0].length-1 && row - 1 >= 0) {
			cursor = new Pixel(column+1,row-1,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		//rightbottom
		if(column + 1 <= this.getImage1()[0].length-1 && row + 1 <= this.getImage1().length-1) {
			cursor = new Pixel(column+1,row+1,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		//leftbottom
		if(column - 1 >= 0 && row + 1 <= this.getImage1().length-1) {
			cursor = new Pixel(column-1,row+1,this.getImage1(),this.getImage2());
			cursor.setPrev(this);
			if(!cursor.equals(this.getPrev())) {
				neighbours.add(cursor);
			}
		}
		
		Iterator<Pixel> pixels = neighbours.iterator();
		//System.out.println("Cursor:"  + this.getColumn() + "-" + this.getRow());
		while(pixels.hasNext()) {
			Pixel resultcursor = pixels.next();
			//System.out.println("Neighbour: " + resultcursor.getColumn() + "-" + resultcursor.getRow() + "--" + resultcursor.getTotalCost());
		}
		//System.out.println("----");
		
		return neighbours;
	}
}
