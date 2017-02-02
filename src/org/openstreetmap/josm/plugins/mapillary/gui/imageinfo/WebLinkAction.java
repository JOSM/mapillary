// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.mapillary.gui.imageinfo;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;

import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.plugins.mapillary.utils.MapillaryUtils;
import org.openstreetmap.josm.tools.I18n;
import org.openstreetmap.josm.tools.ImageProvider;

/**
 *
 */
public class WebLinkAction extends AbstractAction {
  private static final long serialVersionUID = 2397830510179013823L;

  private URL url;

  public WebLinkAction(final String name, final URL url) {
    super(name, ImageProvider.get("link"));
    setURL(url);
  }

  /**
   * @param url the url to set
   */
  public void setURL(URL url) {
    this.url = url;
    setEnabled(url != null);
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      MapillaryUtils.browse(url);
    } catch (IOException e1) {
      new Notification(I18n.tr("Could not open the URL {0} in a browser", url == null ? "‹null›" : url)).show();
    }
  }

}
