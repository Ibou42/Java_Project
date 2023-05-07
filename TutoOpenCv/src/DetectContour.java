
import org.opencv.highgui.Highgui;




import org.opencv.core.Core;

import org.opencv.core.Mat;



public class DetectContour {
    public static void main (String []args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m = Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/circles_rectangles.jpg");
        MaBibliothequeTraitementImage.afficheImage("Cercles Rectangle",m);
        Mat hsv_image = new Mat();
        hsv_image = MaBibliothequeTraitementImage.transformeBGRversHSV(m);
        MaBibliothequeTraitementImage.afficheImage("Cercles Rectangle HSV",hsv_image);


    }
    
}
