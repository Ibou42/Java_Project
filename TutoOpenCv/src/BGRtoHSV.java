
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


import java.util.Arrays;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;



public class BGRtoHSV{

public static void main(String[] args){
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat m = Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/hsv.png");
    Mat output = Mat.zeros(m.size(),m.type());
    Imgproc.cvtColor(m,output,Imgproc.COLOR_BGR2HSV);
    CanauxCouleurs.Imshow("HSV",output);
    Vector<Mat> channels = new Vector<Mat>();
    Core.split(output,channels);
    double hsv_values[][]={{1,255,255},{179,1,255},{179,0,1}};
    for(int i=0;i<3;i++){
        CanauxCouleurs.Imshow(Integer.toString(i)+"HSV",channels.get(i));
        Mat chans[]=new Mat[3];
        for (int j=0;j<3;j++){

            Mat empty = new Mat(m.size(), CvType.CV_8UC1);
            Mat comp = new Mat(m.size(), CvType.CV_8UC1);
            Scalar v = new Scalar(hsv_values[i][j]);
            Core.multiply(empty, v, comp);
            chans[j]=comp;
        }
        chans[i]=channels.get(i);
        Mat dst = Mat.zeros(output.size(),output.type());
        Mat res = Mat.ones(dst.size(),dst.type());
        Core.merge(Arrays.asList(chans),dst);
        Imgproc.cvtColor(dst, res, Imgproc.COLOR_HSV2BGR);
        CanauxCouleurs.Imshow(Integer.toString(i), res);
        


            
        }
    }
}