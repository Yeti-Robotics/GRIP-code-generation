package grip.sample1;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class FindRedContoursApp {

	// Camera settings
	static final int IMG_WIDTH = 640;
	static final int IMG_HEIGHT = 480;
	static double exposure = -1.0;

	public static void main(String[] args) {
		RedContourVisionPipeline pipeline = new RedContourVisionPipeline();
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("localhost");
		NetworkTable table = NetworkTable.getTable("ContoursDetected");

		// Creating camera object
		VideoCapture camera = new VideoCapture(0);
		camera.set(Videoio.CAP_PROP_FRAME_WIDTH, IMG_WIDTH);
		camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, IMG_HEIGHT);
		if (exposure > -1.0) {
			System.out.println("\t" + -1.0);
			camera.set(Videoio.CAP_PROP_AUTO_EXPOSURE, 0);
			camera.set(Videoio.CAP_PROP_EXPOSURE, exposure);
		}
		if (!camera.isOpened()) {
			throw new RuntimeException("Camera will not open");
		}

		// Never-ending processing loop
		while (true) {
			Mat mat = new Mat();
			camera.read(mat);
			pipeline.process(mat);
			System.out.println(pipeline.convexHullsOutput().size() + " contours found");
			if (pipeline.convexHullsOutput().size() > 0) {
				Point[] points = pipeline.convexHullsOutput().get(0).toArray();
				for (int i = 0; i < points.length; i++) {
					System.out.print(points[i] + ", ");
				}
				System.out.println();
				table.putNumber("pixelX", points[0].x);
				table.putNumber("pixelY", points[0].y);
			}
		}
	}

}