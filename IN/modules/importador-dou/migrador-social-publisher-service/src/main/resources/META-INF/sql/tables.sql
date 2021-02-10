create table IN_ItemPublicacao (
	itemPublicacaoId LONG not null primary key,
	entryId LONG,
	postId VARCHAR(75) null,
	publishDate DATE null,
	modifiedDate DATE null,
	socialNetwork INTEGER,
	itemMigracaoId LONG
);