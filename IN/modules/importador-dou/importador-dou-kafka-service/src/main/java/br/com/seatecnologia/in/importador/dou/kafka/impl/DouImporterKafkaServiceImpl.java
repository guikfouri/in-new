package br.com.seatecnologia.in.importador.dou.kafka.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.article.Article;
import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;
import br.com.seatecnologia.in.importador.dou.exception.DOXMLParserException;
import br.com.seatecnologia.in.importador.dou.json.factory.ArticleJSONParserFactory;
import br.com.seatecnologia.in.importador.dou.kafka.DouImporterKafkaService;
import br.com.seatecnologia.in.importador.dou.kafka.SourceFormat;
import br.com.seatecnologia.in.importador.dou.kafka.consumer.MigradorRunner;
import br.com.seatecnologia.in.importador.dou.model.ItemImportacao;
import br.com.seatecnologia.in.importador.dou.service.ItemImportacaoLocalService;
import br.com.seatecnologia.in.importador.dou.xml.parser.jsoup.ArticleXMLParser;
import br.com.seatecnologia.migracao.model.ItemMigracao;
import br.com.seatecnologia.migracao.service.ItemMigracaoLocalService;

/**
 * @author SEA Tecnologia
 */
@Component(
	immediate = true,
	service = DouImporterKafkaService.class
)
public class DouImporterKafkaServiceImpl implements DouImporterKafkaService, Runnable {

	@Override
	public Boolean isKafkaConsumerRunning() {
		Boolean kafkaConsumerIsRunning = false;

		if (_xmlConsumerThread != null && _xmlConsumerThread.isAlive()) {
			kafkaConsumerIsRunning = true;
		}

		return kafkaConsumerIsRunning;
	}

	@Override
	public void startConsumer(String topic, String propertiesFile,
			SourceFormat format,
			Long groupId,
			Long idSecaoVocab,
			Long idArranjoSecaoVocab,
			Long idTipoMateriaVocab,
			Long structureId,
			Long templateId,
			Long ownerId,
			Integer assetDisplayPageType,
			Long assetDisplayPageId,
			String layoutUuid,
			Long threadPoolSize,
			String imagesPath) throws SystemException {
		if (_xmlConsumerThread == null || !_xmlConsumerThread.isAlive()) {
			log.debug("Starting Kafka consumer...");

			try {
				initImportadorXmlKafkaConsumer(
						topic,
						propertiesFile,
						format,
						groupId,
						idSecaoVocab,
						idArranjoSecaoVocab,
						idTipoMateriaVocab,
						structureId,
						templateId,
						ownerId,
						assetDisplayPageType,
						assetDisplayPageId,
						layoutUuid,
						threadPoolSize,
						imagesPath);
				_xmlConsumerThread = new Thread(this);
				_xmlConsumerThread.setDaemon(true);
				_xmlConsumerThread.setName("ImportadorDouXMLKafkaConsumer");
				_xmlConsumerThread.start();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Impossible to start Kafka xml consumer: " + e.getMessage(), e);
				throw new SystemException("Impossible to start Kafka xml consumer");
			}
		}
	}

	@Override
	public void startConsumer(String topic, String brokers, String consumerGroup,
			Map<String, String> properties,
			SourceFormat format,
			Long groupId,
			Long idSecaoVocab,
			Long idArranjoSecaoVocab,
			Long idTipoMateriaVocab,
			Long structureId,
			Long templateId,
			Long ownerId,
			Integer assetDisplayPageType,
			Long assetDisplayPageId,
			String layoutUuid,
			Long threadPoolSize,
			String imagesPath) throws SystemException {
		if (_xmlConsumerThread == null || !_xmlConsumerThread.isAlive()) {
			log.debug("Starting Kafka consumer...");

			try {
				initImportadorXmlKafkaConsumer(
						topic,
						brokers,
						consumerGroup,
						properties,
						format,
						groupId,
						idSecaoVocab,
						idArranjoSecaoVocab,
						idTipoMateriaVocab,
						structureId,
						templateId,
						ownerId,
						assetDisplayPageType,
						assetDisplayPageId,
						layoutUuid,
						threadPoolSize,
						imagesPath);
				_xmlConsumerThread = new Thread(this);
				_xmlConsumerThread.setDaemon(true);
				_xmlConsumerThread.setName("ImportadorDouXMLKafkaConsumer");
				_xmlConsumerThread.start();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Impossible to start Kafka xml consumer: " + e.getMessage(), e);
				throw new SystemException("Impossible to start Kafka xml consumer");
			}
		}
	}

