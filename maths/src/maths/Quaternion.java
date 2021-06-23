package maths;

public class Quaternion extends Rotation{
	
	public Quaternion() {
		super();
	}
	
	public Quaternion(Vec3 v) {
		super();
		setFrom(v);
	}
	
	public Quaternion(Vec3 n, double theta) {
		super();
		setFrom(n, theta);
		normalise();
	}
	
	public Quaternion(EulerRotation euler) {
		super();
		setFromEuler(euler);
	}
	
	public Quaternion(double[] vec) {
		this(vec[0], vec[1], vec[2], vec[3]);
	}
	
	public Quaternion(double[][] mat) {
		this(mat[0][0], mat[1][0], mat[2][0], mat[3][0]);
	}
	
	public Quaternion(double w, double x, double y, double z) {
		super(w, x, y, z);
	}
	
	public void setFrom(Vec3 n, double theta) {
		theta *= 1;
		w = Math.cos(theta / 2);
		n.multiply(Math.sin(theta / 2));
		x = n.x;
		y = n.y;
		z = n.z;
	}
	
	public void setFromEuler(EulerRotation euler) {
		switch(euler.mode) {
		case XYZ_EULER:
			double roll = euler.x;
			double pitch = euler.y;
			double yaw = euler.z;
			
			x = Math.sin(roll/2) * Math.cos(pitch/2) * Math.cos(yaw/2) - Math.cos(roll/2) * Math.sin(pitch/2) * Math.sin(yaw/2);
		    y = Math.cos(roll/2) * Math.sin(pitch/2) * Math.cos(yaw/2) + Math.sin(roll/2) * Math.cos(pitch/2) * Math.sin(yaw/2);
		    z = Math.cos(roll/2) * Math.cos(pitch/2) * Math.sin(yaw/2) - Math.sin(roll/2) * Math.sin(pitch/2) * Math.cos(yaw/2);
		    w = Math.cos(roll/2) * Math.cos(pitch/2) * Math.cos(yaw/2) + Math.sin(roll/2) * Math.sin(pitch/2) * Math.sin(yaw/2);
		    break;
		case ZYX_EULER:
			double cy = Math.cos(euler.z * 0.5);
		    double sy = Math.sin(euler.z * 0.5);
		    double cp = Math.cos(euler.y * 0.5);
		    double sp = Math.sin(euler.y * 0.5);
		    double cr = Math.cos(euler.x * 0.5);
		    double sr = Math.sin(euler.x * 0.5);

		    w = cr * cp * cy + sr * sp * sy;
		    x = sr * cp * cy - cr * sp * sy;
		    y = cr * sp * cy + sr * cp * sy;
		    z = cr * cp * sy - sr * sp * cy;
//		    y *= -1;
//		    z *= -1;
			break;
			default: throw new RuntimeException("conversion from " + euler.mode + " to quaternion is not implemented yet");
		}
	    normalise();
	}
	
	public Quaternion negate() {
		x *= -1;
		y *= -1;
		z *= -1;
		w *= -1;
		return this;
	}
	
	public Quaternion calibrate() {
		return calibrate(88);// deg
	}
	
	public Quaternion calibrate(double limit) {
		EulerRotation r = toEulerZYX();
		if(Math.toDegrees(r.y) > -limit && Math.toDegrees(r.y) < limit) {
//			if(r.x < 0) r.x = 2 * Math.PI + r.x;
//			if(r.y < 0) r.y = 2 * Math.PI + r.y;
//			if(r.z < 0) r.z = 2 * Math.PI + r.z;
			this.setFromEuler(new EulerRotation(r.x, -r.y, -r.z));
		}
		return this;
	}
	
	public EulerRotation toEulerZYX() {
	    double t0 = 2.0 * (w * x + y * z);
	    double t1 = 1.0 - 2.0 * (x * x + y * y);
	    double roll = Math.atan2(t0, t1);
	    
	    double t2 = 2.0 * (w * y - z * x);
	    t2 = t2 > 1.0 ? 1.0 : t2;
	    t2 = t2 < -1.0 ? 1.0 : t2;
	    double pitch = Math.asin(t2);
	    
	    double t3 = 2.0 * (w * z + x * y);
	    double t4 = 1.0 - 2.0 * (y * y + z * z);
	    double yaw = Math.atan2(t3, t4);
	    
		return new EulerRotation(roll, -pitch, -yaw, EulerRotationMode.ZYX_EULER);
	}
	
	public static Quaternion getForward() {
		return new Quaternion(new EulerRotation(0, 0, 0));
	}
	
	public Quaternion toSpace(Quaternion q) {
		return new Quaternion(-q.z, q.x, q.z, q.w);
	}
	
	public Quaternion normalise() {
	    double n = Math.sqrt(x*x + y*y + z*z + w*w);
	    x /= n;
	    y /= n;
	    z /= n;
	    w /= n;
	    return this;
	}
	
	public Quaternion conjugate() {
	    x *= -1;
	    y *= -1;
	    z *= -1;
	    return this;
	}
	
	public Quaternion inverse() {
        return conjugate().divide(lengthSquared());
    }
	
