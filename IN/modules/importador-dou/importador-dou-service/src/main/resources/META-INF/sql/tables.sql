create table IN_ItemImportacao (
	itemImportacaoId LONG not null primary key,
	tipoItem VARCHAR(1000) null,
	dataImportacao DATE null,
	identificadorAtualizacao VARCHAR(1000) null,
	importado BOOLEAN
);