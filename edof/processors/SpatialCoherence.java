//c1645152

package edof.processors;

import java.lang.Math;
import java.util.ArrayList;

public class SpatialCoherence {

    /*

    private Image original;
    private Image depthMap;
    private int stackDepth;
    private int iterations = 10;

    public SpatialCoherence(Image img, int stackDepth) {
        original = img;
        this.stackDepth = stackDepth;

        calculateSpatialCoherence(); //Perform an initial caluculation based off the original image
        for (int i = 1; i < iterations; i++) {
            calculateSpatialCoherence();
        }
    }


    public Image getDepthMap() {
        return depthMap;
    }

    private calculateSpatialCoherence(Image source) {
        Image result = new Image(source.depth, source.width, source.height);

        for (int y = 0; y < source.height; y++) {
            for (int x = 0; x < source.width; x++) {

                //Skip image boundaries
                if (!(y == 0 || y == source.height - 1 || x == 0 || x == source.width - 1)) {

                    ArrayList<Integer> neighbourhood = new ArrayList<Integer>();

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            neighbourhood.add(source.pixels[x + j][y + i]);
                        }
                    }

                    spatialValue = Math.pow(1 / (0.0001 + standardDeviation(neighbourhood) * centerPixel), 2);

                    if (spatialValue > stackDepth) {
                        spatialValue = stackDepth;
                    } else if (spatialValue < 0) {
                        spatialValue = 0;
                    }

                    result.pixels[x][y] = (int)spatialValue;

                }
            }    
        }

        depthMap = result;
    }


    private float standardDeviation(ArrayList<Integer> values) {
        float mean = 0;
        float variance = 0;

        for (int n : values) {
            mean += n;
        }
        mean /= values.size();

        for (int n : values) {
            variance += Math.pow(n - mean, 2);
        }
        variance /= values.size();

        return Math.sqrt(variance);
    }

    */
}