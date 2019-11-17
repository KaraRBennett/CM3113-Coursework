//c1645152

package edof.processors;

import java.util.ArrayList;

import edof.Image;

public class DepthOfField {

    private ArrayList<Image> imageStack;
    private Image depthMap;
    private Image depthOfField;


    public DepthOfField(ArrayList<Image> imageStack, Image depthMap) {
        this.imageStack = imageStack;
        this.depthMap = depthMap;
        depthOfField = new Image (depthMap.depth, depthMap.width, depthMap.height);
        calculateDepthOfField();
    }


    private void calculateDepthOfField() {
        for (int y = 0; y < depthOfField.height; y++) {
            for (int x = 0; x < depthOfField.width; x++) {
                int imageSlice = depthMap.pixels[x][y];
                depthOfField.pixels[x][y] = imageStack.get(imageSlice).pixels[x][y];
            }
        }    
    }


    public Image getDepthOfField() {
        return depthOfField;
    }
    
}