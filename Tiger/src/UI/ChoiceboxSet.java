/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import tiger.Context;

/**
 *
 * @author coin
 */
public class ChoiceboxSet {
    
    Context context;
    
    private ChoiceBox term;
    private ChoiceBox condition;
    
    private ChangeListener<Number> termListener;
    private ChangeListener<Number> condListener;
    
    public ChoiceboxSet(Context context) {
	this.context = context;
	term = context.term;
	condition = context.condition;
	grade();
    }
    
    public void grade() {
	termListener = new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		context.termCBox = newValue.intValue();
	    }
	};
	
	condListener = new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		int i = newValue.intValue();
		context.conditionCBox = i;
		TextField textField1 = context.textField1;
		TextField textField2 = context.textField2;
		TextField textField3 = context.textField3;
		textField1.setText("");
		textField2.setText("");
		textField3.setText("");
		if (i == 2 || i == 5) {
		    textField1.setVisible(false);
		} else {
		    textField1.setVisible(true);
		}
	    }
	};
	
	add();
    }
    
    public void student(){
	condListener = new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		int i = newValue.intValue();
		context.conditionCBox = i;
		TextField textField1 = context.textField1;
		TextField textField2 = context.textField2;
		TextField textField3 = context.textField3;
		textField1.setText("");
		textField2.setText("");
		textField3.setText("");
		if (i == 2 || i == 4) {
		    textField1.setVisible(false);
		} else {
		    textField1.setVisible(true);
		}
	    }
	};
	
	add();
    }
    
    public void teacher(){
	condListener = new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		int i = newValue.intValue();
		context.conditionCBox = i;
		TextField textField1 = context.textField1;
		TextField textField2 = context.textField2;
		TextField textField3 = context.textField3;
		textField1.setText("");
		textField2.setText("");
		textField3.setText("");
		if (i == 3) {
		    textField1.setVisible(false);
		} else {
		    textField1.setVisible(true);
		}
	    }
	};
	
	add();
    }
    
    public void course(){
	termListener = new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		context.termCBox = newValue.intValue();
	    }
	};
	
	condListener = new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		context.conditionCBox = newValue.intValue();
		TextField textField1 = context.textField1;
		textField1.setVisible(true);
	    }
	};
	add();
    }
    
    public void remove() {
	term.getSelectionModel().selectedIndexProperty().removeListener(termListener);
	condition.getSelectionModel().selectedIndexProperty().removeListener(condListener);
    }
    
    private void add() {
	term.getSelectionModel().selectedIndexProperty().addListener(termListener);
	condition.getSelectionModel().selectedIndexProperty().addListener(condListener);
    }
    
}
