package maths;

/**
 * Static helper class for matrices / Vectors represented by a
 * 	double[][] array,
 * 	double[] array,
 * 	or a class implementing the Matrixable interface
 * 
 * @author Timo Lehnertz
 *
 */
public class Matrix {
	
	private Matrix() {
		super();
	}
	
	public static <T extends Matrixable> T add(T a, T b) {
		if(a.isMatrix() != b.isMatrix()) throw new RuntimeException("Cant add matrix to vector");
		if(a.isMatrix()) {
			return a.constructNewFromMatrix(add(a.getMatrix(), b.getMatrix()));
		} else {
			return a.constructNewFromVec(add(a.getVec(), b.getVec()));
		}
	}
	
	public static <T extends Matrixable> T subtract(T a, T b) {
		if(a.isMatrix() != b.isMatrix()) throw new RuntimeException("Cant subtract matrix to vector");
		if(a.isMatrix()) {
			return a.constructNewFromMatrix(subtract(a.getMatrix(), b.getMatrix()));
		} else {
			return a.constructNewFromVec(subtract(a.getVec(), b.getVec()));
		}
	}
	
	public static <T extends Matrixable> T multiply(T a, T b) {
		if(a.isMatrix() != b.isMatrix()) throw new RuntimeException("Cant multiply matrix to vector");
		if(a.isMatrix()) {
			return a.constructNewFromMatrix(multiply(a.getMatrix(), b.getMatrix()));
		} else {
			return a.constructNewFromVec(multiply(a.getVec(), b.getVec()));
		}
	}
	
	public static <T extends Matrixable> T divide(T a, T b) {
		if(a.isMatrix() || b.isMatrix()) throw new RuntimeException("Cant divide matrices");
		return a.constructNewFromVec(divide(a.getVec(), b.getVec()));
	}
	
	public static double[][] add(double[][] a, double[][] b) {
		if(a.length != b.length || a[0].length != b[0].length) {
			throw new RuntimeException("Cant add Matrices with different sizes");
		}
		double[][] m = new double[a.length][a[0].length];
		for (int row = 0; row < a.length; row++) {
			for (int col = 0; col < a[0].length; col++) {
				m[row][col] = a[row][col] + b[row][col];
			}
		}
		return m;
	}
	
	public static double[][] subtract(double[][] a, double[][] b) {
		if(a.length != b.length || a[0].length != b[0].length) {
			throw new RuntimeException("Cant subtract Matrices with different sizes");
		}
		double[][] m = new double[a.length][a[0].length];
		for (int row = 0; row < a.length; row++) {
			for (int col = 0; col < a[0].length; col++) {
				m[row][col] = a[row][col] - b[row][col];
			}
		}
		return m;
	}
	
	public static double[] add(double[] a, double[] b) {
		if(a.length != b.length) {
			throw new RuntimeException("Cant add Vectors with unequal sizes");
		}
		double[] res = new double[a.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = a[i] + b[i];
		}
		return res;
	}
	
	public static double[] subtract(double[] a, double[] b) {
		if(a.length != b.length) {
			throw new RuntimeException("Cant subtract Vectors with unequal sizes");
		}
		double[] res = new double[a.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = a[i] - b[i];
		}
		return res;
	}
	
	//http://matrixmultiplication.xyz/
	public static double[] multiply(double[] a, double[] b) {
		if(a.length != b.length) {
			throw new RuntimeException("Cant multiply Vectors with unequal sizes");
		}
		double[] res = new double[a.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = a[i] * b[i];
		}
		return res;
	}
	
	public static double[] divide(double[] a, double[] b) {
		if(a.length != b.length) {
			throw new RuntimeException("Cant divide Vectors with unequal sizes");
		}
		double[] res = new double[a.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = a[i] / b[i];
		}
		return res;
	}
	
	public static double[][] multiply(double[][] a, double[][] b) {
		if(b.length != a[0].length) {
			throw new RuntimeException("Cant multiply Matrices with wrong sizes");
		}
		double[][] m = new double[a.length][b[0].length];
		for (int row = 0; row < m.length; row++) {
			for (int col = 0; col < m[0].length; col++) {
				for (int i = 0; i < a[0].length; i++) {
					m[row][col] += a[row][i] * b[i][col];
				}
			}
		}
		return m;
	}
	
	public static double[][] getTranspose(double[][] m) {
		double[][] transpose = new double[m[0].length][m.length];
		for (int row = 0; row < transpose.length; row++) {
			for (int col = 0; col < transpose[row].length; col++) {
				transpose[row][col] = m[col][row];
			}
		}
		return transpose;
	}
	
	public static double[][] getInverse(double[][] m) {
		double[][] inverse = new double[m.length][m[0].length];
		for (int row = 0; row < inverse.length; row++) {
			for (int col = 0; col < inverse[row].length; col++) {
				inverse[row][col] = -m[row][col];
			}
		}
		return inverse;
	}

	
	public static String toString(Matrixable m) {
		if(m.isMatrix()) {
			return toString(m.getMatrix());
		} else {
			return toString(m.getVec());
		}
	}
	
	public static String toString(double[] vec) {
		String s = "(";
		String delimiter = "";
		for (int i = 0; i < vec.length; i++) {
			s += delimiter + Math.round(vec[i] * 100) / 100.0;
			delimiter = "|";
		}
		return s + ")";
	}
	
	public static String toString(double[][] matrix) {
		if(matrix == null) return "null";
		String s = "";
		for (int row = 0; row < matrix.length; row++) {
			s += "|";
			for (int col = 0; col < matrix[0].length; col++) {
				s += Math.round(matrix[row][col] * 100) / 100.0 + "|";
			}
			s += "\n";
		}
		return s;
	}
	
	protected static double[][] clone(double[][] matrix) {
		double[][] clone = new double[matrix.length][matrix[0].length];
		return clone;
	}
}