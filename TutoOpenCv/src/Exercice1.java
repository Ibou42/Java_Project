//Ici on va afficher la matrice de l'image que l'on importe

import java.io.File;

import org.opencv.core.Core;
//import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class Exercice1 {
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //Mat m = LectureImage("/Users/ibrahim/Java_Project/TutoOpenCv/assets/opencv.png");
        Mat m = Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/opencv.png");
        for (int i=0;i<m.height();i++){
            for(int j=0;j<m.width();j++){
                double [] BGR = m.get(i,j);
                if (BGR[0] == 255 && BGR[1]==255 && BGR[2]==255){
                    System.out.print(".");
                }
                else{
                    System.out.print("+");
                }
            }
        }
        System.out.println();
    }
    
    public static Mat LectureImage(String fichier){
        File f = new File(fichier);
        Mat m = Highgui.imread(f.getAbsolutePath());
        return m;
    }
}
