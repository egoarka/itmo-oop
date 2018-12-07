use shopdb;

EXEC sp_msforeachtable "declare @name nvarchar(max); set @name = parsename('?', 1); exec sp_msdropconstraints @name";
EXEC sp_msforeachtable 'delete ?';