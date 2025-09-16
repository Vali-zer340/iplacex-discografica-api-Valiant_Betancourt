package org.iplacex.proyectos.discografia.discos;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
    

public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;
    
    @Autowired
    private IArtistaRepository artistaRepo;


    //Post request
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco disco) {
        try {
            if (!artistaRepo.existsById(disco.idArtista)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El artista con id " + disco.idArtista + " no existe");
            }
            Disco saved = discoRepo.save(disco);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el disco: " + e.getMessage());
        }
    }


    //Get request (todos los discos)

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<?> HandleGetDiscosRequest() {
        
        List<Disco> discos = discoRepo.findAll();
        return ResponseEntity.ok(discos);
}



//Get request (Disco por id)

@GetMapping(
    value = "/disco/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)

public ResponseEntity<?> HandleGetDiscosRequest(@PathVariable String id) {
    Optional<Disco> disco = discoRepo.findById(id);
    if (!disco.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El disco con id " + id + " no existe");
    }
    else{
        return ResponseEntity.ok(disco.get());
    }
}


//Get request (Discos por artista (idArtista))

@GetMapping(
    value = "/artista/{id}/discos",
    produces = MediaType.APPLICATION_JSON_VALUE
)

public ResponseEntity<?> HandleGetDiscosByArtistaRequest(@PathVariable String id) {

    if (!artistaRepo.existsById(id)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El artista con id " + id + " no existe");
    }
    List<Disco> discos = discoRepo.findDiscosByIdArtista(id);
    return ResponseEntity.ok(discos);
}






}