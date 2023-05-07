import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import utilitaireAgreg.*;
public class AnalyseVideo {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	static Mat imag = null;

	public static void main(String[] args) {
		JFrame jframe = new JFrame("Detection de panneaux sur un flux vid�o");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel vidpanel = new JLabel();
		jframe.setContentPane(vidpanel);
		jframe.setSize(720, 480);
		jframe.setVisible(true);

		Mat frame = new Mat();
		VideoCapture camera = new VideoCapture("/Users/ibrahim/Java_Project/TutoOpenCv/assets/video1.mp4");
		Mat PanneauAAnalyser = null;

			while (camera.read(frame)) {
			// completer		
			//transformation hsv 
			Mat imageTransformee=MaBibliothequeTraitementImage.transformeBGRversHSV(frame);
			// saturation des rouges 
			Mat imageSaturee=MaBibliothequeTraitementImage.seuillage(imageTransformee, 6, 170, 110);	
			//liste de contours 
			List<MatOfPoint> ListeContours= MaBibliothequeTraitementImage.ExtractContours(imageSaturee);
			int i=0;
			//je test si y'a des contours 
			for (MatOfPoint contour: ListeContours  ){
				i++;
				PanneauAAnalyser=MaBibliothequeTraitementImage.DetectForm(imageSaturee,contour);

				if (identifiepanneau(PanneauAAnalyser)==0){
					System.out.println("Panneau 30 d�t�ct�");
				}
				else if (identifiepanneau(PanneauAAnalyser)==1){
					System.out.println("Panneau 50 d�t�ct�");
				}
				else if (identifiepanneau(PanneauAAnalyser)==2){
					System.out.println("Panneau 70 d�t�ct�");
				}
				else if (identifiepanneau(PanneauAAnalyser)==3){
					System.out.println("Panneau 90 d�t�ct�");
				}
				else if (identifiepanneau(PanneauAAnalyser)==4){
					System.out.println("Panneau 110 d�t�ct�");
				}
				else if (identifiepanneau(PanneauAAnalyser)==5){
					System.out.println("Panneau interdiction depassement");
				}
				else{
					System.out.println("Aucun panneau");
				}				
			}
			//J'affiche le panneau de reference et la phrase 

			ImageIcon image = new ImageIcon(Mat2bufferedImage(frame));
			vidpanel.setIcon(image);
			vidpanel.repaint();
		}
	}






	public static BufferedImage Mat2bufferedImage(Mat image) {
		MatOfByte bytemat = new MatOfByte();
		Highgui.imencode(".jpg", image, bytemat);
		byte[] bytes = bytemat.toArray();
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}



	public static int identifiepanneau(Mat objetrond){
		double [] scores=new double [6];
		int indexmax=-1;
		if (objetrond!=null){
			scores[0]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref30.jpg");
			scores[1]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref50.jpg");
			scores[2]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref70.jpg");
			scores[3]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref90.jpg");
			scores[4]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/ref110.jpg");
			scores[5]=MaBibliothequeTraitementImage.tauxDeSimilitude(objetrond,"/Users/ibrahim/Java_Project/TutoOpenCv/assets/refdouble.jpg");

			double scoremax=scores[0];

			for(int j=1;j<scores.length;j++){
				if (scores[j]>scoremax){scoremax=scores[j];indexmax=j;}}	


		}
		return indexmax;
	}


}