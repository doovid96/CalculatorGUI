import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/* Calculator class extends Application class.
 * It must have the method start() which is executed.
 * Calculator class implements EventHandler<ActionEvent>.
 * It must have the method handle() with ActionEvent as a parameter. */

public class Calculator extends Application implements EventHandler<ActionEvent> {
	
	Stage CalculatorWindow;
	Scene CalculatorViewer;
	GridPane layout;
	TextArea output;
	Button zeroButton;
	Button oneButton;
	Button twoButton;
	Button threeButton;
	Button fourButton;
	Button fiveButton;
	Button sixButton;
	Button sevenButton;
	Button eightButton;
	Button nineButton;
	Button pointButton;
	Button addButton;
	Button subtractButton;
	Button multiplyButton;
	Button divideButton;
	Button equalButton;
	Button changeSignButton;
	Button clearButton;
	Button allClearButton;
	
	boolean hasPoint;
	/* boolean hasPoint
	 * Disables decimal point when true.
	 * Allows decimal point when false.
	 * Value changes to true in point().
	 * Value changes to false in clear functions. */
	
	boolean error;
	/* boolean error
	 * Keeps track of whether dividing by zero.
	 * Keeps track of imaginary answer.
	 * When toggled, must use all clear. */
	
	char operator;
	/* char operator
	 * Keeps track of the last operator entered.
	 * '\0' when operator is not entered.
	 * Controls which buttons work.
	 * Controls flow of logic. */
	
	char lastChar;
	/* char lastChar
	 * Keeps track of the last button clicked.
	 * May not be equal to operator char.
	 * Critical in equal() method.
	 * Controls flow of logic. */
	
	double operand1;
	/* double operand1
	 * Keeps track of the first operand.
	 * Value changes upon first operator button event. */
	
	double operand2;
	/* double operand2
	 * Keeps track of second operand.
	 * Value changes upon equal button event. */
	
	public static void main(String[] args) {
		/* Calculator class extends Application object.
		 * launch(String[] args) is a static method of Application.
		 * Initializes the launch of the GUI. */
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		/* Calculator class extends Application object..
		 * Overrides the method within Application object.
		 * Called on by launch(String[] args) */
		CalculatorWindow = primaryStage;
		CalculatorWindow.setTitle("Calculator");
		defineLayout();
		defineTextArea();
		defineButtons();
		addTextArea();
		addButtons();
		initializeCalculator();
		CalculatorWindow.setScene(CalculatorViewer);
		CalculatorWindow.show();
	}
	
	@Override
	public void handle(ActionEvent event) {
		/* Calculator class implements EventHandler<ActionEvent>.
		 * handle(ActionEvent event) is required as a result.
		 * Buttons refer to an ActionEvent value.
		 * Calculator buttons are defined to refer to an EventHandler.
		 * So, the Calculator buttons are defined to refer to this object.
		 * The handle method of this implemented interface is then executed,
		 * taking the ActionEvent as a parameter. */
		Object source = event.getSource();
		if (error) {
			/* On any error, only clear the calculator with all clear.
			 * The calculator will not function until clicking on all clear.
			 * A button click would be considered an event. */
			if (source != allClearButton)
				return;
		}
		if (source == zeroButton) {
			zero();
		}
		else if (source == oneButton) {
			one();
		}
		else if (source == twoButton) {
			two();
		}
		else if (source == threeButton) {
			three();
		}
		else if (source == fourButton) {
			four();
		}
		else if (source == fiveButton) {
			five();
		}
		else if (source == sixButton) {
			six();
		}
		else if (source == sevenButton) {
			seven();
		}
		else if (source == eightButton) {
			eight();
		}
		else if (source == nineButton) {
			nine();
		}
		else if (source == pointButton) {
			point();
		}
		else if (source == addButton) {
			add();
		}
		else if (source == subtractButton) {
			subtract();
		}
		else if (source == multiplyButton) {
			multiply();
		}
		else if (source == divideButton) {
			divide();
		}
		else if (source == equalButton) {
			equal();
		}
		else if (source == changeSignButton) {
			negate();
		}
		else if (source == clearButton) {
			clear();
		}
		else if (source == allClearButton) {
			allClear();
		}
	}
	
