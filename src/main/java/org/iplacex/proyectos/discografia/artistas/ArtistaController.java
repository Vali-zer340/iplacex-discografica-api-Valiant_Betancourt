package org.iplacex.proyectos.discografia.artistas;

import org.springframework.http.MediaType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    // Post request
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista){
        Artista temp = artistaRepo.save(artista); 
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    // Get request (todos)
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<List<Artista>> HandleGetArtistasRequest(){
        List<Artista> artistas = artistaRepo.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    // Get request (por id)
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id){
        Optional<Artista> temp = artistaRepo.findById(id);
        if (!temp.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(), HttpStatus.OK);
    }

    // Update Request
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
        @PathVariable("id") String id,
        @RequestBody Artista artista
    ){
        if (!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        artista._id = id;
        Artista temp = artistaRepo.save(artista);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    // Delete Request

    @DeleteMapping(
        value = "/artista/{id}"
    )

    public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id") String id){
        if (!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } 
        Artista temp = artistaRepo.findById(id).get();
        artistaRepo.deleteById(id);
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }
}