	@Override
	public void stopConsumer() {
		if (_xmlConsumerThread != null && _xmlConsumerThread.isAlive()) {
			log.info("Stoping Kafka consumer...");
			this.shutdown();
			try {
				_xmlConsumerThread.join();
				_xmlConsumerThread = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("Kafka consumer successfully stoped!");
		}
	}

	@Override
	public void run() {
		DDMStructure estrutura = null;
		DDMTemplate template = null;

		if (!LocaleUtil.getDefault().equals(serviceContext.getLocale())) {
			log.info("Changing default locale to " + serviceContext.getLocale());
			LocaleUtil.setDefault(serviceContext.getLocale().getLanguage(), 
					serviceContext.getLocale().getCountry(), 
					serviceContext.getLocale().getVariant());
		}

		try {
			estrutura = _ddmStructureLocalService.getDDMStructure(idEstrutura);
			template = _ddmTemplateLocalService.getDDMTemplate(idTemplate);
		} catch (PortalException e1) {
			log.error(e1);
		}

		MediaLibraryConnector mediaLibraryConnector = new MediaLibraryConnector() {

			@Override
			public String saveMedia(String filename, String uri) {
				if (uri.startsWith("data:")) {
					String finalUri = convertData(filename, uri);
					if (finalUri.length() > 0) {
						return finalUri;
					}
				}
				return uri;
			}

			private String convertData(String filename, String base64) {

				String[] strings = base64.split(",");
				String extension;
				switch (strings[0]) {//check media's extension
				case "data:image/jpeg;base64":
					extension = "jpg";
					break;
				case "data:image/jpg;base64":
					extension = "jpg";
					break;
				case "data:image/png;base64":
					extension = "png";
					break;
				case "data:image/gif;base64":
					extension = "gif";
					break;
//				case "data:video/webm;base64":
//					extension = "webm";
//					break;
//				case "data:video/mp4;base64":
//					extension = "mp4";
//					break;
				default://should write cases for more media types
					log.warn("Unsupported media type: " + strings[0]);
					extension = null;
					break;
				}

				if (extension != null) {
					//convert base64 string to binary data
					log.debug("Using liferay base64 converter");
					byte[] data = Base64.decode(strings[1]);
					String outputDirPath = imagesPath;
					String fullFilePath = outputDirPath + (outputDirPath.endsWith(File.separator) ? "" : File.separator) + filename;
					if (!filename.endsWith("." + extension))
					{
						fullFilePath = fullFilePath + "." + extension;
					}
					File file = new File(fullFilePath);
					try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
						outputStream.write(data);
					} catch (IOException e) {
						log.error(e);
					}
					return file.getAbsolutePath();
				}
				return "";
			}
		};

		try {
			while (!closed.get()) {
//				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
				ConsumerRecords<String, String> records = consumer.poll(10000);
				Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
				for (TopicPartition partition : records.partitions()) {
					List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
					// Handle new records
					for (ConsumerRecord<String, String> record : partitionRecords) {
						log.debug(String.format("Starting import for %s [%d] offset=%d, key=%s", record.topic(), record.partition(), record.offset(), record.key()));

						String identificadorAtualizacao = record.key() != null ? record.key() : "/kafka/" + record.topic() + "/" + record.partition() + "/" + record.offset();
						String content = record.value();

						sendMessage(identificadorAtualizacao, content);

						ItemImportacao itemImportacao = null;
						if (this.isLegacyXMLFormat) {
							log.trace("Trying to decode the source article as legacy XML");
							itemImportacao = _itemImportacaoLocalService.importarXML(
									groupId,
									idSecaoVocab,
									idArranjoSecaoVocab,
									idTipoMateriaVocab,
									imagesPath,
									identificadorAtualizacao,
									content,
									null,
									serviceContext);
						} else {
							Article douArticle = null;
							String errorMessage = "n/a";
							switch(this.format) {
							case JSON:
								log.trace("Trying to decode the source article as JSON");
								try {
									douArticle = ArticleJSONParserFactory.getArticleParser(content, mediaLibraryConnector);
								} catch (Exception e) {
									errorMessage = e.getMessage();
									log.error(e);
								}
								break;
							case XML:
								log.trace("Trying to decode the source article as XML");
								try {
									douArticle = new ArticleXMLParser(content, mediaLibraryConnector);
								} catch (DOXMLParserException e1) {
									errorMessage = e1.getMessage();
									log.error(e1);
								}
								break;
							case LEGACY_XML:
							default:
								errorMessage = "invalid format (" + this.format.name() + ")";
								break;
							}

							if (douArticle == null) {
								log.error("[ERROR] article parse failed for " + identificadorAtualizacao + " - " + errorMessage);
								continue;
							}

							log.debug(douArticle.toString());

							if (identificadorAtualizacao.startsWith("/kafka/")) {
								identificadorAtualizacao = identificadorAtualizacao + "/" + douArticle.getUniqueName();
							}

							if (douArticle.getID() != null && !douArticle.getID().isEmpty()) {
								identificadorAtualizacao = douArticle.getID();
							}

							try {
								itemImportacao = _itemImportacaoLocalService.importarXML(
										groupId,
										idSecaoVocab,
										idArranjoSecaoVocab,
										idTipoMateriaVocab,
										imagesPath,
										identificadorAtualizacao,
										douArticle,
										null,
										serviceContext);
							} catch (Exception e) {
								e.printStackTrace();
								log.error("Could not import " + identificadorAtualizacao + ": " + e.getMessage(), e);
								continue;
							}
						}

						if (itemImportacao != null) {
							log.info("Import successfully done for " + identificadorAtualizacao);
							offsets.put(partition, new OffsetAndMetadata(record.offset() + 1));
							consumer.commitSync(offsets);
							offsets.clear();
						} else {
							log.error("[ERROR] Import failed for " + identificadorAtualizacao);
							continue;
						}

						try {
							List<ItemMigracao> itensMigracao = _itemMigracaoLocalService.recuperarItensPorIdentificadorAtualizacao(identificadorAtualizacao);
							if (itensMigracao.isEmpty()) {
								log.error("[ERROR] Migration item not found for " + identificadorAtualizacao);
							} else {
								if (itensMigracao.size() > 1) {
									log.warn("[WARN] Found " + itensMigracao.size() + " migrations itens for " + identificadorAtualizacao);
								}
								for (ItemMigracao itemMigracao : itensMigracao) {
									if (!itemMigracao.isMigrado()) {
										MigradorRunner currentMigrador = new MigradorRunner(
												itemMigracao,
												ownerId,
												groupId,
												assetDisplayPageType,
												assetDisplayPageId,
												layoutUuid,
												estrutura,
												template,
												documentsPath,
												serviceContext);
										executor.execute(currentMigrador);
									}
								};
							}
						} catch (SystemException e) {
							e.printStackTrace();
							log.error("[ERROR] Migration failed for import %s" + identificadorAtualizacao, e);
						}
					}
				}
			}
		} catch (WakeupException e) {
			// Ignore exception if closing
			if (!closed.get()) throw e;
		} finally {
			executor.shutdown();
			consumer.close();
		}

		while (!executor.isTerminated()) {
			try {
				if (!executor.awaitTermination(10L, TimeUnit.MINUTES)) {
					log.warn("The pool is taking too long to terminate. Forcing shutdown...");
					executor.shutdownNow();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.warn("Interruption occured while waiting pool termination. Forcing shutdown...");
				executor.shutdownNow();
			}
		}

		log.debug("Good bye!!!");
	}

	private void initImportadorXmlKafkaConsumer(
			SourceFormat format,
			Long groupId,
			Long idSecaoVocab,
			Long idArranjoSecaoVocab,
			Long idTipoMateriaVocab,
			Long structureId,
			Long templateId,
			Long ownerId,
			Integer assetDisplayPageType,
			Long assetDisplayPageId,
			String layoutUuid,
			String imagesPath) {
		this.format = format;
		this.groupId = groupId;
		this.idSecaoVocab = idSecaoVocab;
		this.idArranjoSecaoVocab = idArranjoSecaoVocab;
		this.idTipoMateriaVocab = idTipoMateriaVocab;

		this.idEstrutura = structureId;
		this.idTemplate = templateId;
		this.ownerId = ownerId;

		this.assetDisplayPageType = assetDisplayPageType;
		this.assetDisplayPageId = assetDisplayPageId;
		this.layoutUuid = layoutUuid;

		this.imagesPath = imagesPath;
		this.documentsPath = imagesPath;

		this.isLegacyXMLFormat = SourceFormat.LEGACY_XML.equals(format);

		closed.set(false);
	}

	private void initImportadorXmlKafkaConsumer(String topic, String propertiesFile,
			SourceFormat format,
			Long groupId,
			Long idSecaoVocab,
			Long idArranjoSecaoVocab,
			Long idTipoMateriaVocab,
			Long structureId,
			Long templateId,
			Long ownerId,
			Integer assetDisplayPageType,
			Long assetDisplayPageId,
			String layoutUuid,
			Long threadPoolSize,
			String imagesPath) throws PortalException, SystemException {

		initImportadorXmlKafkaConsumer(
				format,
				groupId,
				idSecaoVocab,
				idArranjoSecaoVocab,
				idTipoMateriaVocab,
				structureId,
				templateId,
				ownerId,
				assetDisplayPageType,
				assetDisplayPageId,
				layoutUuid,
				imagesPath);

		String deserializer = StringDeserializer.class.getName();
		Properties props = new Properties();
		InputStream input = null;
		Boolean success = true;
		try {
			input = new FileInputStream(propertiesFile);

			// load a properties file
			props.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
			success = false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					success = false;
				}
			}
		}

