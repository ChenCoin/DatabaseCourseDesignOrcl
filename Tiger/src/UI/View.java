/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.scene.Parent;
import tiger.Context;

/**
 *
 * @author Cano
 */
public class View {

    Parent parent;

    public View(Parent parent) {
	this.parent = parent;
    }

    public void init() {
	Context context = new Context(parent);

	Page page = new Page(context);

	Event event = new Event(context);

    }

}
