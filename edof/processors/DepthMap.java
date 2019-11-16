//c1645152

package edof.processors;

import java.util.ArrayList;

import edof.Image;

public class DepthMap {

    private ArrayList<Image> imageStack;
    private Image depthMap;
    private Image depthMapScaled;

    public DepthMap(ArrayList<Image> sobelImageStack) {
        this.imageStack = sobelImageStack;
        calculateDepthMap();
        calculateDepthMapScaled();
    }


    public Image getDepthMap() {
        return depthMap;
    }


    public Image getDepthMapScaled() {
        return depthMapScaled;
    }
    

    private void calculateDepthMap() {
        depthMap = new Image(imageStack.get(0).depth, imageStack.get(0).width, imageStack.get(0).height);

        for (int y = 0; y < depthMap.height; y++) {
            for (int x = 0; x < depthMap.width; x++) {
                int highestValue = 0;
                int imageSlice = 0;

                for(int i =0; i < imageStack.size(); i++) {
                    int sobelValue = imageStack.get(i).pixels[x][y];
                    if(sobelValue > highestValue) {
                        highestValue = sobelValue;
                        imageSlice = i;
                    }
                }

                depthMap.pixels[x][y] = imageSlice;

            }
        }    
    }  
    
    
    private void calculateDepthMapScaled() {
        depthMapScaled = new Image(depthMap.depth, depthMap.width, depthMap.height);

        int scale = (int) 255/imageStack.size();

        for (int y = 0; y < depthMapScaled.height; y++) {
            for (int x = 0; x < depthMapScaled.width; x++) {
                int value = depthMap.pixels[x][y] * scale;
                depthMapScaled.pixels[x][y] = value < 255? value : 255;
            }
        }        
    }
}