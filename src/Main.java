import java.awt.AWTException;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;
import org.bytedeco.javacpp.helper.opencv_core.CvArr;
public class Main {


	public static void main(String[] args) throws AWTException {
		
		IplImage img1,imgbinG, imgbinB;
		IplImage imghsv;
		
		
		CvScalar Bminc = opencv_core.cvScalar(95,150,75,0), Bmaxc = opencv_core.cvScalar(145,255,255,0);
		CvScalar Gminc = opencv_core.cvScalar(40,50,60,0), Gmaxc = opencv_core.cvScalar(80,255,255,0);
		
		//img1 = cvLoadImage("Pic.jpg");
		CvArr mask;
		int w=320,h=240;
		imghsv = opencv_core.cvCreateImage(opencv_core.cvSize(w,h),8,3);
		imgbinG = opencv_core.cvCreateImage(opencv_core.cvSize(w,h),8,1);
		imgbinB = opencv_core.cvCreateImage(opencv_core.cvSize(w,h),8,1);
		IplImage imgC = opencv_core.cvCreateImage(opencv_core.cvSize(w,h),8,1);
		opencv_core.CvSeq contour1 = new opencv_core.CvSeq(), contour2=null;
		opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
		opencv_imgproc.CvMoments moments = new opencv_imgproc.CvMoments(Loader.sizeof(CvMoments.class));
		
		CvCapture capture1 = opencv_highgui.cvCreateCameraCapture(opencv_highgui.CV_CAP_ANY);
		opencv_highgui.cvSetCaptureProperty(capture1,opencv_highgui.CV_CAP_PROP_FRAME_WIDTH,w);
		opencv_highgui.cvSetCaptureProperty(capture1,opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT,h);
		
		//int i=1;
		while(true)
		{
				
			img1 = opencv_highgui.cvQueryFrame(capture1);
			if(img1 == null){
				System.err.println("No Image");
				break;
				}
				
			imgbinB = ccmFilter.Filter(img1,imghsv,imgbinB,Bmaxc, Bminc, contour1, contour2, storage,moments,1,0);
			imgbinG = ccmFilter.Filter(img1,imghsv,imgbinG,Gmaxc, Gminc, contour1, contour2, storage,moments,0,1);
					
			opencv_core.cvOr(imgbinB,imgbinG,imgC,mask=null);
			opencv_highgui.cvShowImage("Combined",imgC);	
			opencv_highgui.cvShowImage("Original",img1);
			char c = (char)opencv_highgui.cvWaitKey(15);
			if(c=='q') break;
					
		}
		opencv_core.cvReleaseImage(imghsv);
		opencv_core.cvReleaseImage(imgbinG);
		opencv_core.cvReleaseImage(imgbinB);
		opencv_core.cvReleaseImage(imghsv);
		opencv_core.cvReleaseMemStorage(storage);
		opencv_highgui.cvReleaseCapture(capture1);
				
	}

}
