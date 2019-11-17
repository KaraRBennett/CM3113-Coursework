//c1645152

package edof.processors;

import java.util.ArrayList;

import edof.Image;

public class DepthMap {

    private ArrayList<Image> imageStack;
    private Image depthMap;


    public DepthMap(ArrayList<Image> convolutionImageStack) {
        this.imageStack = convolutionImageStack;
        calculateDepthMap();
    }
    

    //Method to calculate the depth map where at each location x,y the value
    //represents the slice of the image with the highest convolution value
    private void calculateDepthMap() {
        depthMap = new Image(imageStack.get(0).depth, imageStack.get(0).width, imageStack.get(0).height);
        int convolutionValue;
        int maxConvolutionValue;
        int imageSlice;

        for (int y = 0; y < depthMap.height; y++) {
            for (int x = 0; x < depthMap.width; x++) {
                maxConvolutionValue = 0;
                imageSlice = 0;

                for(int i = 0; i < imageStack.size(); i++) {
                    convolutionValue = imageStack.get(i).pixels[x][y];
                    if(convolutionValue > maxConvolutionValue) {
                        maxConvolutionValue = convolutionValue;
                        imageSlice = i;
                    } 
                }

                depthMap.pixels[x][y] = imageSlice;

            }
        }    
    }  


    public Image getDepthMap() {
        return depthMap;
    }

}