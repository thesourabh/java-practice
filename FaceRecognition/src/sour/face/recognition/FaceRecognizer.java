package sour.face.recognition;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sour.face.recognition.javafaces2.FaceRecognition;
import sour.face.recognition.javafaces2.ImageUtils;
import sour.face.recognition.javafaces2.MatchResult;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.*;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;
import com.googlecode.javacv.cpp.videoInputLib.videoInput;
import com.googlecode.javacpp.Loader;

public class FaceRecognizer implements Runnable {

	private static final int numOfSeconds = 10;

	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final int DELAY = 100; // ms
	private static final int CAMERA_ID = 0;
	private static final int IM_SCALE = 4;
	// private static final int SMALL_MOVE = 5;
	private static final int DETECT_DELAY = 500;
	// time (ms) between each face detection
	private static final int MAX_TASKS = 4;
	// max no. of tasks that can be waiting to be executed
	// cascade definition to be used for face detection
	private static final String FACE_CASCADE_FNM = "res/haarcascade_frontalface_alt.xml";
	// for recognizing a detected face image
	private static final String FACE_DIR = "trainingImages";
	// private static final String FACE_FNM = "savedFace.png";
	private static final String RECOG_SAVE_FILE = "facerec.txt";

	private static final int FACE_WIDTH = 125;
	private static final int FACE_HEIGHT = 150;

	private IplImage snapIm = null;
	private volatile boolean isRunning;
	private volatile boolean isFinished;

	// used for the average ms snap time information
	private int imageCount = 0;
	private long totalTime = 0;

	// JavaCV variables
	private CvHaarClassifierCascade classifier;
	private CvMemStorage storage;
	private IplImage grayIm;

	// used for thread that executes the face detection
	private ExecutorService executor;
	private AtomicInteger numTasks;
	// used to record number of detection tasks
	private long detectStartTime = 0;

	private Rectangle faceRect; // holds the coordinates of the highlighted face

	private volatile boolean recognizeFace = false;
	private FaceRecognition faceRecog; // this class comes from the javaFaces
										// example
	private String faceName = null; // name associated with last recognized face

	Map<String, Integer> people = new HashMap<String, Integer>();

	public FaceRecognizer() {

		Loader.load(opencv_objdetect.class);
		faceRecog = new FaceRecognition();
		executor = Executors.newSingleThreadExecutor();
		/*
		 * this executor manages a single thread with an unbounded queue. Only
		 * one task can be executed at a time, the others wait.
		 */
		numTasks = new AtomicInteger(0);
		// used to limit the size of the executor queue
		setRecog();
		initDetector();
		faceRect = new Rectangle();

		new Thread(this).start(); // start updating the panel's image

	}

	private void initDetector() {
		// instantiate a classifier cascade for face detection
		classifier = new CvHaarClassifierCascade(cvLoad(FACE_CASCADE_FNM));
		if (classifier.isNull()) {
			System.out.println("\nCould not load the classifier file: "
					+ FACE_CASCADE_FNM);
			System.exit(1);
		}

		storage = CvMemStorage.create(); // create storage used during object
											// detection
	} // end of initDetector()

