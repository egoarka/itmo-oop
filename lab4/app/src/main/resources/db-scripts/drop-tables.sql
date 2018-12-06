use shopdb;
-- drop all tables
EXEC sp_msforeachtable "declare @name nvarchar(max); set @name = parsename('?', 1); exec sp_msdropconstraints @name";
EXEC sp_msforeachtable "drop table ?";