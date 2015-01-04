package br.unicamp.ic.anubisdefaultviewer.controller.menu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.ui.menu.MenuAction;
import br.unicamp.ic.anubisdefaultviewer.ui.fontmanagement.FontManager;
import br.unicamp.ic.anubisdefaultviewer.ui.fontmanagement.FontManager.Direction;

public class IncreaseFontSizeAction extends MenuAction {

	@Override
	public void execute(ActionEvent event) {
		FontManager.changeFontSize(FontManager.Direction.INCREASE);
	}

}
