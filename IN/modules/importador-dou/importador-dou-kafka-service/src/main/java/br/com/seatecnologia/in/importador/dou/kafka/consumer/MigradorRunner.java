package br.com.seatecnologia.in.importador.dou.kafka.consumer;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import br.com.seatecnologia.migracao.model.ItemMigracao;
import br.com.seatecnologia.migracao.service.ItemMigracaoLocalService;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

public class MigradorRunner implements Runnable {
	private static Log log = LogFactoryUtil.getLog(MigradorRunner.class);
	
	private ServiceContext serviceContext;
	private DDMStructure estrutura;
	private DDMTemplate template;
	private Long ownerId;
	private Long groupId;
	private Integer assetDisplayPageType;
	private Long assetDisplayPageId;
	private String layoutUuid;
	private String basePath;

	private ItemMigracao itemMigracao;

	private ServiceTracker<UserLocalService, UserLocalService> _userLocalServiceTracker;
	private ServiceTracker<ItemMigracaoLocalService, ItemMigracaoLocalService> _itemMigracaoLocalServiceTracker;
	private ServiceTracker<ItemPublicacaoLocalService, ItemPublicacaoLocalService> _itemPublicacaoLocalServiceTracker;

	private ItemMigracaoLocalService _itemMigracaoLocalService;
	private ItemPublicacaoLocalService _itemPublicacaoLocalService;
	private UserLocalService _userLocalService;

	public MigradorRunner(
			ItemMigracao itemMigracao,
			long ownerId, 
			long groupId, 
			Integer assetDisplayPageType,
			Long assetDisplayPageId,
			String layoutUuid,
			DDMStructure estrutura, 
			DDMTemplate template, 
			String basePath,
			ServiceContext serviceContext
			) {
		this.serviceContext = serviceContext;
		this.estrutura = estrutura;
		this.template = template;
		this.ownerId = ownerId;
		this.groupId = groupId;
		this.assetDisplayPageType = assetDisplayPageType;
		this.assetDisplayPageId = assetDisplayPageId;
		this.layoutUuid = layoutUuid;
		this.basePath = basePath;

		this.itemMigracao = itemMigracao;
	}

	@Override
	public void run() {
		loadLocalServices();

		if (!LocaleUtil.getDefault().equals(serviceContext.getLocale())) {
			log.info("Changing default locale to " + serviceContext.getLocale());
			LocaleUtil.setDefault(serviceContext.getLocale().getLanguage(), 
					serviceContext.getLocale().getCountry(), 
					serviceContext.getLocale().getVariant());
		}

		try {
			PrincipalThreadLocal.setName(serviceContext.getUserId());
			PermissionChecker permissionChecker;
			permissionChecker = PermissionCheckerFactoryUtil.create(_userLocalService.getUser(serviceContext.getUserId()));
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		} catch (Exception e1) {
			e1.printStackTrace();
			unloadLocalServices();
		}

		JournalArticle journalArticle = null;
		try {
			journalArticle = _itemMigracaoLocalService.migrarNoticia(
			itemMigracao,
			serviceContext.getCompanyId(),
			serviceContext.getUserId(),
			serviceContext.getScopeGroupId(),
			serviceContext.getLanguageId(),
			estrutura,
			template,
			ownerId,
			groupId,
			assetDisplayPageType,
			assetDisplayPageId,
			layoutUuid,
			basePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[ERROR] Migration failed for import " + itemMigracao.getIdentificador_atualizacao());
			unloadLocalServices();
		}
		if (journalArticle != null) {
			log.info("Publication successfully done for " + itemMigracao.getIdentificador_atualizacao());
			try {
				if (_itemPublicacaoLocalService.publishArticleHighlightsOnSocialNetworks(itemMigracao, journalArticle, serviceContext)) {
					log.info("Highlights published for " + itemMigracao.getIdentificador_atualizacao());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("[ERROR] Highlighs publication failed for import " + itemMigracao.getIdentificador_atualizacao());
				unloadLocalServices();
			}
		}

		unloadLocalServices();
	}
	
	private void loadLocalServices() {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();

		try {
			_userLocalServiceTracker = new ServiceTracker<>(bundleContext, UserLocalService.class, null);
			_userLocalServiceTracker.open();
			_userLocalService = _userLocalServiceTracker.waitForService(500);

			_itemMigracaoLocalServiceTracker = new ServiceTracker<>(bundleContext, ItemMigracaoLocalService.class, null);
			_itemMigracaoLocalServiceTracker.open();
			_itemMigracaoLocalService = _itemMigracaoLocalServiceTracker.waitForService(500);

			_itemPublicacaoLocalServiceTracker = new ServiceTracker<>(bundleContext, ItemPublicacaoLocalService.class, null);
			_itemPublicacaoLocalServiceTracker.open();
			_itemPublicacaoLocalService = _itemPublicacaoLocalServiceTracker.waitForService(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (_itemMigracaoLocalService != null) {
			log.debug(_itemMigracaoLocalService.getOSGiServiceIdentifier());
		} else {
			log.error("NO ItemMigracaoLocalService loaded!");
		}
		if (_itemPublicacaoLocalService != null) {
			log.debug(_itemPublicacaoLocalService.getOSGiServiceIdentifier());
		} else {
			log.error("NO ItemPublicacaoLocalService loaded!");
		}
	}
	
	private void unloadLocalServices() {
		_userLocalService = null;
		_userLocalServiceTracker.close();
		_userLocalServiceTracker = null;

		_itemMigracaoLocalService = null;
		_itemMigracaoLocalServiceTracker.close();
		_itemMigracaoLocalServiceTracker = null;

		_itemPublicacaoLocalService = null;
		_itemPublicacaoLocalServiceTracker.close();
		_itemPublicacaoLocalServiceTracker = null;
	}
}
