package maths;

/**
 * @todo http://www.chrobotics.com/library/understanding-quaternions
 * @author Timo Lehnertz
 *
 */
public class Vec3 implements Matrixable {
	
	public double x, y, z;

	public Vec3(double val) {
		this(val,val,val);
	}
	
	public Vec3() {
		this(0,0,0);
	}
	
	/**
	 * from matrix
	 * @param multiply
	 */
	public Vec3(double[][] mat) {
		this(mat[0][0], mat[1][0], mat[2][0]);
	}
	
	/**
	 * from vec
	 * @param multiply
	 */
	public Vec3(double[] vec) {
		this(vec[0], vec[1], vec[2]);
	}
	
	public Vec3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(String s) {
		super();
		setFromString(s);
	}

	public double getLength() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public double getDistanceFrom(Transform transform) {
		return getDistanceFrom(transform.getLoc());
	}
	
	public double getDistanceFrom(Vec3 vec) {
		return vec.clone().subtract(this).getLength();
	}
	
	public Vec3 add(double val) {
		x+=val;
		y+=val;
		z+=val;
		return this;
	}
	
	public double dotProduct(Vec3 vec) {
		Vec3 a = clone();//.toUnitLength();
		Vec3 b = vec.clone();//.toUnitLength();
		return a.multiply(b).getValue();//
	}
	
	public Vec3 crossProduct(Vec3 v) {
		Vec3 cross = new Vec3();
		cross.x = y * v.z - z * y;
		cross.y = -(x * v.z - z * x);
		cross.z = x * v.y - y * v.x;
		return cross;
	}
	
	public Vec3 add(Vec3 vec) {
		x+=vec.x;
		y+=vec.y;
		z+=vec.z;
		return this;
	}
	
	public Vec3 subtract(double val) {
		x-=val;
		y-=val;
		z-=val;
		return this;
	}
	
	public Vec3 subtract(Vec3 vec) {
		x-=vec.x;
		y-=vec.y;
		z-=vec.z;
		return this;
	}
	
	public Vec3 multiply(double val) {
		x*=val;
		y*=val;
		z*=val;
		return this;
	}
	
	public Vec3 multiply(Vec3 vec) {
		x*=vec.x;
		y*=vec.y;
		z*=vec.z;
		return this;
	}
	
	public Vec3 divide(double val) {
		x/=val;
		y/=val;
		z/=val;
		return this;
	}
	
	public Vec3 divide(Vec3 vec) {
		x/=vec.x;
		y/=vec.y;
		z/=vec.z;
		return this;
	}
	
	public Vec3 pow(double d) {
		this.x = Math.pow(x, d);
		this.y = Math.pow(y, d);
		this.z = Math.pow(z, d);
		return this;
	}
	
	public Vec3 rotate(Vec3 rot) {
		return rotate(rot.x, rot.y, rot.z);
	}
	
	public Vec3 rotateReverse(Vec3 rot) {
		rotateZ(rot.z);
		rotateY(rot.y);
		rotateX(rot.x);
		return this;
	}
	
	public Vec3 rotate(double degX, double degY, double degZ) {
		return rotate(degX, degY, degZ, true);
	}
	
	public Vec3 rotate(double degX, double degY, double degZ, boolean cw) {
		rotateX(degX, cw);
		rotateY(degY, cw);
		rotateZ(degZ, cw);
		return this;
	}
	
	public Vec3 rotateX(double rad) {
		return rotateX(rad, true);
	}
	
	public Vec3 rotateX(double rad, boolean cw) {
		double radius = Math.sqrt(y*y + z*z);
		double theta = Math.atan2(z, y);
		theta += rad * (cw?-1:1);
		y = radius * Math.cos(theta);
		z = radius * Math.sin(theta);
		return this;
	}
	
	public Vec3 rotateY(double rad) {
		return rotateY(rad, true);
	}
	
	public Vec3 rotateY(double rad, boolean cw) {
		double radius = Math.sqrt(x*x + z*z);
		double theta = Math.atan2(x, z);
		theta +=  rad * (cw?-1:1);
		x = radius * Math.sin(theta);
		z = radius * Math.cos(theta);
		return this;
	}
	
	public Vec3 rotateHeight(double deg, double max) {
		deg *= -1;
		double height = getHeightRot();
		double newRot = height + deg;
		newRot = Math.min(max, Math.max(-max, newRot));
		deg = (height - newRot);
		double zRot = getZRot();
		rotateZ(zRot);
		rotateY(deg);
		rotateZ(-zRot);
		return this;
	}
	
