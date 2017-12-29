/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.DBresult;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import tiger.Context;

/**
 *
 * @author coin
 */
public class Event {

    Context context;
    DBresult ret;

    public Event(Context context) {
	this.context = context;
	init();
    }

    public void init() {
	Button searchBtn = context.searchBtn;
	Button addBtn = context.addBtn;
	Button motifyBtn = context.motifyBtn;
	Button deleteBtn = context.deleteBtn;

	searchBtn.setOnAction((ActionEvent e) -> {
	    Platform.runLater(new Runnable() {
		@Override
		public void run() {
		    context.todo.search();
		}
	    });
	});

	addBtn.setOnAction((ActionEvent e) -> {
	    Platform.runLater(new Runnable() {
		@Override
		public void run() {
		    context.todo.add();
		}
	    });
	});

	motifyBtn.setOnAction((ActionEvent e) -> {
	    Platform.runLater(new Runnable() {
		@Override
		public void run() {
		    context.todo.motify();
		}
	    });
	});

	deleteBtn.setOnAction((ActionEvent e) -> {
	    Platform.runLater(new Runnable() {
		@Override
		public void run() {
		    context.todo.delete();
		}
	    });
	});

    }

}
