SELECT * FROM stop_bad_files.files;
SELECT * FROM stop_bad_files.files WHERE uidNumber = 1001;

commit;
SELECT * FROM stop_bad_files.files WHERE filename LIKE '%nuev%';
DELETE FROM stop_bad_files.files WHERE filename LIKE '%nuev%';
TRUNCATE TABLE stop_bad_files.files;

UPDATE stop_bad_files.files set report = "test3" WHERE idfile = 3;

INSERT INTO `stop_bad_files`.`files` (`filename`, `size`, `state`, `uidNumber`, `gidNumber`, `datetime`, `path`) 
VALUES ('fichero_de_jose', 456, 0, 0, 0, '2016-02-26 18:43:23', '/StopBadFiles/store/fichero_de_jose' );
INSERT INTO `stop_bad_files`.`files` (`filename`, `size`, `state`, `uidNumber`, `gidNumber`, `datetime`, `path`) 
VALUES ('nuevo_AKI', 234, 0, 0, 0, '2016-02-26 18:43:30', '/StopBadFiles/store/nuevo_AKI' );


