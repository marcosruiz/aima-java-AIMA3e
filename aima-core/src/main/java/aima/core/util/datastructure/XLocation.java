package aima.core.util.datastructure;

/**
 * Note: If looking at a rectangle - the coordinate (x=0, y=0) will be the top
 * left hand corner. This corresponds with Java's AWT coordinate system.
 * 
 * @author Ravi Mohan
 * @author Mike Stampone
 */
public class XLocation {
	public enum Direction {
		OneEast, OneWest, TwoEast, TwoWest, ThreeEast, ThreeWest
	};

	int xCoOrdinate;

	/**
	 * Constructs and initializes a location at the specified (<em>x</em>,
	 * <em>y</em>) location in the coordinate space.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public XLocation(int x) {
		xCoOrdinate = x;
	}

	/**
	 * Returns the X coordinate of the location in integer precision.
	 * 
	 * @return the X coordinate of the location in double precision.
	 */
	public int getXCoOrdinate() {
		return xCoOrdinate;
	}

	/**
	 * Returns the location i units left of this location.
	 * 
	 * @return the location i units left of this location.
	 */
	public XLocation west(int i) {
		return new XLocation(xCoOrdinate - i);
	}

	/**
	 * Returns the location i units right of this location.
	 * 
	 * @return the location i units right of this location.
	 */
	public XLocation east(int i) {
		return new XLocation(xCoOrdinate + i);
	}

	
	/**
	 * Returns the location one unit left of this location.
	 * 
	 * @return the location one unit left of this location.
	 */
	public XLocation left(int i) {
		return west(i);
	}

	/**
	 * Returns the location one unit right of this location.
	 * 
	 * @return the location one unit right of this location.
	 */
	public XLocation right(int i) {
		return east(i);
	}

	/**
	 * Returns the location one unit from this location in the specified
	 * direction.
	 * 
	 * @return the location one unit from this location in the specified
	 *         direction.
	 */
	public XLocation locationAt(Direction direction) {
		if (direction.equals(Direction.OneEast)) {
			return east(1);
		}
		if (direction.equals(Direction.OneWest)) {
			return west(1);
		}
		if (direction.equals(Direction.TwoEast)) {
			return east(2);
		}
		if (direction.equals(Direction.TwoWest)) {
			return west(2);
		}
		if (direction.equals(Direction.OneEast)) {
			return east(3);
		}
		if (direction.equals(Direction.OneWest)) {
			return west(3);
		}else {
			throw new RuntimeException("Unknown direction " + direction);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (null == o || !(o instanceof XLocation)) {
			return super.equals(o);
		}
		XLocation anotherLoc = (XLocation) o;
		return ((anotherLoc.getXCoOrdinate() == xCoOrdinate));
	}

	@Override
	public String toString() {
		return " ( " + xCoOrdinate + " ) ";
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + xCoOrdinate;
		return result;
	}
}