package br.unicamp.ic.anubis.successmetrics.ui.settings;


import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.SuccessMetricsStrategyFactory;
import br.unicamp.ic.anubis.successmetrics.ui.settings.menu.SuccessMetricsMenuItem;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SUCCESS_METRICS_STRATEGY_KEY;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.CONSIDER_GAPS_KEY;

public class SuccessMetricsSettingsDialog extends JDialog implements ActionListener {
	
	private JPanel contentPanel;
	private JComboBox cbSuccessStrategy;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox chbConsiderGaps;
	
	private static final String CANCEL_ACTION = "Cancel";
	private static final String OK_ACTION = "OK";
	
	public SuccessMetricsSettingsDialog(JFrame frame) {
		super(frame, "", Dialog.ModalityType.DOCUMENT_MODAL);
		contentPanel = new JPanel();
		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 0, 30));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Success metrics",
				TitledBorder.LEFT, TitledBorder.TOP, null, null));
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(4, 1, 0, 10));

		cbSuccessStrategy = new JComboBox();
		panel.add(cbSuccessStrategy);
		
		chbConsiderGaps = new JCheckBox();
		chbConsiderGaps.setText("Consider gaps");
		panel.add(chbConsiderGaps);

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

	@Override
	public void actionPerformed(ActionEvent event) {
		if (OK_ACTION.equals(event.getActionCommand())){
			AnubisManager manager = AnubisManager.getInstance();
			ISettingsManager settings = manager.getSettingsManager();
			if (settings!=null){
				SuccessMetricsMenuItem selectedAlphabet = (SuccessMetricsMenuItem) cbSuccessStrategy.getSelectedItem();
				if (selectedAlphabet!=null){
					settings.setProperty(SUCCESS_METRICS_STRATEGY_KEY, selectedAlphabet.getId());
				}
				settings.setProperty(CONSIDER_GAPS_KEY, chbConsiderGaps.isSelected());
			}
			this.dispose();
		}
		if (CANCEL_ACTION.equals(event.getActionCommand())){
			this.dispose();
		}
		
	}

	public void showModal() {
		load();
		setVisible(true);
	}
	
	private void load() {
		SuccessMetricsStrategyFactory factory = SuccessMetricsStrategyFactory.getInstance();
		List<ISuccessMetricsStrategy> strategies = factory.getStrategies();
		if (strategies!=null){
			AnubisManager manager = AnubisManager.getInstance();
			ISettingsManager settings = manager.getSettingsManager();
			
			UUID selectedId = null;
			boolean considerGaps = false;
			SuccessMetricsMenuItem selectedItem = null;
			
			if (settings!=null){
				selectedId = (UUID)settings.getProperty(SUCCESS_METRICS_STRATEGY_KEY);
				Boolean gaps = (Boolean)settings.getProperty(CONSIDER_GAPS_KEY);
				if (gaps!=null){
					considerGaps = gaps;
				}
			}
			
			for (ISuccessMetricsStrategy entry:strategies){
				SuccessMetricsMenuItem item = new SuccessMetricsMenuItem(entry);
				if (item.getId().equals(selectedId)){
					selectedItem = item;
				}
				cbSuccessStrategy.addItem(item);
			}
			
			
			if (selectedItem!=null){
				cbSuccessStrategy.setSelectedItem(selectedItem);
			}
			
			chbConsiderGaps.setSelected(considerGaps);			
		}
	}

}
