/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Table;

import java.util.Iterator;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tiger.Context;

/**
 *
 * @author coin
 */
public class Table {

    Context context;
    TableView table;

    public Table(Context context) {
	this.context = context;
	table = context.tableView;

	table.setEditable(false);

	table.getFocusModel().focusedCellProperty().addListener(
		new ChangeListener<TablePosition>() {
	    @Override
	    public void changed(ObservableValue<? extends TablePosition> observable,
		    TablePosition oldPos, TablePosition pos) {
		//System.out.println(oldPos+" "+pos.getRow()+" "+pos.getColumn());
		if (pos.getRow() >= 0 && pos.getColumn() >= 0) {
		    context.tableRow = pos.getRow();
		    itemSelect(true);
		}
	    }
	});

    }
    
    public void show(){
	table.setVisible(true);
    }
    
    public void hide(){
	table.setVisible(false);
    }

    public void setCol(Map<String, Object> mMap) {
	table.getColumns().clear();
	Iterator<Map.Entry<String, Object>> itera_Entry = mMap.entrySet().iterator();
	while (itera_Entry.hasNext()) {
	    Map.Entry<String, Object> mapEntry = itera_Entry.next();

	    TableColumn Col = new TableColumn(mapEntry.getKey());
	    Col.setMinWidth(100);
	    Col.setCellValueFactory(
		    new PropertyValueFactory<>(mapEntry.getValue().toString()));

	    table.getColumns().add(Col);
	}
    }

    public void itemSelect(boolean b) {
	Button motifyBtn = context.motifyBtn;
	Button deleteBtn = context.deleteBtn;

	motifyBtn.setDisable(!b);
	deleteBtn.setDisable(!b);

    }

}
