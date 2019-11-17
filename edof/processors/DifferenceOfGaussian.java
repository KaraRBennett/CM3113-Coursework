package edof.processors;

import java.lang.Math;

import edof.Image;
import edof.processors.Gaussian;
import edof.processors.GaussianKernel;


public class DifferenceOfGaussian {

    private Image original;
    private int sigma;
    private Image diffOfGaussian;
    private Image gaussian1;
    private Image gaussian2;


    public DifferenceOfGaussian(Image img, int sigma, GaussianKernel kernel) {
        original = img;
        this.sigma = sigma;
        gaussian1 = new Gaussian(original, sigma, kernel.getS1GaussianKernel()).getGaussian();
        gaussian2 = new Gaussian(original, sigma * 2, kernel.getS2GaussianKernel()).getGaussian();
        calculateDiffOfGaussian();
    }


    private void calculateDiffOfGaussian() {
        int difference;
        diffOfGaussian = new Image(original.depth, original.width, original.height);

        for (int y = 0; y < original.height; y++) {
            for (int x = 0; x < original.width; x++) {
                difference = gaussian1.pixels[x][y] - gaussian2.pixels[x][y];
                difference = Math.abs(difference);
                diffOfGaussian.pixels[x][y] = difference;
            }
        }
    }


    public Image getDiffOfGaussian() {
        return diffOfGaussian;
    }

}