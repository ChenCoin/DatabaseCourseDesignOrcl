/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import DB.DB;
import DB.DBresult;
import Table.Table;
import Table.TeacherItem;
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
public class TeacherDo implements Do {

    private Context context;

    public TeacherDo(Context context) {
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
	teacherPage.setFont(new Font(17));

	morePage.setVisible(false);
	tablePage.setVisible(true);

	termTxt.setVisible(false);
	term.setVisible(false);
	termTxt.setPrefWidth(0);
	term.setPrefWidth(0);

	condition.setItems(
		FXCollections.observableArrayList(context.teacherCondition)
	);
	condition.getSelectionModel().select(0);
	ChoiceboxSet choiceboxSet = context.choiceboxSet;
	choiceboxSet.teacher();

	Table table = context.table;
	table.hide();
	table.itemSelect(false);
    }

    @Override
    public void add() {
	HBox teacheraction = context.teacheraction;
	teacheraction.setVisible(true);

	Label teacheractiontitle = context.teacheractiontitle;
	teacheractiontitle.setText("添加数据");

	TextField teacheractionname = context.teacheractionname;
	teacheractionname.setText("");
	TextField teacheractionid = context.teacheractionid;
	teacheractionid.setText("");
	TextField teacheractionage = context.teacheractionage;
	teacheractionage.setText("");
	TextField teacheractionsex = context.teacheractionsex;
	teacheractionsex.setText("");

	Button teacheractioncancel = context.teacheractioncancel;
	Button teacheractionconfirm = context.teacheractionconfirm;

	teacheractioncancel.setOnAction((ActionEvent e) -> {
	    teacheraction.setVisible(false);
	});

	teacheractionconfirm.setOnAction((ActionEvent e) -> {
	    teacheraction.setVisible(false);
	    Confirm confirm = context.confirm;
	    confirm.show("添加数据中", "正在进行中，请等待");
	    String name = teacheractionname.getText();
	    String id = teacheractionid.getText();
	    String age = teacheractionage.getText();
	    String sex = teacheractionsex.getText();

	    new Thread(new Runnable() {
		@Override
		public void run() {
		    int idNum = str2num(id);
		    int ageNum = str2num(age);

		    if (name.equals("") || id.equals("") || age.equals("")) {
			Platform.runLater(() -> {
			    confirm.show("输入错误", "不能有空的选项",
				    (ActionEvent e) -> {
					confirm.hide();
				    },
				    (ActionEvent e) -> {
					confirm.hide();
				    }
			    );
			});
			return;
		    }
		    String Sex = sex;
		    if (Sex.equals("男")) {
			Sex = "male";
		    } else if (Sex.equals("女")) {
			Sex = "female";
		    } else {
			Platform.runLater(() -> {
			    confirm.show("输入错误", "输入的性别只能为‘男’或‘女’",
				    (ActionEvent e) -> {
					confirm.hide();
				    },
				    (ActionEvent e) -> {
					confirm.hide();
				    }
			    );
			});
			return;
		    }

		    String command = "insert into TeacherInfo values('" + idNum + "','" + name + "'," + ageNum + ",'" + Sex + "',NULL);";

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
	String command = "select TeacherID \"TeacherID\",name \"name\",age \"age\",sex \"sex\" from TeacherInfo ";
	String end = " order by time;";

	TextField textField1 = context.textField1;
	TextField textField2 = context.textField2;
	TextField textField3 = context.textField3;

	Label tip = context.tip;

	String cond1 = textField1.getText();
	String cond2 = textField2.getText();
	String cond3 = textField3.getText();
	int cond2num = str2num(cond2);
	int cond3num = str2num(cond3);

	String str = "";
	switch (context.conditionCBox) {
	    case 0:
		command = command + end;
		break;
	    case 1:
		command = command + "where name like '%" + cond1 + "%'" + end;
		break;
	    case 2:
		if (cond1.equals("男")) {
		    cond1 = "male";
		} else if (cond1.equals("女")) {
		    cond1 = "female";
		} else {
		    tip.setText("输入的条件不符合要求，只能输入‘男’或‘女’，暂时没有其他选项");
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
		command = command + "where sex = '" + cond1 + "' " + end;
		break;
	    case 3:
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
		command = command + "where age>" + cond2num + " and age<" + cond3num + end;
		break;
	    default:
		str = null;
		break;
	}
	if (str == null) {
	    System.err.println("err with condition ChoiceBox");
	    return;
	}
	DBresult result = DB.query(command);
	if (result.state) {
	    context.resultList = result.list;
	    List<Map<String, Object>> list = result.list;

	    Table table = context.table;
	    table.show();
	    table.setCol(context.teacherColMap);
	    table.itemSelect(false);

	    ObservableList<TeacherItem> data
		    = FXCollections.observableArrayList();

	    for (int i = 0; i < list.size(); i++) {
		Map<String, Object> item = list.get(i);
		String teacherID = item.get("TeacherID").toString();
		String name = item.get("name").toString();
		String age = item.get("age").toString();
		String sex = item.get("sex").toString();
		sex = (sex.equals("male")) ? "男" : "女";
		data.add(new TeacherItem(teacherID, name, age, sex));
	    }

	    TableView tableView = context.tableView;
	    tableView.setItems(data);
	} else {
	    System.out.println("err with DB");
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
	confirm.show("删除", "即将删除教师记录，姓名：" + map.get("name") + ",学号：" + map.get("TeacherID") + "。",
		(ActionEvent e) -> {
		    confirm.hide();
		},
		(ActionEvent e) -> {
		    confirm.show("删除", "删除正在进行中，请等待", null, null);
		    new Thread(new Runnable() {
			@Override
			public void run() {
			    String preStr1 = "delete from GradeInfo "
				    + "where CourseID = (select CourseID from CourseInfo where TeacherID = '" + map.get("TeacherID") + "');";
			    DB.execute(preStr1);
			    String preStr2 = "delete from CourseInfo "
				    + "where TeacherID = '" + map.get("TeacherID") + "';";
			    DB.execute(preStr2);
			    String str = "delete from TeacherInfo "
				    + "where TeacherID = '" + map.get("TeacherID") + "';";
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

	String name = map.get("name").toString();
	String id = map.get("TeacherID").toString();
	String age = map.get("age").toString();
	String sex = map.get("sex").toString();

	HBox teacheraction = context.teacheraction;
	teacheraction.setVisible(true);

	Label teacheractiontitle = context.teacheractiontitle;
	teacheractiontitle.setText("修改数据");

	TextField teacheractionname = context.teacheractionname;
	teacheractionname.setText(name);
	TextField teacheractionid = context.teacheractionid;
	teacheractionid.setText(id);
	teacheractionid.setEditable(false);
	TextField teacheractionage = context.teacheractionage;
	teacheractionage.setText(age);
	TextField teacheractionsex = context.teacheractionsex;
	sex = (sex.equals("male")) ? "男" : "女";
	teacheractionsex.setText(sex);

	Button teacheractioncancel = context.teacheractioncancel;
	Button teacheractionconfirm = context.teacheractionconfirm;

	teacheractioncancel.setOnAction((ActionEvent e) -> {
	    teacheraction.setVisible(false);
	    teacheractionid.setEditable(true);
	});

	teacheractionconfirm.setOnAction((ActionEvent e) -> {
	    teacheraction.setVisible(false);
	    teacheractionid.setEditable(true);

	    Confirm confirm = context.confirm;
	    confirm.show("修改数据", "正在进行中，请等待");

	    String nameNew = teacheractionname.getText();
	    String ageNew = teacheractionage.getText();
	    String sexNew = teacheractionsex.getText();

	    int ageNew2 = str2num(ageNew);
	    if (ageNew2 == 0) {
		confirm.show("输入错误", "错误的年龄",
			(ActionEvent e1) -> {
			    confirm.hide();
			},
			(ActionEvent e1) -> {
			    confirm.hide();
			}
		);
		return;
	    }

	    if (sexNew.equals("男")) {
		sexNew = "male";
	    } else if (sexNew.equals("女")) {
		sexNew = "female";
	    } else {
		confirm.show("输入错误", "错误的性别",
			(ActionEvent e1) -> {
			    confirm.hide();
			},
			(ActionEvent e1) -> {
			    confirm.hide();
			}
		);
		return;
	    }
	    String sexNew2 = sexNew;

	    new Thread(new Runnable() {
		@Override
		public void run() {
		    int idNew = str2num(id);
		    String command = "update TeacherInfo\n"
			    + "set name = '" + nameNew + "',age = " + ageNew2
			    + ", sex = '" + sexNew2 + "'\n"
			    + "where TeacherID = " + idNew + ";";
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
