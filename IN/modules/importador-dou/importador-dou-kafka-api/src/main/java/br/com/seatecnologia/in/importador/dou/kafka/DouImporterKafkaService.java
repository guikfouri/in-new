package br.com.seatecnologia.in.importador.dou.kafka;

import com.liferay.portal.kernel.exception.SystemException;

import java.util.Map;

/**
 * @author mgsasaki
 */
public interface DouImporterKafkaService {
	public Boolean isKafkaConsumerRunning();

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
			String imagesPath) throws SystemException;

	public void startConsumer(String topic, String brokers, String consumerGroup,
			Map<String, String> otherConfigurations,
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
			String imagesPath) throws SystemException;

	public void stopConsumer();
}