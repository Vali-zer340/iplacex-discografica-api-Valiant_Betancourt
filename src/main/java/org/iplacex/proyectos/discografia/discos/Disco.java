package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("discos")
public class Disco {
        @Id
        public String _id;
        public String idArtista;
        public String nombre;
        
        @Field(name = "Año Lanzamiento", targetType = FieldType.INT32)
        public int aniolanzamiento;
        public List<String> canciones;

    }
