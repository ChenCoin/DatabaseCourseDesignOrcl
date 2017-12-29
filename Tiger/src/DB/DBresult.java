/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.util.List;
import java.util.Map;

/**
 *
 * @author coin
 */
public class DBresult {

    public boolean state = false;
    public String SQL = "";
    public String msg = "";
    public List<Map<String, Object>> list;

    public DBresult(boolean state, String SQL, String msg, List<Map<String, Object>> list) {
	this.state = state;
	this.SQL = SQL;
	this.msg = msg;
	this.list = list;
    }

}
