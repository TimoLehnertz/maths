package maths;

public class EulerRotation extends Rotation {
	
	EulerRotationMode mode;
	
	public EulerRotation() {
		this(0, 0, 0);
	}
	
	public EulerRotation(Vec3 v) {
		this(v.x, v.y, v.z);
	}
	
	public EulerRotation(Vec3 v, EulerRotationMode mode) {
		this(v.x, v.y, v.z, mode);
	}
	
	public EulerRotation(double x, double y, double z) {
		this(x, y, z, EulerRotationMode.XYZ_EULER);
	}
	
	public EulerRotation(double x, double y, double z, EulerRotationMode mode) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
	}
	
//	public double dot(EulerRotation r) {
//		Vec3 v1 = new Vec3(1, 0, 0);
//		Vec3 v2 = new Vec3(1, 0, 0);
//		rotate(v1);
//		r.rotate(v2);
//		return v1.dotProduct(v2);
//	}
	
	
	public double[][] getMatrix() {
		double[][] xMat = {
				{1, 0, 0},
				{0, Math.cos(x), -Math.sin(x)},
				{0, Math.sin(x), Math.cos(x)}
		};
		double[][] yMat = {
				{Math.cos(y), 0, Math.sin(y)},
				{0, 1, 0},
				{-Math.sin(y), 0, Math.cos(y)}
		};
		double[][] zMat = {
				{Math.cos(z), -Math.sin(z), 0},
				{Math.sin(z), Math.cos(z), 0},
				{0, 0, 1},
		};
		switch(mode) {
		case XYZ_EULER: return Matrix.multiply(Matrix.multiply(xMat, yMat), zMat);
		case XZY_EULER: return Matrix.multiply(Matrix.multiply(xMat, zMat), yMat);
		case YXZ_EULER: return Matrix.multiply(Matrix.multiply(yMat, xMat), zMat);
		case YZX_EULER: return Matrix.multiply(Matrix.multiply(yMat, zMat), xMat);
		case ZXY_EULER: return Matrix.multiply(Matrix.multiply(zMat, xMat), yMat);
		case ZYX_EULER: return Matrix.multiply(Matrix.multiply(zMat, yMat), xMat);
		default: return null;
		}
	}

	@Override
	public void rotate(Vec3 vec) {
		vec.setFrom(Matrix.multiply(getMatrix(), vec.getMatrix()));
	}

	@Override
	public void rotateReverse(Vec3 vec) {
		vec.setFrom(Matrix.multiply(Matrix.getTranspose(getMatrix()), vec.getMatrix()));
	}

	@Override
	public Rotation clone() {
		EulerRotation clone = new EulerRotation(x, y, z, mode);
		return clone;
	}

	@Override
	public void rotateBy(Rotation r) {
		// TODO Auto-generated method stub
		
	}

	public EulerRotationMode getMode() {
		return mode;
	}

	public void setMode(EulerRotationMode mode) {
		this.mode = mode;
	}
	
	public double getPitch() {
		if(mode != EulerRotationMode.ZYX_EULER) throw new RuntimeException("Pitch yaw and roll only works with ZYX Eulers");
		return y;
	}
	
	public double getYaw() {
		if(mode != EulerRotationMode.ZYX_EULER) throw new RuntimeException("Pitch yaw and roll only works with ZYX Eulers");
		return z;
	}

	public double getRoll() {
		if(mode != EulerRotationMode.ZYX_EULER) throw new RuntimeException("Pitch yaw and roll only works with ZYX Eulers");
		return x;
	}

	@Override
	public String getName() {
		switch(mode) {
		case XYZ_EULER: return "Euler XYZ";
		case XZY_EULER: return "Euler XZY";
		case YXZ_EULER: return "Euler YXZ";
		case YZX_EULER: return "Euler YZX";
		case ZXY_EULER: return "Euler ZXY";
		case ZYX_EULER: return "Euler ZYX";
		default: return null;
		}
	}
}