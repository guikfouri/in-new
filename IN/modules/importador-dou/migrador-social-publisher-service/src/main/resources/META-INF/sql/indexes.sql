create index IX_4309F240 on IN_ItemPublicacao (entryId, postId[$COLUMN_LENGTH:75$]);
create index IX_FD92C53C on IN_ItemPublicacao (entryId, publishDate);
create unique index IX_709399A0 on IN_ItemPublicacao (entryId, socialNetwork);