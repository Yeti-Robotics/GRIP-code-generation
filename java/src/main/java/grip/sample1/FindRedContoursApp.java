package grip.sample1;

import static edu.wpi.first.wpilibj.vision.GripRunner.makeCamera;
import static edu.wpi.first.wpilibj.vision.GripRunner.makeWindow;

import edu.wpi.first.wpilibj.vision.GripRunner;
import edu.wpi.first.wpilibj.vision.GripRunner.Listener;
import edu.wpi.first.wpilibj.vision.VideoViewer;

public class FindRedContoursApp {

	static final int IMG_WIDTH = 320;
	static final int IMG_HEIGHT = 240;

	final VideoViewer window;
	final Listener<RedContourVisionPipeline> listener;
	final GripRunner<RedContourVisionPipeline> gripRunner;

	public FindRedContoursApp() {
		this.window = makeWindow("GRIP", IMG_WIDTH, IMG_HEIGHT);
		this.listener = (this.window != null) ? (processor -> {
			window.imshow(processor.rgbThresholdOutput());
		}) : null;
		this.gripRunner = new GripRunner<>(makeCamera(0, IMG_WIDTH, IMG_HEIGHT, -1.0), new RedContourVisionPipeline(),
				listener);
	}

	public static void main(String[] args) {
		FindRedContoursApp app = new FindRedContoursApp();
		app.gripRunner.runForever();
	}

}