		if (!success) {
			throw new SystemException("Unable to load properties file");
		}

		for (String property : REQUIRED_PROPERTY_LIST) {
			if (!props.containsKey(property)) {
				throw new SystemException("Properties file found, but missing " + property + " property, which is required! Look at http://kafka.apache.org/0102/documentation.html#consumerconfigs for more information.");
			}
		}

		for (String property : DEFAULT_PROPERTY_VALUES.keySet()) {
			if (!props.containsKey(property)) {
				props.setProperty(property, DEFAULT_PROPERTY_VALUES.get(property));
			}
		}

		props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializer);
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

		this.properties = props;

		createKafkaConsumer(topic, threadPoolSize);
	}

	private void initImportadorXmlKafkaConsumer(String topic, String brokers, String consumerGroup, Map<String, String> properties,
			SourceFormat format,
			Long groupId,
			Long idSecaoVocab,
			Long idArranjoSecaoVocab,
			Long idTipoMateriaVocab,
			Long structureId,
			Long templateId,
			Long ownerId,
			Integer assetDisplayPageType,
			Long assetDisplayPageId,
			String layoutUuid,
			Long threadPoolSize,
			String imagesPath) throws PortalException, SystemException {

		initImportadorXmlKafkaConsumer(
				format,
				groupId,
				idSecaoVocab,
				idArranjoSecaoVocab,
				idTipoMateriaVocab,
				structureId,
				templateId,
				ownerId,
				assetDisplayPageType,
				assetDisplayPageId,
				layoutUuid,
				imagesPath);

		String deserializer = StringDeserializer.class.getName();
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);

		properties.forEach(props::put);

		for (String property : DEFAULT_PROPERTY_VALUES.keySet()) {
			if (!props.containsKey(property)) {
				props.setProperty(property, DEFAULT_PROPERTY_VALUES.get(property));
			}
		}

		props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializer);
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

		this.properties = props;

		createKafkaConsumer(topic, threadPoolSize);
	}

	private void createKafkaConsumer(String topic, Long threadPoolSize)
			throws PortalException, SystemException {

		Thread.currentThread().setContextClassLoader(null);

		try {
			this.consumer = new KafkaConsumer<>(this.properties);
			this.consumer.subscribe(Arrays.asList(topic));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not connect to Kafka brokers!", e);
			throw new PortalException(e);
		}

		try {
			Map<String, List<PartitionInfo>> topics = consumer.listTopics();
			List<PartitionInfo> partitionInfos = topics.get(topic);
			if (partitionInfos == null) {
				log.error("Partition information was not found for topic " + topic);
				this.consumer.close();
				throw new PortalException();
			} else {
				Collection<TopicPartition> partitions = new ArrayList<>();
				for (PartitionInfo partitionInfo : partitionInfos) {
					TopicPartition partition = new TopicPartition(topic, partitionInfo.partition());
					partitions.add(partition);
				}
				Map<TopicPartition, Long> endingOffsets = consumer.endOffsets(partitions);
				Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(partitions);
				long topicMessageCounter = diffOffsets(beginningOffsets, endingOffsets);
				log.debug("Found " + topicMessageCounter + " pending messages for topic " + topic);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not get info of Kafka topic!", e);
			this.consumer.close();
			throw new PortalException(e);
		}

		this.serviceContext = ServiceContextThreadLocal.getServiceContext();
		if (groupId != serviceContext.getScopeGroupId()) {
			// Set ScopeGroupId to Global
			serviceContext.setScopeGroupId(GroupLocalServiceUtil.fetchGroup(serviceContext.getCompanyId(), String.valueOf(serviceContext.getCompanyId())).getGroupId());
		}

		if (threadPoolSize > 1) {
			this.executor = Executors.newFixedThreadPool(threadPoolSize.intValue());
		}
		else {
			this.executor = Executors.newSingleThreadExecutor();
		}
	}

	private long diffOffsets(Map<TopicPartition, Long> beginning, Map<TopicPartition, Long> ending) {
		long retval = 0;
		for (TopicPartition partition : beginning.keySet()) {
			Long beginningOffset = beginning.get(partition);
			Long endingOffset = ending.get(partition);
			log.debug("Begin = " + beginningOffset + ", end = " + endingOffset + " for partition " + partition);
			if (beginningOffset != null && endingOffset != null) {
				retval += (endingOffset - beginningOffset);
			}
		}
		return retval;
	}

	private void shutdown() {
		closed.set(true);
		consumer.wakeup();
	}

	private void sendMessage(String identificadorAtualizacao, String materia) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("identificadorAtualizacao", identificadorAtualizacao);
		jsonObject.put("materia", materia);

		MessageBusUtil.sendMessage(LIFERAY_MESSAGE_BUS_DESTINATION, jsonObject.toString());
	}

	private static Log log = LogFactoryUtil.getLog(DouImporterKafkaServiceImpl.class);
	private static final String LIFERAY_MESSAGE_BUS_DESTINATION = "dou/xml/materia/publicada";
	private static final List<String> REQUIRED_PROPERTY_LIST = Arrays.asList(
			ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
			ConsumerConfig.GROUP_ID_CONFIG
	);
	private static final Map<String,String> DEFAULT_PROPERTY_VALUES = new HashMap<String,String>();
	static {
		DEFAULT_PROPERTY_VALUES.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		DEFAULT_PROPERTY_VALUES.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		DEFAULT_PROPERTY_VALUES.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "50");

		//DEFAULT_PROPERTY_VALUES.put("security.protocol", "SSL");
		//DEFAULT_PROPERTY_VALUES.put("ssl.truststore.location", "C:\\temp\\bigdata\\cliente\\sea.portal.truststore.jks");
		//DEFAULT_PROPERTY_VALUES.put("ssl.truststore.password", "seastore");
		//DEFAULT_PROPERTY_VALUES.put("ssl.keystore.location", "C:\\temp\\bigdata\\cliente\\sea.portal.keystore.jks");
		//DEFAULT_PROPERTY_VALUES.put("ssl.keystore.password", "seastore");
		//DEFAULT_PROPERTY_VALUES.put("ssl.key.password", "seastore");
	};

	private Thread _xmlConsumerThread = null;

	private final AtomicBoolean closed = new AtomicBoolean(false);

	private ExecutorService executor;

	private Properties properties;

	private KafkaConsumer<String, String> consumer;

	private ServiceContext serviceContext;

	private Long groupId;
	private Long idSecaoVocab;
	private Long idArranjoSecaoVocab;
	private Long idTipoMateriaVocab;
	private String imagesPath;
	private String documentsPath;

	private Long idEstrutura; 
	private Long idTemplate;
	private Long ownerId;

	private Integer assetDisplayPageType;
	private Long assetDisplayPageId;
	private String layoutUuid;

	private Boolean isLegacyXMLFormat = false;
	private SourceFormat format;

	@Reference
	private ItemImportacaoLocalService _itemImportacaoLocalService;
	@Reference
	private ItemMigracaoLocalService _itemMigracaoLocalService;
	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;
	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;
}