	public Vec3 rotateHeight(double deg) {
		double zRot = getZRot();
		rotateZ(zRot);
		rotateY(deg);
		rotateZ(-zRot);
		return this;
	}
	
	public double getHeightRot() {
		double zRot = getZRot();
		Vec3 clone = clone();
		clone.rotateZ(zRot);
		return clone.getYRot();
	}
	
	public double getXRot() {
		Vec3 forward = new Vec3(0, 1, 0);
		Vec3 clone = clone();
		clone.x = 0;
		double rot = clone.rotFrom(forward);
		rot *= z > 0 ? -1 : 1;
		return rot;
	}
	
	public double getYRot() {
		Vec3 forward = new Vec3(1, 0, 0);
		Vec3 clone = clone();
		clone.y = 0;
		double rot = clone.rotFrom(forward);
		rot *= z > 0 ? -1 : 1;
		return rot;
	}
	
	public double getZRot() {
		Vec3 forward = new Vec3(1, 0, 0);
		Vec3 clone = clone();
		clone.z = 0;
		double rot = clone.rotFrom(forward);
		rot *= y < 0 ? -1 : 1;
		return rot;
	}

	
	public double rotFrom(Vec3 vec) {
		return Math.acos((clone().multiply(vec).getValue()) / (getLength() * vec.getLength()));
	}
	
	public double getValue() {
		return x + y + z;
	}
	
	public Vec3 rotateZ(double rad) {
		return rotateZ(rad, true);
	}
	
	public Vec3 rotateZ(double rad, boolean cw) {
		double radius = Math.sqrt(y*y + x*x);
		double theta = Math.atan2(y, x);
		theta += rad * (cw?-1:1);
		y = radius * Math.sin(theta);
		x = radius * Math.cos(theta);
		return this;
	}
	
	public Rotation rotToZUp(Vec3 center) {
		EulerRotation rot = new EulerRotation();
		Vec3 diff = center.clone().subtract(this);
		double zRot = diff.getZRot();
		rot.z = zRot;
		diff.rotateZ(zRot);
		double yRot = diff.getYRot();
		rot.y = yRot;
		return rot;
	}
	
	public Vec3 toUnitLength() {
		return divide(getLength());
	}
	
	public double getX() {
		return x;
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
	
	public Vec3 setFrom(Rotation v) {
		x = v.x;
		y = v.y;
		z = v.z;
		return this;
	}
	
	public Vec3 setFrom(Vec3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
		return this;
	}
	
	public Vec3 setFrom(double[] vec) {
		x = vec[0];
		y = vec[1];
		z = vec[2];
		return this;
	}
	
	public Vec3 setFrom(double[][] vec) {
		x = vec[0][0];
		y = vec[1][0];
		z = vec[2][0];
		return this;
	}
	
	public Vec3 toDegrees() {
		x = Math.toDegrees(x);
		y = Math.toDegrees(y);
		z = Math.toDegrees(z);
		return this;
	}
	
	public Vec3 toRadians() {
		x = Math.toRadians(x);
		y = Math.toRadians(y);
		z = Math.toRadians(z);
		return this;
	}
	
	public boolean equals(Vec3 v) {
		return v.x == x && v.y == y && v.z == z;
	}
	
	public void set(int axis, double val) {
		switch(axis) {
		case 0: x = val; return;
		case 1: y = val; return;
		case 2: z = val; return;
		default: return;
		}
	}
	
	public void setFromString(String s) {
		s = s.substring(1, s.length() - 1);
		String split[] = s.split("\\|");
		if(split.length > 2) {
			x = Double.parseDouble(split[0]);
			y = Double.parseDouble(split[1]);
			z = Double.parseDouble(split[2]);
		}
	}

	@Override
	public Vec3 clone() {
		return new Vec3(x, y, z);
	}


	@Override
	public boolean isMatrix() {
		return true;
	}

	@Override
	public double[][] getMatrix() {
		double[][] matrix = {{x}, {y}, {z}};
		return matrix;
	}

	@Override
	public double[] getVec() {
		double[] vec = {x, y, z};
		return vec;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Matrixable> T constructNewFromVec(double[] vec) {
		return (T) new Vec3(vec);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Matrixable> T constructNewFromMatrix(double[][] mat) {
		return (T) new Vec3(mat);
	}
	
	@Override
	public String toString() {
		double precision = 1000D;
		return "(" + Math.round(x * precision) / precision + "," + Math.round(y * precision) / precision + "," + Math.round(z * precision) / precision + ")";
	}
}