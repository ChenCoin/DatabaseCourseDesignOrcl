/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import DB.DB;
import DB.DBresult;
import Table.CourseItem;
import Table.Table;
import UI.ChoiceboxSet;
import UI.Confirm;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import tiger.Context;

/**
 *
 * @author coin
 */
public class CourseDo implements Do {

    /*
	TeacherID and TeacherName is in mix
    */
    
    private Context context;

    public CourseDo(Context context) {
	this.context = context;
	init();
    }

    private void init() {
	Label gradePage = context.gradePage;
	Label studentPage = context.studentPage;
	Label teacherPage = context.teacherPage;
	Label coursePage = context.coursePage;
	Label infoPage = context.infoPage;
	Label[] page = {gradePage, studentPage, teacherPage, coursePage, infoPage};

	HBox morePage = context.morePage;
	AnchorPane tablePage = context.tablePage;

	Label termTxt = context.termTxt;
	ChoiceBox term = context.term;
	ChoiceBox condition = context.condition;
	for (Label label : page) {
	    label.setFont(new Font(13));
	}
	coursePage.setFont(new Font(17));

	morePage.setVisible(false);
	tablePage.setVisible(true);

	termTxt.setVisible(true);
	term.setVisible(true);
	termTxt.setPrefWidth(45);
	term.setPrefWidth(72);

	HBox alert = context.alert;
	Label alertTitle = context.alertTitle;
	Label alertContent = context.alertContent;
	Button alertCancel = context.alertCancel;
	Button alertConfirm = context.alertConfirm;

	DBresult result = DB.query("select DISTINCT term \"term\" from CourseInfo;");
	String[] str;
	if (result.state) {
	    int length = result.list.size();
	    str = new String[length + 1];
	    str[0] = "全部  ";
	    for (int i = 0; i < length; i++) {
		str[i + 1] = result.list.get(i).get("term").toString();
	    }
	    context.termItem = str;
	} else {
	    alert.setVisible(true);
	    alertTitle.setText("严重错误");
	    alertContent.setText("执行SQL语句：" + result.SQL + "\n出现错误：" + result.msg);
	    alertCancel.setOnAction((ActionEvent e) -> {
		Platform.exit();
	    });
	    alertConfirm.setOnAction((ActionEvent e) -> {
		Platform.exit();
	    });
	    str = new String[0];
	}
	term.setItems(
		FXCollections.observableArrayList(str)
	);
	term.getSelectionModel().select(0);
	condition.setItems(
		FXCollections.observableArrayList(context.courseCondition)
	);
	condition.getSelectionModel().select(0);
	ChoiceboxSet choiceboxSet = context.choiceboxSet;
	choiceboxSet.course();

	Table table = context.table;
	table.hide();
	table.itemSelect(false);
    }

