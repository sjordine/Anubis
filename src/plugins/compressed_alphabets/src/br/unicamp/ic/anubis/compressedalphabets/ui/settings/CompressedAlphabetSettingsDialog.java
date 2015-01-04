package br.unicamp.ic.anubis.compressedalphabets.ui.settings;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabet;
import br.unicamp.ic.anubis.compressedalphabets.alphabets.CompressedAlphabetFactory;
import br.unicamp.ic.anubis.compressedalphabets.ui.settings.menu.CompressedAlphabetItem;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_PROPERTY_ID;


public class CompressedAlphabetSettingsDialog extends JDialog implements ActionListener {

	private JPanel contentPanel;
	private JComboBox cbCompressedAlphabets;
	private JButton okButton;
	private JButton cancelButton;
	
	private static final String CANCEL_ACTION = "Cancel";
	private static final String OK_ACTION = "OK";

	public CompressedAlphabetSettingsDialog(JFrame frame) {
		super(frame, "", Dialog.ModalityType.DOCUMENT_MODAL);
		contentPanel = new JPanel();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 0, 30));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Compressed Alphabet",
				TitledBorder.LEFT, TitledBorder.TOP, null, null));
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 10));

		cbCompressedAlphabets = new JComboBox();
		panel.add(cbCompressedAlphabets);

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
	
	
	public void showModal(){
		load();
		setVisible(true);
	}


	private void load() {
		CompressedAlphabetFactory alphabetFactory = CompressedAlphabetFactory.getInstance();
		Set<Entry<UUID,CompressedAlphabet>> alphabets = alphabetFactory.getRegisteredAlphabets();
		if (alphabets!=null){
			AnubisManager manager = AnubisManager.getInstance();
			ISettingsManager settings = manager.getSettingsManager();
			
			UUID selectedId = null;
			CompressedAlphabetItem selectedItem = null;
			
			if (settings!=null){
				selectedId = (UUID)settings.getProperty(COMPRESSED_ALPHABET_PROPERTY_ID);
			}
			
			for (Entry<UUID,CompressedAlphabet> entry:alphabets){
				CompressedAlphabetItem item = new CompressedAlphabetItem(entry.getValue());
				if (item.getId().equals(selectedId)){
					selectedItem = item;
				}
				cbCompressedAlphabets.addItem(item);
			}
			
			
			if (selectedItem!=null){
				cbCompressedAlphabets.setSelectedItem(selectedItem);
			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		if (OK_ACTION.equals(event.getActionCommand())){
			AnubisManager manager = AnubisManager.getInstance();
			ISettingsManager settings = manager.getSettingsManager();
			if (settings!=null){
				CompressedAlphabetItem selectedAlphabet = (CompressedAlphabetItem) cbCompressedAlphabets.getSelectedItem();
				if (selectedAlphabet!=null){
					settings.setProperty(COMPRESSED_ALPHABET_PROPERTY_ID, selectedAlphabet.getId());
				}
			}
		}
		this.dispose();
	}

}
