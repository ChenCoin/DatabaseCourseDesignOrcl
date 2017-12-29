/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import DB.DB;
import DB.DBresult;
import Table.GradeItem;
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
public class GradeDo implements Do {

    private Context context;

    public GradeDo(Context context) {
	this.context = context;
	init();
    }

    public void init() {
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

	Table table = context.table;
	table.hide();

	for (Label label : page) {
	    label.setFont(new Font(13));
	}
	gradePage.setFont(new Font(17));

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
		FXCollections.observableArrayList(context.gradeCondition)
	);
	condition.getSelectionModel().select(0);

	ChoiceboxSet choiceboxSet = context.choiceboxSet;
	choiceboxSet.grade();

	table.itemSelect(false);

    }

    @Override
    public void add() {
	HBox gradeaction = context.gradeaction;
	gradeaction.setVisible(true);

	Label gradeactiontitle = context.gradeactiontitle;
	gradeactiontitle.setText("添加数据");

	TextField gradeactionname = context.gradeactionname;
	gradeactionname.setText("");
	TextField gradeactioncourse = context.gradeactioncourse;
	gradeactioncourse.setText("");
	TextField gradeactionterm = context.gradeactionterm;
	gradeactionterm.setText("");
	TextField gradeactiongrade = context.gradeactiongrade;
	gradeactiongrade.setText("");

	Button gradeactioncancel = context.gradeactioncancel;
	Button gradeactionconfirm = context.gradeactionconfirm;

	gradeactioncancel.setOnAction((ActionEvent e) -> {
	    gradeaction.setVisible(false);
	});

	gradeactionconfirm.setOnAction((ActionEvent e) -> {
	    gradeaction.setVisible(false);
	    Confirm confirm = context.confirm;
	    confirm.show("添加数据中", "正在进行中，请等待");
	    String name = gradeactionname.getText();
	    String course = gradeactioncourse.getText();
	    String term = gradeactionterm.getText();
	    String grade = gradeactiongrade.getText();
	    new Thread(new Runnable() {
		@Override
		public void run() {
		    int gradeNum = str2num(grade);
		    String command = "insert into GradeInfo values(\n"
			    + "	(select StudentID from StudentInfo where name = '" + name + "'),\n"
			    + "	(select DISTINCT CourseID from CourseInfo where name = '" + course + "'),\n"
			    + "	'" + term + "',\n"
			    + "	'Selected',\n"
			    + gradeNum + ",\n"
			    + "	NULL\n"
			    + ");";
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
	TextField textField2 = context.textField2;
	TextField textField3 = context.textField3;
	Label tip = context.tip;
	int termIndex = term.getSelectionModel().getSelectedIndex();
	int condIndex = condition.getSelectionModel().getSelectedIndex();
	String str = "select\n"
		+ "	GradeInfo.StudentID \"studentID\",\n"
		+ "	StudentInfo.name \"studentName\",\n"
		+ "	GradeInfo.term \"time\",\n"
		+ "	CourseInfo.name \"course\",\n"
		+ "	TeacherInfo.name \"teacher\",\n"
		+ "	GradeInfo.grade \"grade\"\n"
		+ "from\n"
		+ "	GradeInfo,\n"
		+ "	CourseInfo,\n"
		+ "	StudentInfo,\n"
		+ "	TeacherInfo\n"
		+ "where \n"
		+ "	state = 'Selected' and \n"
		+ "	GradeInfo.term = CourseInfo.term and \n"
		+ "	GradeInfo.CourseID = CourseInfo.CourseID and\n"
		+ "	GradeInfo.StudentID = StudentInfo.StudentID and\n"
		+ "	CourseInfo.TeacherID = TeacherInfo.TeacherID";
	String end;
	if (termIndex == 0) {
	    end = ";";
	} else {
	    end = " and GradeInfo.term = '" + context.termItem[termIndex] + "';";
	}
	String condString = "";
	String cond1 = textField1.getText();
	String cond2 = textField2.getText();
	String cond3 = textField3.getText();
	int cond2num = str2num(cond2);
	int cond3num = str2num(cond3);
	switch (condIndex) {
	    case 0:
		str = str + end;
		break;
	    case 1:
		condString = " and StudentInfo.name like '%" + cond1 + "%'";
		str = str + condString + end;
		break;
	    case 2:
		if (cond2num < 0 || cond2num > cond3num) {
		    tip.setText("输入的数字不符合要求");
		    Timer mTimer = new Timer();
		    mTimer.schedule(new TimerTask() {
			public void run() {
			    Platform.runLater(() -> {
				tip.setText("");
			    });
			    mTimer.cancel();
			}
		    }, 1500);
		    return;
		}
		condString = " and GradeInfo.StudentID >= " + cond2num + " and GradeInfo.StudentID <= " + cond3num;
		str = str + condString + end;
		break;
	    case 3:
		condString = " and CourseInfo.name like '%" + cond1 + "%'";
		str = str + condString + end;
		break;
	    case 4:
		condString = " and TeacherInfo.name like '%" + cond1 + "%'";
		str = str + condString + end;
		break;
	    case 5:
		if (cond2num < 0 || cond2num > cond3num) {
		    tip.setText("输入的数字不符合要求");
		    Timer mTimer = new Timer();
		    mTimer.schedule(new TimerTask() {
			public void run() {
			    Platform.runLater(() -> {
				tip.setText("");
			    });
			    mTimer.cancel();
			}
		    }, 1500);
		    return;
		}
		condString = " and GradeInfo.grade >= " + cond2num + " and GradeInfo.grade <= " + cond3num;
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
		table.setCol(context.gradeColMap);
		table.itemSelect(false);

		ObservableList<GradeItem> data
			= FXCollections.observableArrayList();

		for (int i = 0; i < list.size(); i++) {
		    Map<String, Object> item = list.get(i);
		    String studentID = item.get("studentID").toString();
		    String studentName = item.get("studentName").toString();
		    String time = item.get("time").toString();
		    String course = item.get("course").toString();
		    String teacher = item.get("teacher").toString();
		    String grade = item.get("grade").toString();
		    data.add(new GradeItem(studentID, studentName, time, course, teacher, grade));
		}

		TableView tableView = context.tableView;
		tableView.setItems(data);
	    } else {
		System.err.println("err with search in DB");
	    }
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
	confirm.show("删除", "即将删除成绩记录，姓名：" + map.get("studentName") + ",科目：" + map.get("course") + "。",
		(ActionEvent e) -> {
		    confirm.hide();
		},
		(ActionEvent e) -> {
		    confirm.show("删除", "删除正在进行中，请等待", null, null);
		    new Thread(new Runnable() {
			@Override
			public void run() {
			    String str = "delete from GradeInfo "
				    + "where StudentID = " + map.get("studentID")
				    + " and term = '" + map.get("time") + "' "
				    + " and CourseID = (select DISTINCT CourseID from CourseInfo where name = '" + map.get("course") + "');";
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

	String name = map.get("studentName").toString();
	String course = map.get("course").toString();
	String term = map.get("time").toString();
	String grade = map.get("grade").toString();

	HBox gradeaction = context.gradeaction;
	gradeaction.setVisible(true);

	Label gradeactiontitle = context.gradeactiontitle;
	gradeactiontitle.setText("修改数据");

	TextField gradeactionname = context.gradeactionname;
	gradeactionname.setEditable(false);
	gradeactionname.setText(name);
	TextField gradeactioncourse = context.gradeactioncourse;
	gradeactioncourse.setEditable(false);
	gradeactioncourse.setText(course);
	TextField gradeactionterm = context.gradeactionterm;
	gradeactionterm.setEditable(false);
	gradeactionterm.setText(term);
	TextField gradeactiongrade = context.gradeactiongrade;
	gradeactiongrade.setText(grade);

	Button gradeactioncancel = context.gradeactioncancel;
	Button gradeactionconfirm = context.gradeactionconfirm;

	gradeactioncancel.setOnAction((ActionEvent e) -> {
	    gradeaction.setVisible(false);
	    gradeactionname.setEditable(true);
	    gradeactioncourse.setEditable(true);
	    gradeactionterm.setEditable(true);
	});

	gradeactionconfirm.setOnAction((ActionEvent e) -> {
	    gradeaction.setVisible(false);
	    gradeactionname.setEditable(true);
	    gradeactioncourse.setEditable(true);
	    gradeactionterm.setEditable(true);

	    Confirm confirm = context.confirm;
	    confirm.show("修改数据", "正在进行中，请等待");
	    String gradeNew = gradeactiongrade.getText();
	    new Thread(new Runnable() {
		@Override
		public void run() {
		    int gradeNum = str2num(grade);
		    String command = "update GradeInfo \n"
			    + "set grade = " + gradeNew + "\n"
			    + "where StudentID = (select StudentID from StudentInfo where name = '" + name + "')\n"
			    + "	and CourseID = (select DISTINCT CourseID from CourseInfo where name = '" + course + "')\n"
			    + "	and term = '" + term + "';";
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
