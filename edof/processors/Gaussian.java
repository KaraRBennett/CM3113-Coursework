package edof.processors;

import java.lang.Math;

import edof.Image;
import edof.helpers.BorderExtrapolation;


public class Gaussian {

    private Image original;
    private Image gaussian;
    private int sigma;
    private double[][] gaussianKernel;
    

    public Gaussian(Image img, int sigma, double[][] kernel) {
        original = img;
        gaussian = new Image(img.depth, img.width, img.height);
        this.sigma = sigma;
        gaussianKernel = kernel;
        calculateGaussian();
    }


    public Image getGaussian() {
        return gaussian;
    }


    private void calculateGaussian() {
        Image edgeCopy = new Image();
        edgeCopy = BorderExtrapolation.edgeCopy(original, sigma);
        int[][] borderExtrapolatedImg = edgeCopy.pixels;

        double cumulativeValue;

        for (int y = 0; y < original.height; y++) {
            for (int x = 0; x < original.width; x++) {

                cumulativeValue = 0;

                for (int i = -sigma; i <= sigma; i++) {
                    for (int j = -sigma; j <= sigma; j++) {
                        cumulativeValue += borderExtrapolatedImg[x + j + sigma][y + i + sigma] * gaussianKernel[j + sigma][i + sigma];
                    }
                }

                if (cumulativeValue > 255) {
                    cumulativeValue = 255;
                } else if (cumulativeValue < 0) {
                    cumulativeValue = 0;
                }
                gaussian.pixels[x][y] = (int) cumulativeValue;
            }
        }
    }    

}