	private void defineLayout() {
		layout = new GridPane();
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setPadding(new Insets(20,20,20,20));
		layout.setHgap(15);
		layout.setVgap(15);
	}
	
	private void defineTextArea() {
		output = new TextArea();
		output.setPrefColumnCount(20);
		output.setPrefRowCount(1);
		output.setEditable(false);
		output.setText("0");
		GridPane.setColumnSpan(output, 4);
	}
	
	private void defineButtons() {
		allClearButton = makeButton("AC");
		clearButton = makeButton("C");
		changeSignButton = makeButton("+/-");
		
		sevenButton = makeButton("7");
		eightButton = makeButton("8");
		nineButton = makeButton("9");
		divideButton = makeButton("/");
		
		fourButton = makeButton("4");
		fiveButton = makeButton("5");
		sixButton = makeButton("6");
		multiplyButton = makeButton("X");
		
		oneButton = makeButton("1");
		twoButton = makeButton("2");
		threeButton = makeButton("3");
		subtractButton = makeButton("-");
		
		zeroButton = makeButton("0");
		pointButton = makeButton(".");
		equalButton = makeButton("=");
		addButton = makeButton("+");
	}
	
	private void addTextArea() {
		/* layout.add(child, columnIndex, rowIndex); */
		layout.add(output, 0, 0);
	}
	
	private void addButtons() {
		/* layout.add(child, columnIndex, rowIndex); */
		layout.add(allClearButton, 0, 1);
		layout.add(clearButton, 1, 1);
		layout.add(changeSignButton, 2, 1);
		//layout.add(exponentButton, 3, 1);
		
		layout.add(sevenButton, 0, 2);
		layout.add(eightButton, 1, 2);
		layout.add(nineButton, 2, 2);
		layout.add(divideButton, 3, 2);
		
		layout.add(fourButton, 0, 3);
		layout.add(fiveButton, 1, 3);
		layout.add(sixButton, 2, 3);
		layout.add(multiplyButton, 3, 3);
		
		layout.add(oneButton, 0, 4);
		layout.add(twoButton, 1, 4);
		layout.add(threeButton, 2, 4);
		layout.add(subtractButton, 3, 4);
		
		layout.add(zeroButton, 0, 5);
		layout.add(pointButton, 1, 5);
		layout.add(equalButton, 2, 5);
		layout.add(addButton, 3, 5);
	}

	private void initializeCalculator() {
		CalculatorViewer = new Scene(layout,300,400);
		hasPoint = false;
		error = false;
		lastChar = '\0';
		operator = '\0';
		operand1 = 0.0;
		operand2 = 0.0;
	}

	private Button makeButton(String s) {
		/* Make a button given the input string.
		 * Set the size.
		 * Have the event handled by this object.
		 * This Calculator object is an event handler as well. */
		Button button = new Button(s);
		button.setTextAlignment(TextAlignment.CENTER);
		button.setPrefSize(50,50);
		button.setOnAction(this);
		return button;
	}
	
	private void zero() {
		/* Ensures no leading zeroes.
		 * If the last button clicked was an operator, ensures display change.
		 * Otherwise, if appropriate, append a '0' to the end. */
		if (output.getText().matches("[-]0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("0");
			hasPoint = false;
		}
		else if (!output.getText().matches("0")) {
			/* Only executes when a non-operator is entered. */
			output.appendText("0");
		}
		lastChar = '0';
	}
	
	private void one() {
		/* Ensures entry of a new number when necessary.
		 * Otherwise, append. */
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-' 
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("1");
			hasPoint = false;
		}
		else {
			output.appendText("1");
		}
		lastChar = '1';
	}
	