    @Override
    public void add() {

	HBox courseaction = context.courseaction;
	courseaction.setVisible(true);

	Label courseactiontitle = context.courseactiontitle;
	courseactiontitle.setText("添加数据");

	TextField courseactionid = context.courseactionid;
	courseactionid.setText("");
	TextField courseactionname = context.courseactionname;
	courseactionname.setText("");
	TextField courseactionteacher = context.courseactionteacher;
	courseactionteacher.setText("");
	TextField courseactionterm = context.courseactionterm;
	courseactionterm.setText("");
	TextField courseactiongrade = context.courseactiongrade;
	courseactiongrade.setText("");

	Button courseactioncancel = context.courseactioncancel;
	Button courseactionconfirm = context.courseactionconfirm;

	courseactioncancel.setOnAction((ActionEvent e) -> {
	    courseaction.setVisible(false);
	});

	courseactionconfirm.setOnAction((ActionEvent e) -> {
	    courseaction.setVisible(false);
	    Confirm confirm = context.confirm;
	    confirm.show("添加数据中", "正在进行中，请等待");

	    String id = courseactionid.getText();
	    String name = courseactionname.getText();
	    String teacher = courseactionteacher.getText();
	    String term = courseactionterm.getText();
	    int grade = str2num(courseactiongrade.getText());

	    new Thread(new Runnable() {
		@Override
		public void run() {
		    String command = "insert into CourseInfo values('" + id
			    + "',(select TeacherID from TeacherInfo where name = '" + teacher + "'),'"
			    + term + "','" + name + "'," + grade + ",NULL);";
		    DBresult result = DB.execute(command);

		    if (result.state) {
			Platform.runLater(() -> {
			    confirm.show("添加成功");
			});
			Timer mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
			    public void run() {
				Platform.runLater(() -> {
				    confirm.hide();
				    search();
				});
				mTimer.cancel();
			    }
			}, 1500);

		    } else {
			Platform.runLater(() -> {
			    confirm.show(
				    "错误",
				    "添加失败。\n执行：" + command + "\n反馈：" + result.msg,
				    (ActionEvent e) -> {
					confirm.hide();
				    },
				    (ActionEvent e) -> {
					confirm.hide();
				    }
			    );
			});
		    }
		}
	    }).start();
	});

    }

    @Override
    public void search() {
	ChoiceBox term = context.term;
	ChoiceBox condition = context.condition;
	TextField textField1 = context.textField1;
	Label tip = context.tip;
	int termIndex = term.getSelectionModel().getSelectedIndex();
	int condIndex = condition.getSelectionModel().getSelectedIndex();
	String str = "select\n"
		+ "	CourseInfo.CourseID \"CourseID\",\n"
		+ "	CourseInfo.name \"name\",\n"
		+ "	TeacherInfo.name \"TeacherID\",\n"
		+ "	CourseInfo.term \"term\",\n"
		+ "	CourseInfo.grade \"grade\"\n"
		+ "from\n"
		+ "	CourseInfo,TeacherInfo\n"
		+ "where\n"
		+ "	CourseInfo.TeacherID = TeacherInfo.TeacherID ";
	String end;
	if (termIndex == 0) {
	    end = ";";
	} else {
	    end = "and term = '" + context.termItem[termIndex] + "';";
	}
	String condString = "";
	String cond1 = textField1.getText();
	switch (condIndex) {
	    case 0:
		str = str + end;
		break;
	    case 1:
		condString = "and CourseID = '" + cond1 + "' ";
		str = str + condString + end;
		break;
	    case 2:
		condString = "and TeacherInfo.name like '%" + cond1 + "%' ";
		str = str + condString + end;
		break;
	    case 3:
		condString = "and name like '%" + cond1 + "%' ";
		str = str + condString + end;
		break;
	    default:
		str = null;
		break;
	}

	if (str != null) {
	    DBresult result = DB.query(str);
	    context.DBcommand = str;
	    if (result.state) {
		context.resultList = result.list;
		List<Map<String, Object>> list = result.list;

		Table table = context.table;
		table.show();
		table.setCol(context.courseColMap);
		table.itemSelect(false);

		ObservableList<CourseItem> data
			= FXCollections.observableArrayList();

		for (int i = 0; i < list.size(); i++) {
		    Map<String, Object> item = list.get(i);
		    String courseID = item.get("CourseID").toString();
		    String name = item.get("name").toString();
		    String teacherID = item.get("TeacherID").toString();
		    String term2 = item.get("term").toString();
		    String grade = item.get("grade").toString();
		    data.add(new CourseItem(courseID, name, teacherID, term2, grade));
		}

		TableView tableView = context.tableView;
		tableView.setItems(data);
	    } else {
		System.err.println("err with search in DB:" + str);
	    }
	} else {
	    System.out.println("err in choicebox item");
	}
    }

    @Override
    public void delete() {
	int row = context.tableRow;
	if (row < 0) {
	    System.out.println("err tablerow < 0");
	    return;
	}
	List<Map<String, Object>> list = context.resultList;
	Map<String, Object> map = list.get(row);

	Confirm confirm = context.confirm;
	confirm.show("删除", "即将删除课程记录，名称：" + map.get("name") + ",编号：" + map.get("CourseID") + ",学期：" + map.get("term") + "。",
		(ActionEvent e) -> {
		    confirm.hide();
		},
		(ActionEvent e) -> {
		    confirm.show("删除", "删除正在进行中，请等待", null, null);
		    new Thread(new Runnable() {
			@Override
			public void run() {
			    String preStr = "delete from GradeInfo "
				    + "where CourseID = '" + map.get("CourseID") + "'; ";
			    DB.execute(preStr);

			    String str = "delete from CourseInfo "
				    + "where CourseID = '" + map.get("CourseID") + "' "
				    + " and term = '" + map.get("term") + "' "
				    + " and TeacherID = ( select TeacherID from TeacherInfo where name = '" + map.get("TeacherID") + "');";
			    DBresult result = DB.execute(str);
			    context.DBcommand = str;
			    if (result.state) {
				Platform.runLater(() -> {
				    confirm.show("删除成功");
				    search();
				});
				Timer mTimer = new Timer();
				mTimer.schedule(new TimerTask() {
				    public void run() {
					Platform.runLater(() -> {
					    confirm.hide();
					});
					mTimer.cancel();
				    }
				}, 1500);
			    } else {
				Platform.runLater(() -> {
				    confirm.show("失败", "删除失败。\n执行：" + str + "\n反馈：" + result.msg,
					    (ActionEvent e) -> {
						confirm.hide();
					    }, (ActionEvent e) -> {
						confirm.hide();
					    });
				});

			    }
			}
		    }).start();
		}
	);
    }

    @Override
    public void motify() {
	int row = context.tableRow;
	if (row < 0) {
	    System.out.println("err tablerow < 0");
	    return;
	}
	List<Map<String, Object>> list = context.resultList;
	Map<String, Object> map = list.get(row);

	String id = map.get("CourseID").toString();
	String name = map.get("name").toString();
	String teacher = map.get("TeacherID").toString();
	String term = map.get("term").toString();
	int grade = str2num(map.get("grade").toString());
	
	HBox courseaction = context.courseaction;
	courseaction.setVisible(true);

	Label courseactiontitle = context.courseactiontitle;
	courseactiontitle.setText("修改数据");

	TextField courseactionid = context.courseactionid;
	courseactionid.setEditable(false);
	courseactionid.setText(id);
	TextField courseactionname = context.courseactionname;
	courseactionname.setText(name);
	TextField courseactionteacher = context.courseactionteacher;
	courseactionteacher.setEditable(false);
	courseactionteacher.setText(teacher);
	TextField courseactionterm = context.courseactionterm;
	courseactionterm.setEditable(false);
	courseactionterm.setText(term);
	TextField courseactiongrade = context.courseactiongrade;
	courseactiongrade.setText(""+grade);

	Button courseactioncancel = context.courseactioncancel;
	Button courseactionconfirm = context.courseactionconfirm;

	courseactioncancel.setOnAction((ActionEvent e) -> {
	    courseaction.setVisible(false);
	    courseactionid.setEditable(true);
	    courseactionteacher.setEditable(true);
	    courseactionterm.setEditable(true);
	});

	courseactionconfirm.setOnAction((ActionEvent e) -> {
	    courseaction.setVisible(false);
	    courseactionid.setEditable(true);
	    courseactionteacher.setEditable(true);
	    courseactionterm.setEditable(true);

	    Confirm confirm = context.confirm;
	    confirm.show("修改数据", "正在进行中，请等待");
	    String nameNew = courseactionname.getText();
	    int gradeNew = str2num(courseactiongrade.getText());
	    new Thread(new Runnable() {
		@Override
		public void run() {
		    String command = "update CourseInfo \n"
			    + "set name = '"+nameNew+"', grade = " + gradeNew + "\n"
			    + "where CourseID = '"+id+"'"
			    + "	and term = '"+term+"'"
			    + "	and TeacherID = (select DISTINCT TeacherID from TeacherInfo where name = '"+teacher+"');";
		    DBresult result = DB.execute(command);

		    if (result.state) {
			Platform.runLater(() -> {
			    confirm.show("修改成功");
			});
			Timer mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
			    public void run() {
				Platform.runLater(() -> {
				    confirm.hide();
				    search();
				});
				mTimer.cancel();
			    }
			}, 1500);

		    } else {
			Platform.runLater(() -> {
			    confirm.show(
				    "错误",
				    "修改失败。\n执行：" + command + "\n反馈：" + result.msg,
				    (ActionEvent e) -> {
					confirm.hide();
				    },
				    (ActionEvent e) -> {
					confirm.hide();
				    }
			    );
			});
		    }
		}
	    }).start();
	});
    }

    private int str2num(String str) {
	int ret = 0;
	try {
	    ret = Integer.parseInt(str);
	} catch (NumberFormatException e) {
	    ret = 0;
	}
	return ret;
    }

}
