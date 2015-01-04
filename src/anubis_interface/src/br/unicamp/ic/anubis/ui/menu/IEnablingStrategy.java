package br.unicamp.ic.anubis.ui.menu;

import br.unicamp.ic.anubis.plugin.IParameterizedContext;

interface IEnablingStrategy {

	public boolean isEnable(MenuItemDefinition item);

	public IParameterizedContext getContext();

	public void setContext(IParameterizedContext context);

}
