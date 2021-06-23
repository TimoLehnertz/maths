package maths;

public abstract class Rotation {

	public double w, x, y, z;
	
	public Rotation() {
		this(0, 0, 0);
	}
	
	public Rotation(double x, double y, double z) {
		this(x, y, z, 1);
	}
	
	public Rotation(double w, double x, double y, double z) {
		super();
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public abstract void rotate(Vec3 vec);
	
	public abstract void rotateReverse(Vec3 vec);
	
	public abstract Rotation clone();
	
	public abstract void rotateBy(Rotation r);

	public double getX() {
		return x;
	}
	
	public Vec3 getV() {
		return new Vec3(x, y, z);
	}
	
	public void setFrom(Vec3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}
	
	public abstract String getName();
	
	@Override
	public String toString() {
		return getName() + " (w=" + w +"|x=" + x + "|y=" + y + "|z="+ z +  ")";
	}
}