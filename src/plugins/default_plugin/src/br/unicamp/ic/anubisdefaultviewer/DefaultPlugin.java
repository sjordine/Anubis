package br.unicamp.ic.anubisdefaultviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.anubisdefaultviewer.dataconnector.AlignmentDataConnectorFeature;
import br.unicamp.ic.anubisdefaultviewer.datarepository.AlignmentDataRepository;
import br.unicamp.ic.anubisdefaultviewer.datarepository.MSFLoader;
import br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsScoreFeature;
import br.unicamp.ic.anubisdefaultviewer.score.settings.ScoreSettingsFeature;
import br.unicamp.ic.anubisdefaultviewer.score.sumofpairs.SumOfPairsScoreFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.alignmentdrawer.AlignmentDrawerFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.alignmentviewer.DefaultAlignmentViewerFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.columnViewer.DefaultColumnLayerFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.fontmanagement.FontManagementFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.menu.AlignmentContextualMenu;
import br.unicamp.ic.anubisdefaultviewer.ui.menu.BaseMenu;
import br.unicamp.ic.anubisdefaultviewer.ui.menu.VisibilityMenu;
import br.unicamp.ic.anubisdefaultviewer.ui.sequenceLock.SequenceLockFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.sequenceViewer.DefaultSequenceLayerFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.statusbar.messaging.MessagingStatusBarFeature;
import br.unicamp.ic.anubisdefaultviewer.ui.statusbar.score.ScoreStatusBarFeature;

public class DefaultPlugin implements IPlugin {
	
	public static final UUID ID = UUID.fromString("e2babe1f-69fa-466a-a470-5b2cc5f531a3");
	private static final String PLUGIN_NAME="Default Viewer";

	@Override
	public String getName() {
		return PLUGIN_NAME;
	}

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public List<IFeature> getFeatures() {
		List<IFeature> features = new ArrayList<IFeature>();
		
		features.add(new BaseMenu());
		features.add(new AlignmentContextualMenu());
		features.add(new VisibilityMenu());
				
		features.add(new AlignmentDataRepository());
		features.add(new MSFLoader());
		features.add(new AlignmentDrawerFeature());
		features.add(new DefaultAlignmentViewerFeature());
		features.add(new AlignmentDataConnectorFeature());
		features.add(new FontManagementFeature());
		features.add(new DefaultColumnLayerFeature());
		features.add(new DefaultSequenceLayerFeature());
		features.add(new SequenceLockFeature());
		
		features.add(new ScoreSettingsFeature());
		features.add(new ScoreStatusBarFeature());
		features.add(new SumOfPairsScoreFeature());
		features.add(new GapAffinitySumOfPairsScoreFeature());
		
		features.add(new MessagingStatusBarFeature());
		
		return features;
	}

}
