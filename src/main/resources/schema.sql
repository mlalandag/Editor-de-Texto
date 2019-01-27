create table documentos
(
   id_Doc  integer auto_increment,
   nombre_Documento varchar(255) not null,
   version integer not null,
   primary key(nombre_Documento)
);

create table lineas
(
   id_Linea  integer auto_increment,
   id_Doc    integer not null,
   texto_Linea varchar(255) not null,
   version integer not null,
   numero_Linea integer not null,
   proxima_Linea integer,
   primary key(id_Doc, id_Linea),
   foreign key (id_Doc) references documentos(id_Doc)
);

create table lineas_historico
(
   id_Linea  integer not null,
   id_Doc    integer not null,
   texto_Linea varchar(255) not null,
   version integer not null,
   numero_Linea integer not null,
   proxima_Linea integer,
   primary key(id_Doc, numero_Linea, version),
   foreign key (id_Doc) references documentos(id_Doc)
);