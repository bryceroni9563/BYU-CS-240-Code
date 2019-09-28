package editor;

public class Pixel {
    int redVal;
    int blueVal;
    int greenVal;
    public Pixel(int redVal, int greenVal, int blueVal) {
        this.redVal = redVal;
        this.blueVal = blueVal;
        this.greenVal = greenVal;
    }

    public int getRedVal() {
        return redVal;
    }

    public int getBlueVal() {
        return blueVal;
    }

    public int getGreenVal() {
        return greenVal;
    }

    public void setRedVal(int newRed) {
        if (newRed > 255) {
            this.redVal = 255;
        }
        else if (newRed < 0) {
            this.redVal = 0;
        }
        else {
            this.redVal = newRed;
        }
    }

    public void setBlueVal(int newBlue) {
        if (newBlue > 255) {
            this.blueVal = 255;
        }
        else if (newBlue < 0) {
            this.blueVal = 0;
        }
        else {
            this.blueVal = newBlue;
        }
    }

    public void setGreenVal(int newGreen) {
        if (newGreen > 255) {
            this.greenVal = 255;
        }
        else if (newGreen < 0) {
            this.greenVal = 0;
        }
        else {
            this.greenVal = newGreen;
        }
    }

    public void invertColors() {
        setRedVal(255 - this.redVal);
        setGreenVal(255 - this.greenVal);
        setBlueVal(255 - this.blueVal);
    }

    public void toGrayscale() {
        int average;
        average = (this.blueVal + this.greenVal + this.redVal) / 3;
        setGreenVal(average);
        setRedVal(average);
        setBlueVal(average);
    }

    public void emboss(int cornerRed, int cornerGreen, int cornerBlue) {

        int redDiff = (this.redVal - cornerRed);
        int blueDiff = (this.blueVal - cornerBlue);
        int greenDiff = (this.greenVal - cornerGreen);

        int maxDifference;
        if ((Math.abs(redDiff) >= Math.abs(blueDiff)) && (Math.abs(redDiff) >= Math.abs(greenDiff))) {
            maxDifference = redDiff;
        }
        else if (Math.abs(greenDiff) >= Math.abs(blueDiff)) {
            maxDifference = greenDiff;
        }
        else {
            maxDifference = blueDiff;
        }
        int v = 128 + maxDifference;
        setRedVal(v);
        setGreenVal(v);
        setBlueVal(v);
    }

    public void blur(Pixel[] pixelsToBlur) {
        int redSum = 0;
        int blueSum = 0;
        int greenSum = 0;
        for (int i = 0; i < pixelsToBlur.length; i++) {
            redSum = redSum + pixelsToBlur[i].getRedVal();
            blueSum = blueSum + pixelsToBlur[i].getBlueVal();
            greenSum = greenSum + pixelsToBlur[i].getGreenVal();

        }
        if (pixelsToBlur.length != 0) {
            setRedVal(redSum / pixelsToBlur.length);
            //System.out.println(redVal);
            setGreenVal(greenSum / pixelsToBlur.length);
            //System.out.println(greenVal);
            setBlueVal(blueSum / pixelsToBlur.length);
            //System.out.println(blueVal);
        }
    }

    public String toString() {
        return redVal + " " + greenVal + " " + blueVal;
    }
}
