import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Fonctions {
    public static ArrayList<Mat> detect_panneau_image(String fichier){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ArrayList<Mat> res = new ArrayList<Mat>();
        Mat imageOriginale=Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/Temoin.png",Highgui.CV_LOAD_IMAGE_COLOR);
		Mat imageTransformee=MaBibliothequeTraitementImage.transformeBGRversHSV(imageOriginale);
		Mat imageSaturee=MaBibliothequeTraitementImage.seuillage(imageTransformee, 6, 170, 110);
	
		
		//Ouverture le l'image et saturation des rouges
		Mat m=Highgui.imread(fichier,Highgui.CV_LOAD_IMAGE_COLOR);
		MaBibliothequeTraitementImage.afficheImage("Image test�e", m);
		Mat transformee=MaBibliothequeTraitementImage.transformeBGRversHSV(m);

		//la methode seuillage est ici extraite de l'archivage jar du meme nom 
		Mat saturee=MaBibliothequeTraitementImage.seuillage(transformee, 6, 170, 110);
		Mat objetrond = null;

		//Cr�ation d'une liste des contours � partir de l'image satur�e
		List<MatOfPoint> ListeContours= MaBibliothequeTraitementImage.ExtractContours(saturee);
		int i=0;
		double [] scores=new double [6];
		//Pour tous les contours de la liste
		for (MatOfPoint contour: ListeContours  ){
			i++;
			objetrond=MaBibliothequeTraitementImage.DetectForm(m,contour);

			if (objetrond!=null){
				MaBibliothequeTraitementImage.afficheImage("Objet rond det�ct�", objetrond);
				scores[0]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref30.jpg");
				scores[1]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref50.jpg");
				scores[2]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref70.jpg");
				scores[3]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref90.jpg");
				scores[4]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref110.jpg");
				scores[5]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/refdouble.jpg");
				//recherche de l'index du maximum et affichage du panneau detect�
				double scoremax=-1;
				int indexmax=0;
				for(int j=0;j<scores.length;j++){
					if (scores[j]>scoremax){
						scoremax=scores[j];
						indexmax=j;
					}
				}	
				if(scoremax<0){System.out.println("Aucun Panneau d�t�ct�");}
				else{switch(indexmax){
				case -1:;break;
				case 0:System.out.println("Panneau 30 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref30.jpg"));
                break;
				case 1:System.out.println("Panneau 50 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref50.jpg"));
                break;
				case 2:System.out.println("Panneau 70 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/asets/ref70.jpg"));
                break;
				case 3:System.out.println("Panneau 90 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref90.jpg"));
                break;
				case 4:System.out.println("Panneau 110 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref110.jpg"));
                break;
				case 5:System.out.println("Panneau interdiction de d�passer d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/refdouble.jpg"));
                break;
				}}

			}
		}	
        return res;
        
    }

    public static ArrayList<Mat> detect_panneau_imagebis(Mat fichier){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ArrayList<Mat> res = new ArrayList<Mat>();
        Mat imageOriginale=Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/Temoin.png",Highgui.CV_LOAD_IMAGE_COLOR);
		Mat imageTransformee=MaBibliothequeTraitementImage.transformeBGRversHSV(imageOriginale);
		Mat imageSaturee=MaBibliothequeTraitementImage.seuillage(imageTransformee, 6, 170, 110);
	
		
		//Ouverture le l'image et saturation des rouges
		Mat m=Highgui.imshow(fichier,Highgui.CV_LOAD_IMAGE_COLOR);
		MaBibliothequeTraitementImage.afficheImage("Image test�e", m);
		Mat transformee=MaBibliothequeTraitementImage.transformeBGRversHSV(m);

		//la methode seuillage est ici extraite de l'archivage jar du meme nom 
		Mat saturee=MaBibliothequeTraitementImage.seuillage(transformee, 6, 170, 110);
		Mat objetrond = null;

		//Cr�ation d'une liste des contours � partir de l'image satur�e
		List<MatOfPoint> ListeContours= MaBibliothequeTraitementImage.ExtractContours(saturee);
		int i=0;
		double [] scores=new double [6];
		//Pour tous les contours de la liste
		for (MatOfPoint contour: ListeContours  ){
			i++;
			objetrond=MaBibliothequeTraitementImage.DetectForm(m,contour);

			if (objetrond!=null){
				MaBibliothequeTraitementImage.afficheImage("Objet rond det�ct�", objetrond);
				scores[0]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref30.jpg");
				scores[1]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref50.jpg");
				scores[2]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref70.jpg");
				scores[3]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref90.jpg");
				scores[4]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref110.jpg");
				scores[5]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/refdouble.jpg");
				//recherche de l'index du maximum et affichage du panneau detect�
				double scoremax=-1;
				int indexmax=0;
				for(int j=0;j<scores.length;j++){
					if (scores[j]>scoremax){
						scoremax=scores[j];
						indexmax=j;
					}
				}	
				if(scoremax<0){System.out.println("Aucun Panneau d�t�ct�");}
				else{switch(indexmax){
				case -1:;break;
				case 0:System.out.println("Panneau 30 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref30.jpg"));
                break;
				case 1:System.out.println("Panneau 50 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref50.jpg"));
                break;
				case 2:System.out.println("Panneau 70 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/asets/ref70.jpg"));
                break;
				case 3:System.out.println("Panneau 90 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref90.jpg"));
                break;
				case 4:System.out.println("Panneau 110 d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref110.jpg"));
                break;
				case 5:System.out.println("Panneau interdiction de d�passer d�t�ct�");
                res.add(Highgui.imread("/Users/ibrahim/Java_Project/TutoOpenCv/assets/refdouble.jpg"));
                break;
				}}

			}
		}	
        return res;
        
    }
    public static ArrayList<Mat> detect_panneau_video(String fichier){
        ArrayList<Mat> res = new ArrayList<Mat>();
        
        return res;

    }
    public static void afficheImage(Mat img){
		MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".png", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        java.awt.image.BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}

}
