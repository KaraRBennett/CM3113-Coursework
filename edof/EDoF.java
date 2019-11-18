//c1645152

package edof;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edof.Image;
import edof.helpers.*;
import edof.processors.*;

public class EDoF{

    //Set default value for command line arguments
    private static boolean medianFiltering = false;
    private static boolean spacialCoherenceFiltering = false;
    private static boolean gaussiansConvultionKernel = false;
    private static int gaussianSigma;

    private static ArrayList<Image> imageStack;
    private static Mean mean;
    private static Image meanImg;
    private static ArrayList<Image> convolutionImageStack;
    private static GaussianKernel gaussianKernel;
    private static DepthMap depthMap;
    private static Image depthMapImg;
    private static Image scaledDepthMap;
    private static MedianFilter medianFilter;
    private static DepthOfField depthOfField;
    private static Image depthOfFieldImg;


    public static void main(String args[]) {

        //Initalise image stack
        String filename = args[0];
        ImageParser imageParser = new ImageParser(filename);
        imageStack = imageParser.getImageStack();


        //Retrive values of command line arguments
        if (args.length > 1 && args[1].equals("1")) {
            medianFiltering = true;
        }
        if (args.length > 2 && args[2].equals("1")) {
            spacialCoherenceFiltering = true;
        }
        if (args.length > 3 && args[3].toLowerCase().equals("d")) {
            gaussiansConvultionKernel = true;
            if (args.length > 4) {
                gaussianSigma = Integer.parseInt(args[4]);

            }
        }


        //Main body

        //Create mean image
        System.out.println("\nCreating mean image");
        mean = new Mean(imageStack);
        meanImg = mean.getMean();
        
        
        if (gaussiansConvultionKernel) {
            //Generate Difference of Gaussian for each image in the stack and add to array
            System.out.println("\nCreating Gaussian convolution image stack");
            convolutionImageStack = new ArrayList<Image>();
            gaussianKernel = new GaussianKernel(gaussianSigma);
            System.out.print("\nImages reamining: ");
            int count = imageStack.size();
            for (Image i : imageStack) {
                System.out.print(count + " ");
                convolutionImageStack.add(new DifferenceOfGaussian(i, gaussianSigma, gaussianKernel).getDiffOfGaussian());
                count--;
            }
        } else {
            //Generate Sobel for each image in the stack and add to array
            System.out.println("\nCreating Sobel convolution image stack");
            convolutionImageStack = new ArrayList<Image>();
            System.out.print("\nImages reamining: \t");
            int count = imageStack.size();
            for (Image i : imageStack) {
                System.out.print(count + " ");
                convolutionImageStack.add(new Sobel(i).getSobel());
                count--;
            }
        }


        //Create 3D Depth Map
        System.out.println("\n\nCreating 3D Depth Map");
        depthMap = new DepthMap(convolutionImageStack);
        depthMapImg = depthMap.getDepthMap();


        //If enabled, apply Median Filter
        if (medianFiltering) { 
            System.out.println("\nApplying Median Filtering");  
            medianFilter = new MedianFilter(depthMapImg);
            depthMapImg = medianFilter.getMedianFilteredDepthMap();
        }

        //If enabled, apply Spacal Cohenerece Filtering
        if (spacialCoherenceFiltering) {
            System.out.println("\nApplying Spatial Coherence");
            SpatialCoherence spatialCoherence = new SpatialCoherence(depthMapImg, imageStack);
            depthMapImg = spatialCoherence.getSpatialCoherence();
        }


        //Create Extended Depth of Field Image
        System.out.println("\nCreating Exetended Depth of Field Image");
        depthOfField = new DepthOfField(imageStack, depthMapImg);
        depthOfFieldImg = depthOfField.getDepthOfField();


        //Get scaled Depth Map
        scaledDepthMap = ScaledDepthMap.getScaledDepthMap(depthMapImg, imageStack.size());



        //Generate output images
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd.HH.mm.ss");
        String saveName = imageParser.getFilenameExtensionless() + "-" + dateTime.format(formatter);

        System.out.println("\nSaving files with the following file names:");

        System.out.println(saveName + "-first.pgm");
        imageStack.get(0).WritePGM(saveName + "-first.pgm");

        System.out.println(saveName + "-mean.pgm");
        meanImg.WritePGM(saveName + "-mean.pgm");

        System.out.println(saveName + "-convolution.pgm");
        convolutionImageStack.get(0).WritePGM(saveName + "-convolution.pgm");

        System.out.println(saveName + "-depth.pgm");
        scaledDepthMap.WritePGM(saveName + "-depth.pgm");

        System.out.println(saveName + "-edof.pgm");
        depthOfFieldImg.WritePGM(saveName + "-edof.pgm");
    }

}