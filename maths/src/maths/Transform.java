package maths;

/**
 * Scale, Rotate, Translate
 * @author Timo Lehnertz
 *
 */
public class Transform {
	
	protected Vec3 loc;
	protected Vec3 scale;
	protected Rotation rot;
	protected boolean isCameraTransform = false;
	protected boolean reverseRotation = false;
	
	public Transform() {
		this(new Vec3());
	}
	
	public Transform(Vec3 loc) {
		this(loc, new EulerRotation(), new Vec3(1,1,1));
	}
	
	public Transform(Vec3 loc, Rotation rot, Vec3 scale) {
		super();
		this.loc = loc;
		this.rot = rot;
		this.scale = scale;
	}
	
	public void toSpace(Vec3 t) {
		toSpace(t, false);
	}
	
	public Vec3 toSpace(Vec3 t, boolean ignoreTranslation) {
		if(isCameraTransform) {
			if(!ignoreTranslation) {
				t.subtract(loc);
			}
			rot.rotateReverse(t);
			t.multiply(scale);
		} else {
			t.multiply(scale);
			rot.rotate(t);
			if(!ignoreTranslation) {				
				t.add(loc);
			}
		}
		return t;
	}
	
	public void setTransformFrom(Transform t) {
		this.loc = t.loc.clone();
		this.scale = t.scale.clone();
		this.rot = t.rot.clone();
	}

	public Vec3 getLoc() {
		return loc;
	}

	public void setLoc(Vec3 loc) {
		this.loc = loc;
	}

	public Rotation getRot() {
		return rot;
	}

	public void setRot(Rotation rot) {
		this.rot = rot;
	}

	public Vec3 getScale() {
		return scale;
	}

	public void setScale(double x, double y, double z) {
		setScale(new Vec3(x, y, z));
	}
	
	public void setScale(double scale) {
		setScale(new Vec3(scale, scale, scale));
	}
	
	public boolean isReverseRotation() {
		return reverseRotation;
	}

	public void setReverseRotation(boolean reverseRotation) {
		this.reverseRotation = reverseRotation;
	}

	public void setScale(Vec3 scale) {
		this.scale = scale;
	}
	
	public boolean isCameraTransform() {
		return isCameraTransform;
	}

	public void setCameraTransform(boolean isCameraTransform) {
		this.isCameraTransform = isCameraTransform;
	}
	
	public Transform getTransform() {
		return this;
	}

	@Override
	protected Transform clone(){
		return new Transform(loc.clone(), rot.clone(), scale.clone());
	}
}