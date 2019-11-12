package edof;

import java.lang.Math;

import edof.Debug;

public class Sobel {

    private int[][] gx;
    private int[][] gy;
    private Image original;
    private Image sobelImg;

    public Sobel(Image img) {
        gx = initaliseGx();
        gy = initaliseGy();
        original = img;
        sobelImg = new Image(img.depth, img.width, img.height);
        calculateSobel();
    }


    public Image getSobel() {
        return sobelImg;
    }


    //Method to initalise Gx mask
    private int[][] initaliseGx() {
        gx = new int[3][3];
        gx[0][0] = -1;
        gx[0][1] = 0;
        gx[0][2] = 1;
        gx[1][0] = -2;
        gx[1][1] = 0;
        gx[1][2] = 2;
        gx[2][0] = -1;
        gx[2][1] = 0;
        gx[2][2] = 1;
        return gx;
    }


    //Method to initalise Gy mask
    private int[][] initaliseGy() {
        gy = new int[3][3];
        gy[0][0] = -1;
        gy[0][1] = -2;
        gy[0][2] = -1;
        gy[1][0] = 0;
        gy[1][1] = 0;
        gy[1][2] = 0;
        gy[2][0] = 1;
        gy[2][1] = 2;
        gy[2][2] = 1;
        return gy;
    }


    //Method to calculate the Sobel of the image
    private void calculateSobel() {
        double gradient = 0;
        double sumX = 0;
        double sumY = 0;

        for (int y = 0; y < original.height; y++) {
            for (int x = 0; x < original.width; x++) {

                //Skip image boundaries
                if (y == 0 || y == original.height - 1) {
                    gradient = 0;
                } else if (x == 0 || x == original.width - 1) {
                    gradient = 0;
                } else {
                    sumX = 0;
                    sumY = 0;

                    //Sobel calculation of sumX and sumY
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            sumX = sumX + original.pixels[x + j][y + i] * gx[i + 1][j + 1];
                            sumY = sumY + original.pixels[x + j][y + i] * gy[i + 1][j + 1];
                        }
                    }

                    //Calucation of gradient from sumX and sumY
                    gradient = Math.sqrt(Math.pow(sumX, 2) + Math.pow(sumY, 2));
                    if(gradient > 255) { 
                        gradient = 255;
                    } else if (gradient < 0) {
                        gradient = 0;
                    }
                }

                //Write gradient value to sobelImg
                sobelImg.pixels[x][y] = (int)gradient;
            }
        }
    }
}