
insert into documentos (nombre_Documento, version)
values('DocPrueba', 1);

insert into Lineas (id_Doc, texto_Linea, version, numero_Linea, proxima_Linea)
values(1,'Hola soy la primera linea', 1, 1, 2);

insert into Lineas (id_Linea, id_Doc, texto_Linea, version, numero_Linea, proxima_Linea)
values(2, 1, 'Hola soy la segunda linea', 1, 2, -1);

insert into Lineas_historico select * from lineas;