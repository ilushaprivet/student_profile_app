//HDC-HGP Ilia Ermolov - 3106798
// EXTRA FEATURE - image opacity regulation

package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OpacitySelector extends HBox {
  	
  //declare variable that require class scope
  public double opacity;

  //Constructor
  public OpacitySelector() {
    //instantiate variables
    opacity = 1;
    
    //label and slider from 0 to 1
    Label lblOpacity = new Label("Opacity: ");
	Slider opacitySlider = new Slider(0, 1, 1);
	
	//listener for the slider
	opacitySlider.valueProperty().addListener(new ChangeListener<Number>() {
        public void changed(ObservableValue <? extends Number > obs, Number oldVal, Number newVal)
        {
	    opacity=newVal.doubleValue();
        }
	});

    // Create an HBox to hold the slider and label
    HBox redBox = new HBox(lblOpacity, opacitySlider);
    redBox.setSpacing(10);

    // Add the slider to the VBox and padding
    getChildren().addAll(lblOpacity, opacitySlider);
    setPadding(new Insets(10));
  }
 
}
