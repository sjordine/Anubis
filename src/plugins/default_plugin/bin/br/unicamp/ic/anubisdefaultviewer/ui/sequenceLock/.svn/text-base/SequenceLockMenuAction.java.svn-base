package br.unicamp.ic.anubisdefaultviewer.ui.sequenceLock;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

import static  br.unicamp.ic.anubis.mechanism.settings.CommonSettingKeys.LOCK_ALIGNMENTS_KEY;

public class SequenceLockMenuAction extends MenuAction implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {

		if (message != null && message instanceof PropertyChangedEvent) {
			PropertyChangedEvent event = (PropertyChangedEvent) message;
			String property = event.getPropertyName();
			if (LOCK_ALIGNMENTS_KEY.equals(property)){
				AnubisManager manager = AnubisManager.getInstance();
				ISettingsManager settingsManager = manager.getSettingsManager();
				JMenuItem associatedMenu = getAssociatedMenu();
				if (settingsManager!=null &&  associatedMenu!=null && associatedMenu instanceof JCheckBoxMenuItem){
					Boolean value = (Boolean)settingsManager.getProperty(LOCK_ALIGNMENTS_KEY);
					boolean enabled = value!=null && value;
					((JCheckBoxMenuItem)associatedMenu).setSelected(enabled);
				}
			}
		}
	}

	@Override
	public void execute(ActionEvent event) {
		if (event != null) {
			if (event.getSource() instanceof JCheckBoxMenuItem) {
				AnubisManager manager = AnubisManager.getInstance();
				ISettingsManager settingsManager = manager.getSettingsManager();

				if (settingsManager != null) {
					JCheckBoxMenuItem menu = (JCheckBoxMenuItem) event
							.getSource();
					boolean value = menu.isSelected();
					
					settingsManager.setProperty(LOCK_ALIGNMENTS_KEY, value);
				}
			}
		}
	}

}
