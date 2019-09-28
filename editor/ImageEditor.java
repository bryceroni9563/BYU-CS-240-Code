package editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ImageEditor {


    public static void main (String[] args) throws IOException {
        File inFile = null;
        if (args.length > 0) {
            inFile = new File(args[0]);
        }
        else {
            System.err.println("Invalid arguments count:" + args.length);
        }
        Scanner sc = new Scanner(inFile).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        StringBuilder toNewFile = new StringBuilder();
        toNewFile.append(sc.next() + " ");
        int width = sc.nextInt();
        toNewFile.append(width + " ");
        int height = sc.nextInt();
        toNewFile.append(height + " ");
        Pixel[][] image;
        image = new Pixel[height][width];
        toNewFile.append(sc.nextInt() + " ");
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                image[i][j] = new Pixel(sc.nextInt(), sc.nextInt(), sc.nextInt());
            }
        }
        if (args[2] == "invert") {
            Pixel[][] invertedImage = new Pixel[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    invertedImage[i][j] = image[i][j];
                    invertedImage[i][j].invertColors();
                    toNewFile.append(invertedImage[i][j].toString() + " ");
                }
            }
        }
        else if (args[2] == "grayscale") {
            Pixel[][] grayscaleImage = new Pixel[height][width];
            int i;
            for (i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    grayscaleImage[i][j] = image[i][j];
                    grayscaleImage[i][j].toGrayscale();
                    toNewFile.append(grayscaleImage[i][j].toString() + " ");
                }
            }
        }
        else if (args[2] == "emboss") {
            Pixel[][] embossedImage = new Pixel[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    if (i == 0 || j == 0) {
                        embossedImage[i][j] = new Pixel(128, 128, 128);
                    }
                    else {
                        //System.out.println(i + " " + j);
                        embossedImage[i][j] = new Pixel(image[i][j].getRedVal(), image[i][j].getGreenVal(), image[i][j].getBlueVal());
                        embossedImage[i][j].emboss(image[i-1][j-1].getRedVal(), image[i-1][j-1].getGreenVal(), image[i-1][j-1].getBlueVal());
                    }
                    toNewFile.append(embossedImage[i][j].toString() + " ");
                }
            }
        }
        else if (args[2] == "motionblur") {
            Pixel[][] blurredImage = new Pixel[height][width];
            int numToBlur = Integer.parseInt(args[3]);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    blurredImage[i][j] = new Pixel(image[i][j].getRedVal(), image[i][j].getGreenVal(), image[i][j].getBlueVal());
                }
            }
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int n = numToBlur;
                    if ((n+j) >= width) {
                        n = width - j;
                    }
                    Pixel[] toBlur = new Pixel[n];
                    for (int k = 0; k < n; k++) {
                        toBlur[k] = new Pixel(image[i][j + k].getRedVal(), image[i][j + k].getGreenVal(), image[i][j + k].getBlueVal());
                    }
                    blurredImage[i][j].blur(toBlur);
                    toNewFile.append(blurredImage[i][j].toString() + " ");
                    Arrays.fill(toBlur, null);
                }
            }
        }
        FileWriter fileWriter = new FileWriter(args[1]);
        fileWriter.write(toNewFile.toString());
        fileWriter.close();
    }
}
