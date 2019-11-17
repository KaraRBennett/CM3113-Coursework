//c1645152

package edof.processors;

import java.util.Collections;
import java.util.ArrayList;

import edof.Image;
import edof.helpers.BorderExtrapolation;

public class MedianFilter {

    private Image depthMap;
    private Image medianFilteredDepthMap;

    public MedianFilter(Image depthMap) {
        this.depthMap = depthMap;
        calculateMedianFiltering();
    }


    private void calculateMedianFiltering() {
        ArrayList<Integer>  filterValues;
        Image extendedDepthMap = BorderExtrapolation.edgeCopy(depthMap, 1);
        medianFilteredDepthMap = new Image(depthMap.depth, depthMap.width, depthMap.height);

        for (int y = 0; y < depthMap.height; y++) {
            for (int x = 0; x < depthMap.width; x++) {
                filterValues = new ArrayList<Integer>();
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        filterValues.add(extendedDepthMap.pixels[x + j + 1][y + i + 1]);
                    }
                }
                Collections.sort(filterValues);
                medianFilteredDepthMap.pixels[x][y] = filterValues.get(4);   
            }
        }
    }


    public Image getMedianFilteredDepthMap() {
        return medianFilteredDepthMap;
    }

}