/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Do.CourseDo;
import Do.GradeDo;
import Do.StudentDo;
import Do.TeacherDo;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import tiger.Context;

/**
 *
 * @author coin
 */
public class Page {

    private final Context context;

    public Page(Context context) {
	this.context = context;
	init();
    }

    private void init() {

	Label gradePage = context.gradePage;
	Label studentPage = context.studentPage;
	Label teacherPage = context.teacherPage;
	Label coursePage = context.coursePage;
	Label infoPage = context.infoPage;

	gradePage.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
	    context.todo = new GradeDo(context);
	});

	studentPage.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
	    context.todo = new StudentDo(context);
	});

	teacherPage.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
	    context.todo = new TeacherDo(context);
	});

	coursePage.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

	    context.todo = new CourseDo(context);
	});

	infoPage.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
	    Label[] page = {gradePage, studentPage, teacherPage, coursePage, infoPage};
	    HBox morePage = context.morePage;
	    AnchorPane tablePage = context.tablePage;
	    for (Label label : page) {
		label.setFont(new Font(13));
	    }
	    infoPage.setFont(new Font(17));
	    morePage.setVisible(true);
	    tablePage.setVisible(false);
	});

    }

}
