//HDC-HGP Ilia Ermolov - 3106798

package application;

//Standard JavaFX Imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//imports for controls
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
//layouts
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//file imports
import java.io.File;
import java.util.regex.Pattern;

//geometry
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class StudentProfileFX extends Application {
	BorderPane bpMain; // Main layout of window, needs to be global so we can change the background
	Label lblLogo, lblName, lblNumber, lblProgramme; // all the labels
	Button btnChoose, btnUpdate, btnCustomise; // all the buttons
	Image img; // profile image file
	ImageView imview; // profile image view

	public StudentProfileFX() {
		
		// setting default values for the labels
		lblLogo = new Label("My Student Profile");
		lblLogo.setId("lblLogo");
		lblName = new Label("Ilia Ermolov");
		lblNumber = new Label("3106798");
		lblProgramme = new Label("Higher Diploma in Science in Computing");
		
		// labeling the buttons
		btnChoose = new Button("Choose");
		btnUpdate = new Button("Update Details");
		btnCustomise = new Button("Customise");
		
		// default image setup
		try {
			img = new Image("./Assets/logo1.jpg");
			imview = new ImageView(img);
		} catch (Exception e) {
			System.err.println("Error loading the image");
		}

	}

	// Event Handling for main UI
	@Override
	public void init() {
		// Handle events for Choose button
		btnChoose.setOnAction(event -> showPictureDialog());
		// Handle events for UpdateProfile button
		btnUpdate.setOnAction(event -> showDialog());
		// Handle events for Customize button
		btnCustomise.setOnAction(event -> showColorPicker());
	}

	// Show System Dialog to choose a profile photo (.png only)
	private void showPictureDialog() {
		{
			//filechooser object
			FileChooser fc = new FileChooser();
			
			//set the title for the window and the extension filter
			fc.setTitle("Open Image File");
			fc.getExtensionFilters().add(new ExtensionFilter("image only", "*.png"));
			File sf = fc.showOpenDialog(null);
			
			//if the file is chosen, change the image
			if (sf != null) {
				try {
					img = new Image(sf.toURI().toString());
					imview.setImage(img);

				} catch (Exception e) {
					System.out.println("Error opening the text file");
					e.printStackTrace();
				}
			}
		}
	}

	// Show Custom Dialog to choose background color
	private void showColorPicker() {
		
		//instantiate layout components
		Stage dialogStage = new Stage();
		BorderPane bpDialog = new BorderPane();
		HBox hbButtons = new HBox();
		ColorSelector cs = new ColorSelector();
		
		//opacity selector is the special feature lass
		OpacitySelector os = new OpacitySelector();

		// set title, set default width & height, icon
		dialogStage.setTitle("Customise interface");
		try {
			dialogStage.getIcons().add(new Image("file:assets/ledger.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file");
			e.printStackTrace();
		}
		dialogStage.setWidth(500);
		dialogStage.setHeight(300);

		// add padding
		bpDialog.setId("bpDialog");

		// create components
		Label lblColor = new Label("Background color: #" + cs.getHex());
		Button btnCancel = new Button("Cancel");
		Button btnOk = new Button("Ok");
		
		// manage size of the ok button
		btnOk.setMinSize(80, 0);

		// add components to the layout, set their position and padding
		hbButtons.getChildren().addAll(btnCancel, btnOk, lblColor);
		hbButtons.setSpacing(20);
		bpDialog.setTop(cs);
		bpDialog.setCenter(os);
		bpDialog.setBottom(hbButtons);
		hbButtons.setPadding(new Insets(10, 0, 0, 0));
		hbButtons.setId("hbButtons");
		os.setPadding(new Insets(20));
		cs.setPadding(new Insets(20));

		// event handling - close and ok
		btnCancel.setOnAction(buttonclicked -> dialogStage.close());
		btnOk.setOnAction(buttonclicked -> {
			//changebackground now takes two arguments
			changeBackground(cs.getHex(), os.opacity);
			dialogStage.close();
		});

		// create scene for the dialog
		Scene s = new Scene(bpDialog);
		
		//apply a stylesheet
		s.getStylesheets().add("application/cm_style.css");

		// set the scene
		dialogStage.setScene(s);

		// show the stage
		dialogStage.show();

	}

	//show a dialog to update user details
	private void showDialog() {
		
		Stage dialogStage = new Stage();

		// set title, set default width & height and icon
		dialogStage.setTitle("Please enter your details:");
		try {
			dialogStage.getIcons().add(new Image("file:assets/ledger.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file");
			e.printStackTrace();
		}
		dialogStage.setWidth(570);
		dialogStage.setHeight(250);

		// layout for the dialog
		BorderPane bpDialog = new BorderPane();
		bpDialog.setId("bpDialog");

		// subcontainers
		GridPane gpDialog = new GridPane();
		HBox hbButtons = new HBox();
		hbButtons.setId("hbButtons");

		// create components
		Label lblNameNew = new Label("Full name:");
		TextField txtfName = new TextField();
		Label lblNumberNew = new Label("Student number:");
		TextField txtfNumber = new TextField();
		Label lblProgrammeNew = new Label("Programme of Study:");
		Button btnCancel = new Button("Cancel");
		Button btnOk = new Button("Ok");
		
		//the dropdown field
		ComboBox<String> cbDropdown = new ComboBox<>();
		cbDropdown.getItems().addAll("Higher Diploma in Science in Computing", "Bachelor of Arts in Business and Management", "Master's Degree in Computing");
		cbDropdown.setValue("Higher Diploma in Science in Computing"); // set a default value

		// manage size of the ok button
		btnOk.setMinSize(80, 0);

		// add components to the layout
		gpDialog.add(lblNameNew, 0, 0);
		gpDialog.add(txtfName, 1, 0);
		gpDialog.add(lblNumberNew, 0, 1);
		gpDialog.add(txtfNumber, 1, 1);
		gpDialog.add(lblProgrammeNew, 0, 2);
		gpDialog.add(cbDropdown, 1, 2);
		hbButtons.getChildren().addAll(btnOk, btnCancel);
		hbButtons.setAlignment(Pos.BOTTOM_RIGHT);

		// spacing
		gpDialog.setVgap(10);
		gpDialog.setHgap(20);
		hbButtons.setSpacing(10);
		hbButtons.setPadding(new Insets(10, 0, 0, 0));
		gpDialog.setPadding(new Insets(20));

		// add sublayouts to the main layout
		bpDialog.setTop(gpDialog);
		bpDialog.setBottom(hbButtons);

		// event handling
		cbDropdown.setOnAction(e -> {
			String selectedOption = cbDropdown.getValue();
			System.out.println(selectedOption);
		});
		btnCancel.setOnAction(buttonclicked -> dialogStage.close());
		btnOk.setOnAction(buttonclicked -> {

			// get the text from the textfield
			String studentName = txtfName.getText();

			// get text from the student no
			String studentNum = txtfNumber.getText();

			// validation for name: characters only. Minimum 1 character, maximum 50 characters.
			// if 50 characters is the limit for the whole string, we just check its size 
			String pattern = "[A-Z]{1}[a-z]*\s[A-Z]{1}[a-z]*"; // regex

			// validation for number: digits only. Maximum 8 digits, minimum 7 digits.
			String patternnumber = "[0-9]{7,8}"; // regex

			// double conditional
			if ((studentName.isEmpty())||(!Pattern.matches(pattern, studentName))||(studentName.length()>50)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong name pattern");
				alert.setHeaderText("Please provide a valid user name");
				alert.setContentText(
						"Make sure your name:\n- Is not empty\n- Starts with a capital letter\n- The rest letters are lowercase\n- Has the space between the name and surname\n- Is no longer than 50 characters");

				alert.showAndWait();
			} else if ((studentNum.isEmpty())||!Pattern.matches(patternnumber, studentNum)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong number pattern");
				alert.setHeaderText("Please provide a valid student number");
				alert.setContentText("Make sure your number:\n- Is not empty\n- Only contains numbers 0-9\n- Is 7 to 9 numbers long");

				alert.showAndWait();
			}

			else {

				lblName.setText(studentName);
				lblNumber.setText(studentNum);
				lblProgramme.setText(cbDropdown.getValue());
				dialogStage.close();
			}
		});

		// create scene for the dialog
		Scene s = new Scene(bpDialog);
		
		//apply a stylesheet
		s.getStylesheets().add("application/cm_style.css");

		// set the scene
		dialogStage.setScene(s);

		// show the stage
		dialogStage.show();
	}

	// Set up the main UI window & scene
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Set the title
		primaryStage.setTitle("3106798 - StudentProfileFX");
		try {
			primaryStage.getIcons().add(new Image("file:assets/ledger.png"));
		} catch (Exception e) {
			System.out.println("Error reading image file");
			e.printStackTrace();
		}

		// Set the width and height.
		primaryStage.setWidth(800);
		primaryStage.setHeight(400);

		// managing containers
		bpMain = new BorderPane(); // main container (background to be changed)
		bpMain.setId("bpMain");

		// setting default background of main UI to last 6 digits of the student number
		changeBackground("106798", 1);
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(50, 10, 10, 50));
		
		// bind sizes of ImageView and Buttons to 25% of window size and preserve ratio
		imview.setPreserveRatio(true);
		imview.fitWidthProperty().bind(primaryStage.widthProperty().divide(4));
		btnChoose.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
		btnUpdate.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
		
		//make em lil bigger
		btnChoose.setMinHeight(30);
		btnUpdate.setMinHeight(30);
		
		// Add components to the gridpane
		gp.add(imview, 0, 0, 1, 5);
		gp.add(lblLogo, 1, 0);
		gp.add(lblName, 1, 1);
		gp.add(lblProgramme, 1, 3);
		gp.add(btnUpdate, 1, 5);
		gp.add(lblNumber, 1, 2);
		gp.add(btnChoose, 0, 5);

		// Sublayout HBox for buttons
		HBox hbButtons = new HBox();
		hbButtons.setId("hbButtons");
		hbButtons.setPadding(new Insets(10, 0, 0, 0));
		hbButtons.getChildren().add(btnCustomise);
		hbButtons.setAlignment(Pos.BOTTOM_RIGHT);
		btnCustomise.setAlignment(Pos.BOTTOM_RIGHT);

		//positioning subcontainers
		bpMain.setTop(gp);
		bpMain.setBottom(hbButtons);
		
		// Create a scene
		Scene s = new Scene(bpMain);
		
		//apply a stylesheet
		s.getStylesheets().add("application/cm_style.css");

		// Set the scene
		primaryStage.setScene(s);

		// Show the stage
		primaryStage.show();

	}

	//Launch the application
	public static void main(String[] args) {
		// Launch the application.
		launch();
	}

	//changeBackground method now takes two arguments - hex color and double opacity and changes the main background and image opacity
	public void changeBackground(String hexcode, double opacity) {
		// change background color of main layout
		bpMain.setStyle("-fx-background-color:\n" + "            linear-gradient(" + "#" + hexcode + ", #FFFFFF);");
		imview.setOpacity(opacity);
		
	}
}
