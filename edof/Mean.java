package edof;

import java.util.ArrayList;

public class Mean{ 

    private ArrayList<Image> imageStack;
    private Image meanImg;

    public Mean(ArrayList<Image> imageStack) {
        this.imageStack = imageStack;
        calculateMean();
    }

    public Image getMean() {
        return meanImg;
    }

    private void calculateMean() {
        meanImg = new Image(imageStack.get(0).depth, imageStack.get(0).width, imageStack.get(0).height);

        for (int y = 0; y < meanImg.height; y++) {
            for (int x = 0; x < meanImg.width; x++) {
                
                int totalIntensity = 0;
                
                for (Image i : imageStack) {
                    totalIntensity += i.pixels[x][y];
                }

                meanImg.pixels[x][y] = (int)totalIntensity / imageStack.size();
            }
        }        
    }
}