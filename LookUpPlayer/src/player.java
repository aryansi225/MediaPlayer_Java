import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


/*Created by Eclipse
 * User: Aryan Singh
 * Date: 03/11/2016
 * Time: 12:13
 */

public class player extends Application{
	public final boolean stoprequested = false;
	String path = "C:/Users/Aryan/Desktop/udacity/player/LookUpPlayer/trailers/Blue.mp4";
	Media media = new Media(new File(path).toURI().toString());
	final MediaPlayer player1 = new MediaPlayer(media);
	final MediaView view = new MediaView(player1);
	StackPane root = new StackPane();
	final double[] w = {960};
	final double[] h = {540};
	final double[] k = {100};

	public static void main(String args[]){
		//To launch stand alone applications
		launch(args);
	}
	@Override
	public void start(final Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Look Up Player");
		
		root.setStyle("-fx-background-color: Black");
		
		//final Timeline slideIn = new Timeline();
		//final Timeline slideOut = new Timeline();
		/*root.setOnMouseExited(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				slideOut.play();
			}
			
		});
		root.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				slideIn.play();
			}
			
		});*/
		final VBox vbox = new VBox();
		final Slider slider = new Slider();
		vbox.getChildren().add(slider);
		
		final HBox hbox = new HBox(2);
		final int bands = player1.getAudioSpectrumNumBands();
		final Rectangle rects[] = new Rectangle[bands]; 
		for(int i=0;i<rects.length;i++){
			rects[i]=new Rectangle();
			rects[i].setFill(Color.TURQUOISE);
			hbox.getChildren().add(rects[i]);
		}
		vbox.getChildren().add(hbox);
		/*HBox toolBar = new HBox();
		toolBar.setPadding(new Insets(20));
		toolBar.setAlignment(Pos.CENTER);
		toolBar.alignmentProperty().isBound();
		toolBar.setSpacing(5);
		//toolBar.setStyle("-fx-background-color: Black");
		Image playButtonImage = new Image(new File("C:/Users/Aryan/Desktop/udacity/player/LookUpPlayer/src/play.png").toURI().toString());
		Button playButton = new Button();
		playButton.setGraphic(new ImageView(playButtonImage));
		playButton.setStyle("-fx-background-color: Black");*/

		/*playButton.setOnAction((ActionEvent e) -> {
				player1.play();
		});*/
		/*toolBar.getChildren().add(playButton);
		vbox.getChildren().add(toolBar);*/
		final DoubleProperty width = view.fitWidthProperty();
		final DoubleProperty height = view.fitHeightProperty();
		width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
		view.setPreserveRatio(true);
		root.getChildren().add(view);
		root.getChildren().add(vbox);
		Scene scene = new Scene(root, 960, 540, Color.BLACK);
		stage.setScene(scene);
		stage.show();
		player1.play();
		player1.setOnReady(new Runnable(){
			@Override
			public void run(){
				hbox.setMinWidth(w[0]);
				slider.setMin(0.0);
				slider.setValue(0.0);
				slider.setMax(player1.getTotalDuration().toSeconds());
			}
		});
	   player1.currentTimeProperty().addListener(new ChangeListener<Duration>(){

		@Override
		public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
			// TODO Auto-generated method stub
			slider.setValue(arg2.toSeconds());
		}   
		 
	   });
	   slider.setOnMouseClicked(new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			player1.seek(Duration.seconds(slider.getValue()));
		}
		   
	   });
	   player1.setAudioSpectrumListener(new AudioSpectrumListener(){

		@Override
		public void spectrumDataUpdate(double arg0, double arg1, float[] arg2, float[] arg3) {
			// TODO Auto-generated method stub
			double bandwidth = root.getWidth()/rects.length;
			for(Rectangle r:rects){
				r.setWidth(bandwidth);
				r.setHeight(2);
			}
			for(int i=0; i<rects.length; i++){
				double h = arg2[i]+60;
				if (h>2){
					rects[i].setHeight(h);
				}
			}
		}
		   
	   });
	   scene.widthProperty().addListener(new ChangeListener<Number>(){

		@Override
		public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
			// TODO Auto-generated method stub
			w[0] = arg2.doubleValue();
			vbox.setMinWidth(w[0]);
			hbox.setMinWidth(w[0]);
		}
		   
	   });
	   scene.heightProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				k[0] = (140.25/900)*arg2.doubleValue();
				h[0] = arg2.doubleValue();
				vbox.setTranslateY(h[0]-k[0]);
				/*slideOut.getKeyFrames().addAll(
						new KeyFrame(new Duration(0),
								new KeyValue(vbox.translateYProperty(), h[0]-k[0]),
								new KeyValue(vbox.opacityProperty(), 0.9)
								),
						new KeyFrame(new Duration(300),
								new KeyValue(vbox.translateYProperty(), h[0]),
								new KeyValue(vbox.opacityProperty(), 0.0)
								)
						
				);
				slideIn.getKeyFrames().addAll(
						new KeyFrame(new Duration(0),
								new KeyValue(vbox.translateYProperty(), h[0]),
								new KeyValue(vbox.opacityProperty(), 0.0)
								),
						new KeyFrame(new Duration(300),
								new KeyValue(vbox.translateYProperty(), h[0]-k[0]),
								new KeyValue(vbox.opacityProperty(), 0.9)
								)
						
				);*/
			}
			   
		});
	   /*	player1.setOnPlaying(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				
				CascadeClassifier faceClassifier = new CascadeClassifier("C:/Users/Aryan/Desktop/udacity/player/haarcascade_frontalface_default.xml");
				VideoCapture videoDevice = new VideoCapture();
				MatOfRect faces = new MatOfRect();
				videoDevice.open(0);
				if(videoDevice.isOpened()){
					while(faces.toArray().length>0)
					{
						Mat frameCapture = new Mat();
						videoDevice.read(frameCapture);
						
						faceClassifier.detectMultiScale(frameCapture, faces);
						System.out.println(String.format("%s FACES", faces.toArray().length));
					}
					player1.pause();
				}
				videoDevice.release();
			}
	   		
	   	});
	   	player1.setOnPaused(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				
				CascadeClassifier faceClassifier = new CascadeClassifier("C:/Users/Aryan/Desktop/udacity/player/haarcascade_frontalface_default.xml");
				VideoCapture videoDevice = new VideoCapture();
				MatOfRect faces = new MatOfRect();
				videoDevice.open(0);
				if(videoDevice.isOpened()){
					while(faces.toArray().length<=0)
					{
						Mat frameCapture = new Mat();
						videoDevice.read(frameCapture);
						
						faceClassifier.detectMultiScale(frameCapture, faces);
						System.out.println(String.format("%s FACES", faces.toArray().length));
					}
					player1.play();
				}
				videoDevice.release();
			}
	   		
	   	});*/
	}
}