	private void two() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("2");
			hasPoint = false;
		}
		else {
			output.appendText("2");
		}
		lastChar = '2';
	}
	
	private void three() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("3");
			hasPoint = false;
		}
		else {
			output.appendText("3");
		}
		lastChar = '3';
	}
	
	private void four() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("4");
			hasPoint = false;
		}
		else {
			output.appendText("4");
		}
		lastChar = '4';
	}
	
	private void five() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("5");
			hasPoint = false;
		}
		else {
			output.appendText("5");
		}
		lastChar = '5';
	}
	
	private void six() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("6");
			hasPoint = false;
		}
		else {
			output.appendText("6");
		}
		lastChar = '6';
	}
	
	private void seven() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("7");
			hasPoint = false;
		}
		else {
			output.appendText("7");
		}
		lastChar = '7';
	}
	
	private void eight() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("8");
			hasPoint = false;
		}
		else {
			output.appendText("8");
		}
		lastChar = '8';
	}
	
	private void nine() {
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("9");
			hasPoint = false;
		}
		else {
			output.appendText("9");
		}
		lastChar = '9';
	}
	
	private void point() {
		/* Ensures entry of a new number beginning with "0." is displayed.
		 * If a point was not already entered, append.
		 * Ensure hasPoint is true. */
		if (output.getText().matches("0")
				|| lastChar=='+'
				|| lastChar=='-'
				|| lastChar=='*'
				|| lastChar=='/'
				|| lastChar=='=') {
			output.setText("0.");
		}
		else if (!hasPoint) {
			output.appendText(".");
		}
		hasPoint = true;
		lastChar = '.';
	}
	
	private void add() {
		lastChar = '+';
		operator = '+';
		operand1 = Double.parseDouble(output.getText());
	}
	
	private void subtract() {
		lastChar = '-';
		operator = '-';
		operand1 = Double.parseDouble(output.getText());
	}
	
	private void multiply() {
		lastChar = '*';
		operator = '*';
		operand1 = Double.parseDouble(output.getText());
	}
	
	private void divide() {
		lastChar = '/';
		operator = '/';
		operand1 = Double.parseDouble(output.getText());
	}
	
	private void equal() {
		double answer = 0.0;
		switch (lastChar) {
		/* If the last button clicked was an operator,
		 * the calculator should do nothing.
		 * The input should have been a number. */
		case '+':
			return;
		case '-':
			return;
		case '*':
			return;
		case '/':
			return;
		case '=':
			return;
		case '.':
			break;
		default:
			
		}
		switch (operator) {
		case '\0':
			return;
		case '+':
			operand2 = Double.parseDouble(output.getText());
			answer = operand1 + operand2;
			break;
		case '-':
			operand2 = Double.parseDouble(output.getText());
			answer = operand1 - operand2;
			break;
		case '*':
			operand2 = Double.parseDouble(output.getText());
			answer = operand1 * operand2;
			break;
		case '/':
			/* You cannot divide by zero. */
			operand2 = Double.parseDouble(output.getText());
			if (operand2 == 0.0) {
				output.setText("Cannot divide by zero!");
				error = true;
				lastChar = '=';
				operator = '\0';
				return;
			}
			answer = operand1 / operand2;
			break;
		default:
			/* Section of code should not be reachable. */
			System.out.println("ERROR: lastChar: "+lastChar);
			System.out.println("ERROR: operator: " + operator);
		}
		/* If the answer is an integer: */
		if (answer - (int)answer == 0.0) {
			output.setText(Integer.toString((int)answer));
		}
		/* If the answer is not an integer: */
		else {
			output.setText(Double.toString(answer));
		}
		lastChar = '=';
		operator = '\0';
	}
	
	private void negate() {
		if (lastChar == '+'
				|| lastChar == '-'
				|| lastChar == '*'
				|| lastChar == '/') {
			return;
		}
		else if (Double.parseDouble(output.getText()) == 0.0) {
			return;
		}
		else if (output.getText().charAt(0) == '-') {
			output.setText(output.getText().substring(1,output.getText().length()));
		}
		else {
			output.setText("-"+output.getText());
		}
	}
	
	private void clear() {
		/* Clear button on calculator: "C" */
		output.setText("0");
		hasPoint = false;
	}
	
	private void allClear() {
		/* All clear button on calculator: "AC" */
		clear();
		lastChar = '\0';
		operator = '\0';
		error = false;
	}
	
} //class Calculator