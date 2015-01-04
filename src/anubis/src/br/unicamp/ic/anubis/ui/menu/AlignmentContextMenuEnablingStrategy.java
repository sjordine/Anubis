package br.unicamp.ic.anubis.ui.menu;

import br.unicamp.ic.anubis.plugin.IParameterizedContext;

public class AlignmentContextMenuEnablingStrategy implements IEnablingStrategy {

	private int alignmentId;
	private int column;
	private int row;
	
	private IParameterizedContext context;

	public AlignmentContextMenuEnablingStrategy(int alignmentId, int column,
			int row) {
		this.alignmentId = alignmentId;
		this.column = column;
		this.row = row;
	}

	@Override
	public boolean isEnable(MenuItemDefinition item) {
		boolean enabled = true;

		MenuAction action = item.getAction();
		if (action != null && action instanceof AlignmentContextMenuAction) {
			AlignmentContextMenuAction contextAction = (AlignmentContextMenuAction)action;
			enabled = contextAction.evaluate(alignmentId, column, row);
		}

		return enabled;
	}

	@Override
	public IParameterizedContext getContext() {
		return context;
	}
	
	@Override
	public void setContext(IParameterizedContext context) {
		this.context = context;
	}

}