	public void run()
	/*
	 * display the current webcam image every DELAY ms The time statistics
	 * gathered here will NOT include the time taken to find a face, which are
	 * farmed out to a separate thread in trackFace().
	 * 
	 * Tracking is only started at least every DETECT_DELAY (1000) ms, and only
	 * if the number of tasks is < MAX_TASKS (one will be executing, the others
	 * waiting)
	 */
	{
		FrameGrabber grabber = initGrabber(CAMERA_ID);
		if (grabber == null)
			return;

		long duration;
		int maxTime = 10000;
		long actualStartTime = System.currentTimeMillis();
		int actualTotalTime = 0;
		isRunning = true;
		isFinished = false;
		getPeopleNames();

		while (isRunning) {
			long startTime = System.currentTimeMillis();

			snapIm = picGrab(grabber, CAMERA_ID);

			if (((System.currentTimeMillis() - detectStartTime) > DETECT_DELAY)
					&& (numTasks.get() < MAX_TASKS))
				trackFace(snapIm);
			imageCount++;

			duration = System.currentTimeMillis() - startTime;
			totalTime += duration;

			actualTotalTime += (System.currentTimeMillis() - actualStartTime);
			System.out.println(actualTotalTime + "  " + duration);

			if (totalTime > 300) {
				isRunning = false;
			}

			if (duration < DELAY) {
				try {
					Thread.sleep(DELAY - duration); // wait until DELAY time has
													// passed
				} catch (Exception ex) {
				}
			}
		}
		closeGrabber(grabber, CAMERA_ID);
		System.out.println("Execution End");
		int max = 0;
		String recogPerson = "failed";
		for (String key : people.keySet()) {
			int val = people.get(key);
			System.out.println(key + ": " + val);
			if (val > max) {
				max = val;
				recogPerson = key;
			}
		}
		FileWriter fw;
		try {
			fw = new FileWriter(RECOG_SAVE_FILE);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(recogPerson);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isFinished = true;
		System.exit(0);

	}

	private void getPeopleNames() {
		File saveDir = new File(FACE_DIR);
		if (saveDir.exists()) {
			File[] listOfFiles = saveDir.listFiles();
			if (listOfFiles.length > 0) {
				System.out.println("more than 0");
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						try {
							String file = listOfFiles[i].getName();
							Pattern pattern = Pattern.compile("([a-zA-Z]+)");
							Matcher matcher = pattern.matcher(file);
							if (matcher.find()) {
								String name = matcher.group();
								if (!people.containsKey(name)) {
									people.put(name, 0);
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		for (String key : people.keySet())
			System.out.println(key);
	}

	private FrameGrabber initGrabber(int ID) {
		FrameGrabber grabber = null;
		System.out.println("Initializing grabber for "
				+ videoInput.getDeviceName(ID) + " ...");
		try {
			grabber = new OpenCVFrameGrabber(ID);
			grabber.setFormat("dshow"); // using DirectShow
			grabber.setImageWidth(WIDTH); // default is too small: 320x240
			grabber.setImageHeight(HEIGHT);
			grabber.start();
		} catch (Exception e) {
			System.out.println("Could not start grabber");
			System.out.println(e);
			System.exit(1);
		}
		return grabber;
	} // end of initGrabber()

	private IplImage picGrab(FrameGrabber grabber, int ID) {
		IplImage im = null;
		try {
			im = grabber.grab(); // take a snap
		} catch (Exception e) {
			System.out.println("Problem grabbing image for camera " + ID);
		}
		return im;
	} // end of picGrab()

	private void closeGrabber(FrameGrabber grabber, int ID) {
		try {
			grabber.stop();
			grabber.release();
		} catch (Exception e) {
			System.out.println("Problem stopping grabbing for camera " + ID);
		}
	} // end of closeGrabber()

	// ------------------------- face tracking ----------------------------

	private void trackFace(final IplImage img)
	/*
	 * Create a separate thread for the time-consuming detection and recognition
	 * tasks: find a face in the current image, store its coordinates in
	 * faceRect, then recognize the face, and place the person's name in the
	 * top-level name TextField
	 */
	{
		grayIm = scaleGray(img);
		numTasks.getAndIncrement(); // increment no. of tasks before entering
									// queue
		executor.execute(new Runnable() {
			public void run() {
				detectStartTime = System.currentTimeMillis();
				CvRect rect = findFace(grayIm);
				if (rect != null) {
					setRectangle(rect);
					if (recognizeFace) {
						recogFace(img);
						// recognizeFace = false;
					}
				}
				// long detectDuration = System.currentTimeMillis()
				// - detectStartTime;
				// System.out.println(" detection/recognition duration: "
				// + detectDuration + "ms");
				numTasks.getAndDecrement(); // decrement no. of tasks since
											// finished
			}
		});
	} // end of trackFace()

	private IplImage scaleGray(IplImage img)
	/*
	 * Scale the image and convert it to grayscale. Scaling makes the image
	 * smaller and so faster to process, and Haar detection requires a grayscale
	 * image as input
	 */
	{
		// convert to grayscale
		IplImage grayImg = cvCreateImage(cvGetSize(img), IPL_DEPTH_8U, 1);
		cvCvtColor(img, grayImg, CV_BGR2GRAY);

		// scale the grayscale (to speed up face detection)
		IplImage smallImg = IplImage.create(grayImg.width() / IM_SCALE,
				grayImg.height() / IM_SCALE, IPL_DEPTH_8U, 1);
		cvResize(grayImg, smallImg, CV_INTER_LINEAR);

		// equalize the small grayscale
		cvEqualizeHist(smallImg, smallImg);
		return smallImg;
	} // end of scaleGray()

	private CvRect findFace(IplImage grayIm) {
		CvSeq faces = cvHaarDetectObjects(grayIm, classifier, storage, 1.1, 1,
				CV_HAAR_DO_ROUGH_SEARCH | CV_HAAR_FIND_BIGGEST_OBJECT);
		// speed things up by searching for only a single, largest face subimage
		int total = faces.total();
		if (total == 0) {
			// System.out.println("No faces found");
			return null;
		} else if (total > 1) // this case should not happen, but included for
								// safety
			System.out.println("Multiple faces detected (" + total
					+ "); using the first");

		CvRect rect = new CvRect(cvGetSeqElem(faces, 0));

		cvClearMemStorage(storage);
		return rect;
	} // end of findface()

	private void setRectangle(CvRect r)
	/*
	 * Extract the (x, y, width, height) values of the highlighted image from
	 * the JavaCV rectangle data structure, and store them in a Java rectangle.
	 * In the process, undo the scaling which was applied to the image before
	 * face detection was carried out. Report any movement of the new rectangle
	 * compared to the previous one. The updating of faceRect is in a
	 * synchronized block since it may be used for drawing or image saving at
	 * the same time in other threads.
	 */
	{
		synchronized (faceRect) {
			int xNew = r.x() * IM_SCALE;
			int yNew = r.y() * IM_SCALE;
			int widthNew = r.width() * IM_SCALE;
			int heightNew = r.height() * IM_SCALE;
			faceRect.setRect(xNew, yNew, widthNew, heightNew);
		}
	} // end of setRectangle()

	private void recogFace(IplImage img)
	/*
	 * clip the image using the current face rectangle, then try to recognize
	 * it. The use of faceRect is in a synchronized block since it may be being
	 * updated or used for drawing at the same time in other threads.
	 */
	{
		BufferedImage clipIm = null;
		synchronized (faceRect) {
			if (faceRect.width == 0) {
				System.out.println("No face selected");
				return;
			}
			clipIm = ImageUtils.clipToRectangle(img.getBufferedImage(),
					faceRect.x, faceRect.y, faceRect.width, faceRect.height);
		}
		if (clipIm != null)
			matchClip(clipIm);
	} // end of recogFace()

	private void matchClip(BufferedImage clipIm)
	// resize, convert to grayscale, clip to FACE_WIDTH*FACE_HEIGHT, recognize
	{
		// long startTime = System.currentTimeMillis();

		// System.out.println("Matching clip...");
		BufferedImage faceIm = clipToFace(resizeImage(clipIm));
		// FileUtils.saveImage(faceIm, "file-" + (fileCount++) + ".jpg");
		MatchResult result = faceRecog.match(faceIm);
		if (result == null)
			System.out.println("No match found");
		else {
			faceName = result.getName();
			people.put(faceName, people.get(faceName) + 1);
			String distStr = String.format("%.4f", result.getMatchDistance());
			System.out.println("Face: " + faceName + " (matches "
					+ result.getMatchFileName() + "; distance = " + distStr
					+ ")" + totalTime);
		}
		// System.out.println("Match time: "
		// + (System.currentTimeMillis() - startTime) + " ms");
	} // end of matchClip()

	private BufferedImage resizeImage(BufferedImage im)
	/*
	 * resize so *at least* FACE_WIDTH*FACE_HEIGHT size and convert to grayscale
	 */
	{
		double widthScale = FACE_WIDTH / ((double) im.getWidth());
		double heightScale = FACE_HEIGHT / ((double) im.getHeight());
		double scale = (widthScale > heightScale) ? widthScale : heightScale;
		return ImageUtils.toScaledGray(im, scale);
	} // end of resizeImage()

	private BufferedImage clipToFace(BufferedImage im)
	// clip image to FACE_WIDTH*FACE_HEIGHT size
	// I assume the input image is face size or bigger
	{
		int xOffset = (im.getWidth() - FACE_WIDTH) / 2;
		int yOffset = (im.getHeight() - FACE_HEIGHT) / 2;
		return ImageUtils.clipToRectangle(im, xOffset, yOffset, FACE_WIDTH,
				FACE_HEIGHT);
	} // end of clipToFace()

	public void setRecog() {
		recognizeFace = true;
	}

	private static String getRecognized() {
		File file = new File(RECOG_SAVE_FILE);
		if (file.exists()) {
			FileReader fr = null;
			try {
				fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String recogname = "", line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					recogname += line;
				}
				br.close();
				System.out.println("Person recognized (best match): "
						+ recogname);
				return recogname;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "0";
	}

	public static void main(String[] args) {
		new FaceRecognizer();
	}

}