	public Quaternion divide (double s) {
        w /= s;
        setFrom(getV().divide(s));
		return this;
    }  
	
	public double lengthSquared() {
        return w * w + (getV().multiply(getV())).getValue();
    }
	
	public void scale(double s) {
	    x *= s;
	    y *= s;
	    z *= s;
	    w *= s;
	}
	
	public void multiply(Quaternion q) {
		double x = this.x;
		double y = this.y;
		double z = this.z;
		double w = this.w;
	    this.x =  x * q.w + y * q.z - z * q.y + w * q.x;
	    this.y = -x * q.z + y * q.w + z * q.x + w * q.y;
	    this.z =  x * q.y - y * q.x + z * q.w + w * q.z;
	    this.w = -x * q.x - y * q.y - z * q.z + w * q.w;
	}
	
	public Quaternion add(Quaternion q2) {
	    x += q2.x;
	    y += q2.y;
	    z += q2.z;
	    w += q2.w;
	    return this;
	}
	
	public void multiply(Vec3 v) {
		w = -x * v.x - y * v.y - z * v.z;
		x = w  * v.x + y * v.z - z * v.y;
		y = w  * v.y + z * v.x - x * v.z;
		z = w  * v.z + x * v.y - y * v.x;
	}
	
//	public static Quaternion slerp(Quaternion qa, Quaternion qb, double t) {
//		// quaternion to return
//		Quaternion qm = new Quaternion();
//		// Calculate angle between them.
//		double cosHalfTheta = qa.w * qb.w + qa.x * qb.x + qa.y * qb.y + qa.z * qb.z;
//		// if qa=qb or qa=-qb then theta = 0 and we can return qa
//		if (Math.abs(cosHalfTheta) >= 1.0){
//			qm.w = qa.w;qm.x = qa.x;qm.y = qa.y;qm.z = qa.z;
//			return qm;
//		}
//		// Calculate temporary values.
//		double halfTheta = Math.acos(cosHalfTheta);
//		double sinHalfTheta = Math.sqrt(1.0 - cosHalfTheta*cosHalfTheta);
//		// if theta = 180 degrees then result is not fully defined
//		// we could rotate around any axis normal to qa or qb
//		if (Math.abs(sinHalfTheta) < 0.001){ // fabs is floating point absolute
//			qm.w = (qa.w * 0.5 + qb.w * 0.5);
//			qm.x = (qa.x * 0.5 + qb.x * 0.5);
//			qm.y = (qa.y * 0.5 + qb.y * 0.5);
//			qm.z = (qa.z * 0.5 + qb.z * 0.5);
//			return qm;
//		}
//		double ratioA = Math.sin((1 - t) * halfTheta) / sinHalfTheta;
//		double ratioB = Math.sin(t * halfTheta) / sinHalfTheta; 
//		//calculate Quaternion.
//		qm.w = (qa.w * ratioA + qb.w * ratioB);
//		qm.x = (qa.x * ratioA + qb.x * ratioB);
//		qm.y = (qa.y * ratioA + qb.y * ratioB);
//		qm.z = (qa.z * ratioA + qb.z * ratioB);
//		return qm;
//	}
	
	public double dot(Quaternion q){
        return q.getV().multiply(getV()).add(q.w + w).getValue();
    }
	
	public static Quaternion lerp(Quaternion q1, Quaternion q2, double t) {
		t = Math.max(0, Math.min(1, t));
        return q1.clone().multiply(1 - t).add(q2.clone().multiply(t)).normalise();
    }
	
//	public static Quaternion slerp(Quaternion q1, Quaternion q2, double t) {
//        t = Math.max(0, Math.min(1, t));
//        
//        Quaternion q3 = new Quaternion();
//        double dot = q1.dot(q2);
// 
//        if(dot < 0) {
//            dot = -dot;
//            q3 = q2.inverse();
//        } else q3 = q2;
//        
//        if (dot < 0.95) {
//            double angle = Math.acos(dot);
//            System.out.println("no lerp");
//            return q1.clone().multiply(Math.sin(angle*(1-t))).add(q3.clone().multiply(Math.sin(angle * t))).divide(Math.sin(angle));
//        } else {
//        	System.out.println("lerp");
//            // if the angle is small, use linear interpolation
//            return lerp(q1, q3,t); 
//        }
//    }

	public Quaternion multiply (double s) {
		w *= s;
		x *= s;
		y *= s;
		z *= s;
        return this;
	}

	@Override
	public void rotate(Vec3 vec) {
		Quaternion p = new Quaternion(vec);
		Quaternion q = clone();
		q.normalise();
		q.multiply(p);
		q.multiply(clone().conjugate().normalise());
		vec.setFrom(q);
	}

	@Override
	public void rotateReverse(Vec3 vec) {
		Quaternion p = new Quaternion(vec);
		Quaternion q = clone().conjugate();
		q.normalise();
		q.multiply(p);
		q.multiply(clone().normalise());
		vec.setFrom(q);	
	}

	@Override
	public Quaternion clone() {
		return new Quaternion(w, x, y, z);
	}

	@Override
	public void rotateBy(Rotation r) {
		
	}

	@Override
	public String getName() {
		return "Quaternion";
	}
}