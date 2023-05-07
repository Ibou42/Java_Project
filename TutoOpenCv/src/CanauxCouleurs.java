import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import org.opencv.core.Core;
//import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class CanauxCouleurs {
    public static void Imshow(String title,Mat img){
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".png", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        java.awt.image.BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.setTitle(title);
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m =  Highgui.imread("./assets/bgr.png");
        Vector <Mat> channels = new Vector<Mat>();
        Core.split(m,channels);
        // BGR order 
        for (int i=0;i<channels.size();i++){
            Imshow(Integer.toString(i),channels.get(i));

        }
    }
}
