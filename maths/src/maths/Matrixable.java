package maths;

public interface Matrixable {

	public boolean isMatrix();
	
	public double[][] getMatrix();
	
	public double[] getVec();
	
	public <T extends Matrixable> T constructNewFromVec(double[] vec);
	
	public <T extends Matrixable> T constructNewFromMatrix(double[][] matrix);
}