package br.unicamp.ic.anubis.ui.viewer;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentClickedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.model.AlignmentScrollBarModel;
import br.unicamp.ic.anubis.model.AlignmentScrollBarSynchronizer;
import br.unicamp.ic.anubis.model.AlignmentScrollBarModel.DIRECTION;
import br.unicamp.ic.anubis.ui.IUIManager;

import static br.unicamp.ic.anubis.mechanism.resources.ResourceConstants.ALIGNMENT_HORIZONTAL_SCROLL_SYNCHRONIZER;
import static br.unicamp.ic.anubis.mechanism.resources.ResourceConstants.ALIGNMENT_VERTICAL_SCROLL_SYNCHRONIZER;

public class AlignmentViewer extends JPanel implements IEventHandler,
		AdjustmentListener, IAlignmentContainer, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AlignmentViewerPanel viewer;
	private JScrollBar verticalBar;
	private JScrollBar horizontalBar;
	
	AlignmentScrollBarModel verticalBarModel;
	AlignmentScrollBarModel horizontalBarModel;

	private int alignmentId = 0;

	public AlignmentViewer() {
		verticalBar = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
		horizontalBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 0);
		viewer = new AlignmentViewerPanel(this);

		AnubisManager anubisManager = AnubisManager.getInstance();
		IEventManager eventManager = anubisManager.getEventManager();
		IUIManager uiManager = anubisManager.getUIManager();

		// verticalBar.setEnabled(false);
		// horizontalBar.setEnabled(false);

		setLayout(new BorderLayout());

		add(verticalBar, BorderLayout.EAST);
		add(horizontalBar, BorderLayout.SOUTH);
		add(viewer, BorderLayout.CENTER);

		verticalBarModel = new AlignmentScrollBarModel();
		verticalBarModel.setDirection(DIRECTION.SEQUENCES);
		setSyncronizer(ALIGNMENT_VERTICAL_SCROLL_SYNCHRONIZER, verticalBarModel);
		verticalBar.setModel(verticalBarModel);

		horizontalBarModel = new AlignmentScrollBarModel();
		horizontalBarModel.setDirection(DIRECTION.COLUMNS);
		setSyncronizer(ALIGNMENT_HORIZONTAL_SCROLL_SYNCHRONIZER, horizontalBarModel);
		horizontalBar.setModel(horizontalBarModel);

		verticalBar.addAdjustmentListener(this);
		horizontalBar.addAdjustmentListener(this);

		// this.setMinimumSize(new Dimension(300, 100));

		if (eventManager != null && uiManager != null) {
			eventManager.register(RedrawAlignmentEvent.class, this);
		}

		this.addMouseListener(this);

	}

	private void setSyncronizer(UUID synchronizerId,
			AlignmentScrollBarModel model) {
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();

		if (resourceManager != null) {
			AlignmentScrollBarSynchronizer synchronizer = (AlignmentScrollBarSynchronizer) resourceManager
					.getResource(synchronizerId);
			if (synchronizer != null) {
				model.setSynchronizer(synchronizer);
			}
		}
	}

	@Override
	public void setHorizontalScrollMax(int value) {
		horizontalBar.setMaximum(value);
	}

	@Override
	public void setVerticalScrollMax(int value) {
		verticalBar.setMaximum(value);
	}

	@Override
	public int getDrawingAreaWidth() {
		return viewer.getWidth();
	}

	@Override
	public int getDrawingAreaHeight() {
		return viewer.getHeight();
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof RedrawAlignmentEvent) {
			RedrawAlignmentEvent redrawMessage = (RedrawAlignmentEvent) message;
			int alignmentIndex = redrawMessage.getAlignmentId();
			if (alignmentIndex < 0 || alignmentIndex == this.alignmentId) {
				repaint();
			}
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent event) {
		
		raiseRedrawEvent();

	}

	private void raiseRedrawEvent() {

		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {

			RedrawAlignmentEvent redrawEvent;

			redrawEvent = new RedrawAlignmentEvent(alignmentId);

			eventManager.raise(redrawEvent);
		}
	}

	@Override
	public void setAlignmentId(int alignmentId) {
		this.alignmentId = alignmentId;
		verticalBarModel.setAlignmentId(alignmentId);
		horizontalBarModel.setAlignmentId(alignmentId);		
	}

	@Override
	public int getAlignmentId() {
		return alignmentId;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {
			AlignmentClickedEvent event = new AlignmentClickedEvent();
			
			event.setButton(e);			
			event.setPoint(e.getPoint());
			event.setAlignmentId(alignmentId);
			event.setContainer(this);
			
			eventManager.raise(event);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
