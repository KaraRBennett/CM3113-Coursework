package edof.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edof.Image;

public class ImageParser {
    
    private String filename;
    private String directory;


    //Method to split input string into filename and directory
    public ImageParser(String input) {
        String[] tmp = input.split("/");
        filename = tmp[tmp.length - 1];
        if (tmp.length > 1) {
            String dir = "";
            for (int i = 0; i < tmp.length - 1; i++) {
                dir += tmp[i] + "/";
            }
            directory = dir;
        } else {
            directory = null;
        }    
    }


    //Method to create Image object for each image file
    public ArrayList<Image> getImageStack() {
        ArrayList<String> imageFiles = getImageFiles();
        ArrayList<Image> imageStack = new ArrayList<Image>();

        System.out.println("\nReading the following files: -");
        for (String i : imageFiles) {
            System.out.println(i);
            Image img = new Image();
            img.ReadPGM(i);
            imageStack.add(img);
        }
        
        return imageStack;
    }


    //Method to get each image file from a file containaing a newline seperated list of images
    private ArrayList<String> getImageFiles() {
        ArrayList<String> imageFiles = new ArrayList<String>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(directory + filename));
            String line = reader.readLine();
            while (line != null) {
                imageFiles.add(directory + line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }    

        return imageFiles;
    }


    //Method to get filename without extension
    public String getFilenameExtensionless() {
        String[] split = filename.split("\\.");
        String extensionless = "";
        for (int i = 0; i < split.length - 1; i++) {
            extensionless += split[i];
        }
        return extensionless;
    }

}