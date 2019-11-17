//c1645152

package edof.processors;

import java.util.ArrayList;

import edof.Image;


public class Mean{ 

    private ArrayList<Image> imageStack;
    private Image meanImg;

    public Mean(ArrayList<Image> imageStack) {
        this.imageStack = imageStack;
        calculateMean();
    }


    private void calculateMean() {
        int totalIntensity;
        meanImg = new Image(imageStack.get(0).depth, imageStack.get(0).width, imageStack.get(0).height);

        for (int y = 0; y < meanImg.height; y++) {
            for (int x = 0; x < meanImg.width; x++) {
                totalIntensity = 0;
                for (Image i : imageStack) {
                    totalIntensity += i.pixels[x][y];
                }
                meanImg.pixels[x][y] = (int)totalIntensity / imageStack.size();
            }
        }        
    }


    public Image getMean() {
        return meanImg;
    }

}