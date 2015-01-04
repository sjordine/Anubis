package br.unicamp.ic.anubisdefaultviewer.ui.statusbar.score;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.AlignmentScoreChangeEvent;
import br.unicamp.ic.anubis.event.AlignmentScoreMethodsChanged;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.score.IScoreMethod;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.settingsdialog.ISettingsDialog;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection.Orientation;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG_BUILDER;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_REPOSITORY_ID;

public class ScoreStatusBar implements IEventHandler, ActionListener {

	private JComboBox cbScoreMethod;
	private JLabel lblBenchmarkScore;
	private JLabel lblTargetScore;
	private JButton btnSettings;

	public ScoreStatusBar() {
		cbScoreMethod = new JComboBox();
		lblBenchmarkScore = new JLabel("benchmark");
		lblTargetScore = new JLabel("target");
	}

	public void create() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();

		if (uiManager != null) {
			// Section - selection method
			JPanel sectionPane = new JPanel();
			sectionPane.setLayout(new FlowLayout());
			sectionPane.add(cbScoreMethod);

			cbScoreMethod.addActionListener(this);

			StatusBarSection section = new StatusBarSection();
			section.setColumnWeight(1.0);
			section.setSectionPanel(sectionPane);
			section.setOrientation(Orientation.RIGHT);
			section.setPriority(300);
			uiManager.addStatusBarSection(section);

			sectionPane = new JPanel();
			sectionPane.setLayout(new BoxLayout(sectionPane, BoxLayout.Y_AXIS));
			sectionPane.add(lblBenchmarkScore);
			sectionPane.add(lblTargetScore);

			section = new StatusBarSection();
			section.setColumnWeight(0.1);
			section.setSectionPanel(sectionPane);
			section.setOrientation(Orientation.LEFT);
			section.setPriority(350);
			uiManager.addStatusBarSection(section);

			sectionPane = new JPanel();
			sectionPane.setLayout(new FlowLayout());

			btnSettings = new JButton();
			btnSettings.setPreferredSize(new Dimension(16, 16));
			Image btnIcon = Toolkit
					.getDefaultToolkit()
					.getImage(
							getClass()
									.getResource(
											"/br/unicamp/ic/anubisdefaultviewer/resources/settings_16x16.png"));
			if (btnIcon != null) {
				btnSettings.setIcon(new ImageIcon(btnIcon));
			}

			btnSettings.addActionListener(this);

			sectionPane.add(btnSettings);

			section = new StatusBarSection();
			section.setColumnWeight(1.0);
			section.setSectionPanel(sectionPane);
			section.setOrientation(Orientation.LEFT);
			section.setPriority(400);
			uiManager.addStatusBarSection(section);

			populateScoreMethods();
		}
	}

	@Override
	public void eventRaised(IEvent message) {
		// Check if selected method was changed
		if (message instanceof AlignmentScoreMethodsChanged) {
			populateScoreMethods();
		}
		// Check if alignment value changed
		if (message instanceof AlignmentScoreChangeEvent) {
			AlignmentScoreChangeEvent event = (AlignmentScoreChangeEvent) message;
			int alignmentId = event.getAlignmentId();
			switch (alignmentId) {
			case AlignmentUtil.BENCHMARK_ALIGNMENT:
				populateField(lblBenchmarkScore, alignmentId);
				break;
			case AlignmentUtil.TARGET_ALIGNMENT:
				populateField(lblTargetScore, alignmentId);
				break;
			case AlignmentUtil.BOTH_ALIGNMENTS:
				populateField(lblBenchmarkScore,
						AlignmentUtil.BENCHMARK_ALIGNMENT);
				populateField(lblTargetScore, AlignmentUtil.TARGET_ALIGNMENT);
				break;
			default:
				break;
			}
		}
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (ALIGNMENT_DATA_REPOSITORY_ID.equals(alignmentLoadedMessage
					.getSource())) {
				raiseScoreChange();
			}
		}

	}

	public void populateField(JLabel targetLabel, int alignmentId) {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		if (alignmentManager != null) {
			IScoreMethod scoreMethod = alignmentManager.getScoreMethod();
			if (scoreMethod != null) {
				Double value = scoreMethod.getScore(alignmentId);
				targetLabel.setText(value.toString());
			}
		}
	}

	private synchronized void populateScoreMethods() {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		
		cbScoreMethod.removeAllItems();

		if (alignmentManager != null) {
			List<IScoreMethod> scoreMethods = alignmentManager
					.getScoreMethods();
			IScoreMethod selectedItem = null;
			IScoreMethod selectedMethod = alignmentManager.getScoreMethod();
			if (scoreMethods != null && scoreMethods.size() > 0) {
				for (int i = 0; i < scoreMethods.size(); i++) {
					IScoreMethod scoreMethod = scoreMethods.get(i);
					ScoreMethodItem item = new ScoreMethodItem(
							scoreMethod.getName(), i);
					if (scoreMethod == selectedMethod) {
						selectedItem = scoreMethod;
					}
					cbScoreMethod.addItem(item);
				}
				if (selectedItem != null) {
					cbScoreMethod.setSelectedItem(selectedItem);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == cbScoreMethod) {
			// Change score method
			AnubisManager manager = AnubisManager.getInstance();
			IAlignmentManager alignmentManager = manager.getAlignmentManager();
			if (alignmentManager!=null){
				ScoreMethodItem item = (ScoreMethodItem)cbScoreMethod.getSelectedItem();
				if (item!=null){
					alignmentManager.setScoreMethod(item.getIndex());
				}
				
			}
			// Recalculate sum of pairs
			raiseScoreChange();
		}
		if (event.getSource() == btnSettings) {
			UUID settingsDialogId = null;
			ISettingsDialog settingsDialog = null;
			// Get selected score method
			AnubisManager manager = AnubisManager.getInstance();
			IAlignmentManager alignmentManager = manager.getAlignmentManager();
			IResourceManager resourceManager = manager.getResourceManager();
			IUIManager uiManager = manager.getUIManager();
			if (alignmentManager != null && resourceManager!=null) {
				IScoreMethod scoreMethod = alignmentManager.getScoreMethod();
				settingsDialog = (ISettingsDialog)resourceManager.getResource(SETTINGS_DIALOG_BUILDER);
				if (scoreMethod!=null){
					settingsDialogId = scoreMethod.getSettingsId();
				}
			}
			// Display Score Method Settings Windows
			if (settingsDialog!=null && settingsDialog!=null && uiManager!=null){
				settingsDialog.create(settingsDialogId);
			}
		}
	}

	private void raiseScoreChange() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {
			AlignmentScoreChangeEvent scoreChanged = new AlignmentScoreChangeEvent(
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(scoreChanged);
		}
	}

}
