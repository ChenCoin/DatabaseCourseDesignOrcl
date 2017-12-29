/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tiger.Context;

/**
 *
 * @author coin
 */
public class Confirm {

    private final HBox alert;
    private final Label alertTitle;
    private final Label alertContent;
    private final Button alertCancel;
    private final Button alertConfirm;

    public Confirm(Context context) {
	alertTitle = context.alertTitle;
	alertContent = context.alertContent;
	alertCancel = context.alertCancel;
	alertConfirm = context.alertConfirm;
	alert = context.alert;
    }

    public void show(String title, String content, EventHandler<ActionEvent> cancel, EventHandler<ActionEvent> confirm) {
	alert.setVisible(true);

	alertTitle.setText(title);
	alertContent.setText(content);

	alertCancel.setDisable(false);
	alertConfirm.setDisable(false);
	alertCancel.setOnAction(cancel);
	alertConfirm.setOnAction(confirm);
    }

    public void show(String content) {
	alert.setVisible(true);
	alertContent.setText(content);
	alertCancel.setDisable(true);
	alertConfirm.setDisable(true);
    }

    public void show(String title, String content) {
	alert.setVisible(true);
	alertTitle.setText(title);
	alertContent.setText(content);
	alertCancel.setDisable(true);
	alertConfirm.setDisable(true);
    }

    public void hide() {
	alert.setVisible(false);
    }

}
