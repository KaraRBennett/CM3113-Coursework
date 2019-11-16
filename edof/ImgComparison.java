package edof;


//Program to compare two images on a pixel by pixel basis
//Used for testing only
public class ImgComparison {

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println("ERROR: ImgComparison requires two files names to be supplied to run");
        } else {
            String image1name = args[0];
            String image2name = args[1];
    
            Image image1 = new Image();
            image1.ReadPGM(image1name);
    
            Image image2 = new Image();
            image2.ReadPGM(image2name);
    
            if (image1.height != image2.height) {
                System.out.println("Images have different height values");
            } else if (image1.width != image2.width) {
                System.out.println("Images have different width values");
            } else {
    
                int differenceMagnitude = 0;
                 
                for (int y = 0; y < image1.height; y++) {
                    for (int x = 0; x < image1.width; x++) {
                        if (image1.pixels[x][y] != image2.pixels[x][y]) {
                            differenceMagnitude++;
                        }
                    }    
                }
    
                if (differenceMagnitude == 0) {
                    System.out.println("Images are the same");
                } else {
                    System.out.println("Images have a difference of magnitude " + differenceMagnitude);
                }
            }
        }
    }    
}