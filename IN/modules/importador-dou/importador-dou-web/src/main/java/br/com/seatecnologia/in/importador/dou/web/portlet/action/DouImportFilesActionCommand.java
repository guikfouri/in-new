package br.com.seatecnologia.in.importador.dou.web.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.file.service.ImportadorDouFileService;
import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou,
		"mvc.command.name=/importador/xml/files"
	},
	service = MVCActionCommand.class
)

public class DouImportFilesActionCommand extends BaseMVCActionCommand {
	private static Log log = LogFactoryUtil.getLog(DouImportFilesActionCommand.class);

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		int itensImportados = 0;

		log.debug("entering");

		PortletPreferences prefs = actionRequest.getPreferences();
		

		String basePathXMLs = prefs.getValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, "");
		Long idGrupo = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
//		Long idUsuario = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_USUARIO, "0"));
		Long idSecaoVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_SECAO_VOCAB, "0"));
		Long idArranjoSecaoVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB, "0"));
		Long idTipoMateriaVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB, "0"));

		Long threadSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_THREAD_SIZE, "1"));

		Long timeout = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_IMPORT_XML_FILE_TIMEOUT, "10"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
		if (idGrupo != serviceContext.getScopeGroupId()) {
			// Set ScopeGroupId to Global
			serviceContext.setScopeGroupId(GroupLocalServiceUtil.fetchGroup(serviceContext.getCompanyId(), String.valueOf(serviceContext.getCompanyId())).getGroupId());
		}

		String alternateXmlPath = ParamUtil.get(actionRequest, "alternateXmlPath", "");
		if (alternateXmlPath.length() > 0) {
			basePathXMLs = alternateXmlPath;
		}
		log.debug("basePathXMLs: " + basePathXMLs);

		itensImportados = _importadorDouFileService.importarXMLs(idGrupo, idSecaoVocab, idArranjoSecaoVocab, idTipoMateriaVocab, basePathXMLs, threadSize, timeout, serviceContext);
		log.info(itensImportados + " itens importados!");
	}

	@Reference
	private ImportadorDouFileService _importadorDouFileService;
}
