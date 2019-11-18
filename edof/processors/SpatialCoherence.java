//c1645152

package edof.processors;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

import edof.Image;
import edof.helpers.BorderExtrapolation;
import edof.processors.ScaledDepthMap;


public class SpatialCoherence {

    private Image spatialCoherence;
    private int iterations = 10;


    public SpatialCoherence(Image d0, ArrayList<Image> imageStack) {
        calculateSpatialCoherence(d0, imageStack);
    }


    public Image getSpatialCoherence() {
        return spatialCoherence;
    }


    private void calculateSpatialCoherence(Image d0, ArrayList<Image> imageStack) {

        Image previousDepthMap = d0;
        previousDepthMap = BorderExtrapolation.edgeCopy(previousDepthMap, 1);
    
        for (int iteration = 0; iteration < iterations; iteration++) {

            Image newIteration = new Image(d0.depth, d0.width, d0.height);

            for (int y = 0; y < d0.height; y++) {
                for (int x = 0; x < d0.width; x++) {
                    double maxConvolutionValue = 0;
                    int imageSlice = 0;

                    int[] spaitalValues = new int[9];
                    for (int j = 0; j <= 2; j++) {
                        for (int k = 0; k <= 2; k++) {
                            spaitalValues[j * 3 + k] = previousDepthMap.pixels[x + k][y + j];
                        }
                    }
    
                    for(int i = 0; i < imageStack.size(); i++) {
                        int convolutionValue = imageStack.get(i).pixels[x][y];
                        spaitalValues[4] = i;
                        double spaitalWeighting = spatialCoherencyWeighting(spaitalValues);

                        double n = convolutionValue * spaitalWeighting;

                        if(n > maxConvolutionValue) {
                            maxConvolutionValue = n;
                            imageSlice = i;
                        } 
                    }
    
                    newIteration.pixels[x][y] = imageSlice;
    
                }
            } 

            previousDepthMap = newIteration;
            previousDepthMap = BorderExtrapolation.edgeCopy(previousDepthMap, 1);
            spatialCoherence = newIteration;

        }

    }


    private double spatialCoherencyWeighting(int[] values) {
        double neighbourhoodSD = standardDeviation(values);
        return Math.pow( (1 / (0.0001 + neighbourhoodSD)), 2 );
    }


    private double standardDeviation(int[] values) {
        double mean = 0;
        double variance = 0;

        for (int n : values) {
            mean += n;
        }
        mean /= values.length;

        for (int n : values) {
            variance += Math.pow(n - mean, 2);
        }
        variance /= values.length;

        return Math.sqrt(variance);
    }

}