package edof;

import java.util.ArrayList;

import edof.DepthMap;
import edof.DepthOfField;
import edof.ImageParser;
import edof.Mean;
import edof.Sobel;
import jdk.internal.dynalink.beans.StaticClass;

public class EDoF{

    //Set default value for command line arguments
    private static boolean medianFiltering = false;
    private static boolean spacialCoherenceFiltering = false;
    private static boolean gaussiansConvultionKernel = false;

    private static ArrayList<Image> imageStack;
    private static Mean mean;
    private static Image meanImg;
    private static ArrayList<Image> sobelImageStack;
    private static DepthMap depthMap;
    private static Image depthMapImg;
    private static DepthOfField depthOfField;
    private static Image depthOfFieldImg;


    public static void main(String args[]) {

        //Initalise image stack
        String filename = args[0];
        ImageParser imageParser = new ImageParser(filename);
        imageStack = imageParser.getImageStack();


        //Set values for command line arguments
        if (args.length > 1 && args[1].equals("1")) {
            medianFiltering = true;
        }
        if (args.length > 2 && args[2].equals("1")) {
            spacialCoherenceFiltering = true;
        }
        if (args.length > 3 && args[3].toLowerCase().equals("d")) {
            gaussiansConvultionKernel = true;
        }


        //Main body

        //Create mean image
        System.out.println("\nCreating mean image");
        mean = new Mean(imageStack);
        meanImg = mean.getMean();
        
        
        if (gaussiansConvultionKernel) {
            System.out.println("\Guassian's Convultion Not Yet Implemented");
        } else {
            //Generate Sobel for each image in the stack and add to array
            System.out.println("\nCreating Sobel Image Stack");
            sobelImageStack = new ArrayList<Image>();
            for (Image i : imageStack) {
                sobelImageStack.add(new Sobel(i).getSobel());
            }
        }

        //Create 3D Depth Map and related image
        System.out.println("\nCreating 3D Depth Map");
        depthMap = new DepthMap(sobelImageStack);
        depthMapImg = depthMap.getDepthMap();

        if (medianFiltering) { 
            System.out.println("\nApplying median filter");
            MedianFilter medianFilter = new MedianFilter(depthMapImg);
            depthMapImg = medianFilter.getMedianFilteredDepthMap();
        }

        //Create Extended Depth of Field Image
        System.out.println("\nCreating Exetend Depth of Field Image");
        depthOfField = new DepthOfField(imageStack, depthMapImg);
        depthOfFieldImg = depthOfField.getDepthOfField();


        //Get scaled depth map
        depthMapImg = depthMap.getDepthMapScaled();

        /*
        if (spacialCoherenceFiltering) {
            spacialCoherenceFiltering(i);
        }
        if (sobelConvolution) {
            sobelConvolution(i);
        }
        */


        //Generate output images
        Debug.printSaveFileHeader();
        String saveName = imageParser.getFilenameExtensionless() + "-" + System.currentTimeMillis();

        Debug.printString(saveName + "-first.pgm");
        imageStack.get(0).WritePGM(saveName + "-first.pgm");

        Debug.printString(saveName + "-mean.pgm");
        meanImg.WritePGM(saveName + "-mean.pgm");

        Debug.printString(saveName + "-convolution.pgm");
        sobelImageStack.get(0).WritePGM(saveName + "-convolution.pgm");

        Debug.printString(saveName + "-depth.pgm");
        depthMapImg.WritePGM(saveName + "-depth.pgm");

        Debug.printString(saveName + "-edof.pgm");
        depthOfFieldImg.WritePGM(saveName + "-edof.pgm");
    }

}