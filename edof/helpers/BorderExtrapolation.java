package edof.helpers;

import edof.Image;
import javafx.scene.input.PickResult;

public class BorderExtrapolation {

    //Method to return new pixel array with n pixel edge copy
    public static Image edgeCopy(Image original, int n) {
        Image extrapolated = new Image(original.depth, original.width + (n*2), original.height + (n*2));

        int originalMaxX = original.width - 1;
        int originalMaxY = original.height - 1;
        int extrapolatedMaxX = extrapolated.width -1;
        int extrapolatedMaxY = extrapolated.height - 1;


        //Set corner values 

        //Set upper left corner values
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                extrapolated.pixels[x][y] = original.pixels[0][0];
            }
        }

        //Set upper right corner values
        for (int y = 0; y < n; y++) {
            for (int x = extrapolatedMaxX; x > extrapolatedMaxX - n; x--) {
                extrapolated.pixels[x][y] = original.pixels[originalMaxX][0];
            }
        }

        //Set lower left corner values
        for (int y = extrapolatedMaxY; y > extrapolatedMaxY - n; y--) {
            for (int x = 0; x < n; x++) {
                extrapolated.pixels[x][y] = original.pixels[0][originalMaxY];
            }
        }

        //Set lower right corner values
        for (int y = extrapolatedMaxY; y > extrapolatedMaxY - n; y--) {
            for (int x = extrapolatedMaxX; x > extrapolatedMaxX - n; x--) {
                extrapolated.pixels[x][y] = original.pixels[originalMaxX][originalMaxY];
            }
        }


        //Set remaining border values

        //Set top and bottom border values
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < original.width; x++) {
                extrapolated.pixels[x + n][y] = original.pixels[x][0];
                extrapolated.pixels[x + n][extrapolatedMaxY - y] = original.pixels[x][originalMaxY];
            }
        }

        //Set right border values
        for (int y = 0; y < original.height; y++) {
            for (int x = 0; x < n; x++) {
                extrapolated.pixels[x][y + n] = original.pixels[0][y];
                extrapolated.pixels[extrapolatedMaxX - x][y + n] = original.pixels[originalMaxX][y];
            }
        }


        //Set all other remaining values
        for (int y = 0; y < original.height; y++) {
            for (int x = 0; x < original.width; x++) {
                extrapolated.pixels[x + n][y + n] = original.pixels[x][y]; 
            }
        }

        //extrapolated.WritePGM("EdgeCopy-" + System.currentTimeMillis() +".pgm");
        return extrapolated;
    }
}