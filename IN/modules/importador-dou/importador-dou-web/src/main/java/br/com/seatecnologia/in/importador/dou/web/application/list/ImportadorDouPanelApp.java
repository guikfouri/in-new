package br.com.seatecnologia.in.importador.dou.web.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;

@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=300",
		"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_CONFIGURATION
	},
	service = PanelApp.class
)
public class ImportadorDouPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ImportadorDouPortletKeys.ImportadorDou;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}
}
