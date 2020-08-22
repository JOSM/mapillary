// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.mapillary.gui.imageviewer;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kishan
 */
public class ZoomPanMouseListener implements MouseListener, MouseWheelListener, MouseMotionListener {

  protected boolean isZooming = false;
  protected boolean isPanning = false;
  private final AbstractImageViewer imageViewer;
  protected boolean inWindow = false;

  public ZoomPanMouseListener(AbstractImageViewer imageViewer) {
    this.imageViewer = imageViewer;
  }

  public void reset() {
    isZooming = false;
    isPanning = false;
  }

  public void stopPanning() {
    isPanning = false;
    imageViewer.stopPanning();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (!SwingUtilities.isLeftMouseButton(e)) {
      return;
    }
    if (e.getClickCount() == 2) {
      imageViewer.zoomBestFitOrOne();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (!isPanning && SwingUtilities.isLeftMouseButton(e)) {
      isPanning = true;
      imageViewer.startPanning(e.getPoint());
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (isPanning && SwingUtilities.isLeftMouseButton(e)) {
      stopPanning();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    inWindow = true;
  }

  @Override
  public void mouseExited(MouseEvent e) {
    inWindow = false;
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (isPanning || !inWindow) {
      return;
    }
    if (e.getWheelRotation() < 0) {
      imageViewer.zoomIn(e.getX(), e.getY());
    } else {
      imageViewer.zoomOut(e.getX(), e.getY());
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      if (isPanning) {
        imageViewer.pan(e.getPoint());
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    //Do nothing.
  }

}
