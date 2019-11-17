package edof.processors;

import edof.Image;

public class ScaledDepthMap {

    //Method return a new image that is the depth map scaled by the factor that is the size of the image
    public static Image getScaledDepthMap(Image depthMap, int stackSize) {
        Image depthMapScaled = new Image(depthMap.depth, depthMap.width, depthMap.height);
        int scale = (int) 255/stackSize;
        for (int y = 0; y < depthMapScaled.height; y++) {
            for (int x = 0; x < depthMapScaled.width; x++) {
                int value = depthMap.pixels[x][y] * scale;
                depthMapScaled.pixels[x][y] = value < 255? value : 255;
            }
        }
        return depthMapScaled;
    }
}