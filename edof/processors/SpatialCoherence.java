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
        Image currentDt = d0;

        int convolutionValue;
        int highestValue;
        int imageSlice;
        double spatialCoherencyWeighting;

        for (int i = 0; i < iterations; i++) {

            //currentDt = ScaledDepthMap.getScaledDepthMap(currentDt, imageStack.size());
            Image previousDt = new Image();
            previousDt = BorderExtrapolation.edgeCopy(currentDt, 1);
            currentDt = new Image(d0.depth, d0.width, d0.height);
            previousDt.WritePGM("previousDT" + i + ".pgm");
            

            for (int y = 0; y < d0.height; y++) {
                for (int x = 0; x < d0.width; x++) {

                    ArrayList<Integer> previousDtNeighbourhood = new ArrayList<Integer>();

                    for (int j = -1; j <= 1; j++) {
                        for (int k = -1; k<= 1; k++) {
                            previousDtNeighbourhood.add(previousDt.pixels[x + j + 1][y + k + 1]);
                        }
                    }
                    //previousDtNeighbourhood.set(4, i);

                    spatialCoherencyWeighting = spatialCoherencyWeighting(previousDtNeighbourhood);
                    currentDt.pixels[x][y] = maxConvolutionSCWProduct(imageStack, spatialCoherencyWeighting, x, y);

                }

            }
        }

        spatialCoherence = currentDt;

    }


    private int maxConvolutionSCWProduct(ArrayList<Image> imageStack, double spatialCoherencyWeighting, int x, int y) {
        int imageSlice = 0; 
        double convolutionValue;
        double convolutionSCWProduct;
        double maxConvolutionSCWProduct = 0;

        for (int i = 0; i < imageStack.size(); i++) {
            convolutionValue = imageStack.get(i).pixels[x][y];
            convolutionSCWProduct = convolutionValue * spatialCoherencyWeighting;
            //System.out.println(spatialCoherencyWeighting);
            if (convolutionSCWProduct > maxConvolutionSCWProduct) {
                maxConvolutionSCWProduct = convolutionSCWProduct;
                imageSlice = i;
            }
        }  

        return imageSlice;

    }


    private double spatialCoherencyWeighting(ArrayList<Integer> values) {
        double neighbourhoodSD = standardDeviation(values);
        return Math.pow( (1 / 0.0001 + neighbourhoodSD), 2 );
    }


    private double standardDeviation(ArrayList<Integer> values) {
        double mean = 0;
        double variance = 0;

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

}