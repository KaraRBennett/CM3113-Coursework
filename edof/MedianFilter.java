package edof;

import java.util.Collections;
import java.util.ArrayList;

public class MedianFilter {

    private Image depthMap;
    private Image medianFilteredDepthMap;

    public MedianFilter(Image depthMap) {
        this.depthMap = depthMap;
        calculateMedianFiltering();
    }

    public Image getMedianFilteredDepthMap() {
        return medianFilteredDepthMap;
    }

    private void calculateMedianFiltering() {

        medianFilteredDepthMap = new Image(depthMap.depth, depthMap.width, depthMap.height);

        for (int y = 0; y < depthMap.height; y++) {
            for (int x = 0; x < depthMap.width; x++) {

                //Skip image boundaries
                if (!(y == 0 || y == depthMap.height - 1 || x == 0 || x == depthMap.width - 1)) {

                    ArrayList<Integer> filterValues = new ArrayList<Integer>();
                    filterValues.add(depthMap.pixels[x - 1][y - 1]);
                    filterValues.add(depthMap.pixels[x][y - 1]);
                    filterValues.add(depthMap.pixels[x + 1][y - 1]);
                    filterValues.add(depthMap.pixels[x - 1][y]);
                    filterValues.add(depthMap.pixels[x][y]);
                    filterValues.add(depthMap.pixels[x + 1][y]);
                    filterValues.add(depthMap.pixels[x - 1][y + 1]);
                    filterValues.add(depthMap.pixels[x][y + 1]);
                    filterValues.add(depthMap.pixels[x + 1][y + 1]);

                    Collections.sort(filterValues);
                    medianFilteredDepthMap.pixels[x][y] = filterValues.get(4);
                }    
            }
        }
    }

}