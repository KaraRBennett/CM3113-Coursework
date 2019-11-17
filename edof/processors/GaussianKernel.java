package edof.processors;

public class GaussianKernel {

    private double[][] s1GaussianKernel;
    private double[][] s2GaussianKernel;

    public GaussianKernel(int sigma) {
        s1GaussianKernel = gaussianKernel(sigma);
        s2GaussianKernel = gaussianKernel(sigma * 2);
    }
    

    //Method to generate the gaussian kernel for a given sigma
    private double[][] gaussianKernel(int sigma) {
        int kernelSize;
        kernelSize = sigma * 2 + 1;

        double[] linearGaussian = new double[kernelSize];
        double[][] kernel = new double[kernelSize][kernelSize];
        double sumLinearGaussian = 0;

        for (int x = -sigma; x <= sigma; x++) {
            double sigmaComponent = 2 * Math.pow(sigma, 2);
            linearGaussian[x + sigma] = Math.pow( Math.exp(1), -Math.pow(x, 2) / sigmaComponent);
            sumLinearGaussian += linearGaussian[x + sigma];
        }

        double weight = 1 / sumLinearGaussian;
        for (int i = 0; i < linearGaussian.length; i++) {
            linearGaussian[i] = linearGaussian[i] * weight;
        }

        for (int y = 0; y < kernelSize; y++) {
            for (int x = 0; x < kernelSize; x++) {
                kernel[x][y] = linearGaussian[x] * linearGaussian[y];
            }
        }

        return kernel;

    }


    //Getters to return the two differently sized kernels
    public double[][] getS1GaussianKernel() {
        return s1GaussianKernel;
    }


    public double[][] getS2GaussianKernel() {
        return s2GaussianKernel;
    }

}