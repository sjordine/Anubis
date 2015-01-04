package br.unicamp.ic.anubis.ui.settingsdialog;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

public class SettingsDialog extends JDialog implements ActionListener {

	private HashMap<Container, IFieldDefinition> fieldMapping;
	private Container contentPane;
	private JTabbedPane tabbedPane;

	private JButton okButton;
	private JButton cancelButton;

	private static final String CANCEL_ACTION = "Cancel";
	private static final String OK_ACTION = "OK";

	public SettingsDialog(JFrame frame) {
		super(frame, "Settings", Dialog.ModalityType.DOCUMENT_MODAL);
		setBounds(100, 100, 450, 350);

		fieldMapping = new HashMap<Container, IFieldDefinition>();
		tabbedPane = new JTabbedPane();
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setActionCommand(OK_ACTION);
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(CANCEL_ACTION);
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public void create(UUID dialogId) {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			SettingsUIDefinition settingsDialogDefinition = (SettingsUIDefinition) resourceManager
					.getResource(dialogId);

			List<SettingsTab> tabs = settingsDialogDefinition.getTabs();

			Collections.sort(tabs, new TabComparator());

			if (tabs != null) {
				for (SettingsTab tab : tabs) {
					buildTab(tab);
				}
			}

		}

		setVisible(true);
	}

	private void buildTab(SettingsTab tab) {
		// create tab
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(0, 1));

		List<IFieldDefinition> fieldsDefinition = tab.getFields();
		
		Collections.sort(fieldsDefinition, new FieldComparator());

		if (fieldsDefinition != null) {
			for (IFieldDefinition field : fieldsDefinition) {
				Container fieldRender = field.render();
				if (fieldRender != null) {
					panel.add(fieldRender);
					field.load(fieldRender);
					fieldMapping.put(fieldRender, field);
				}
			}
		}

		tabbedPane.addTab(tab.getName(), panel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (OK_ACTION.equals(event.getActionCommand())) {
			Set<Entry<Container, IFieldDefinition>> fields = fieldMapping
					.entrySet();
			if (fields != null) {
				for (Entry<Container, IFieldDefinition> fieldEntry : fields) {
					try {
						Container container = fieldEntry.getKey();
						IFieldDefinition fieldDefinition = fieldEntry
								.getValue();
						fieldDefinition.save(container);
					} catch (Exception ex) {
						// TODO:handle errors
					}
				}
			}
			this.dispose();
		}
		if (CANCEL_ACTION.equals(event.getActionCommand())) {
			this.dispose();
		}
	}

}
