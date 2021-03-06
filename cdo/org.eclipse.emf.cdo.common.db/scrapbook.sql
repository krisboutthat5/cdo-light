select * from DBREVISIONCACHE_REVISIONS where VERSION = (select MAX( VERSION ) from DBREVISIONCACHE_REVISIONS)

select id from DBREVISIONCACHE_REVISIONS group by id, version, created, revised;

drop table DBREVISIONCACHE_REVISIONS;

select MAX( VERSION ) from DBREVISIONCACHE_REVISIONS where id = 4

select ID, VERSION, CDOREVISION from DBREVISIONCACHE_REVISIONS where id IN (select distinct id from DBREVISIONCACHE_REVISIONS) GROUP BY ID 

select CDOREVISION from DBREVISIONCACHE_REVISIONS where (select ID, MAX(VERSION) as version from DBREVISIONCACHE_REVISIONS GROUP by ID)

select * from DBREVISIONCACHE_REVISIONS where (ID, VERSION) IN (select ID, MAX(VERSION) as version from DBREVISIONCACHE_REVISIONS GROUP by ID)


select ID, MAX(VERSION), CDOREVISION as version from DBREVISIONCACHE_REVISIONS GROUP by ID

select VERSION from DBREVISIONCACHE_REVISIONS WHERE version = max(version)

select * from DBREVISIONCACHE_REVISIONS where version = (select max(version) from DBREVISIONCACHE_REVISIONS where DBREVISIONCACHE_REVISIONS.id = DBREVISIONCACHE_REVISIONS.id)

select * from DBREVISIONCACHE_REVISIONS where version = (select max(version) from DBREVISIONCACHE_REVISIONS where DBREVISIONCACHE_REVISIONS.id = DBREVISIONCACHE_REVISIONS.id)

select * from DBREVISIONCACHE_REVISIONS a where version = (select max(version) from DBREVISIONCACHE_REVISIONS b where a.id = b.id)

select * from DBREVISIONCACHE_REVISIONS where id = 3 AND created >= 1244048304119 AND revised <= 1244048304119;

UPDATE DBREVISIONCACHE_REVISIONS SET REVISED = 2222 WHERE  ID = 3 AND VERSION = 1

select ID, VERSION, CREATED, REVISED from DBREVISIONCACHE_REVISIONS 

select ID, VERSION, CREATED, REVISED from DBREVISIONCACHE_REVISIONS WHERE ID = 3 AND VERSION = 1

SELECT id, version, created, revised FROM dbrevisioncache_revisions WHERE id = 3 AND created <= 1244129540039 AND (revised >= 1244129540039 OR revised = 0 )

SELECT * FROM DBREVISIONCACHE_REVISIONS

SELECT cdorevision, revised FROM dbrevisioncache_revisions WHERE id = 3 AND created <= 1244760465866 AND ( revised >= 1244760465866 OR revised = 0 ) AND resourcenode_name = 'container1'
