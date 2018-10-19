package pl.edu.agh.kis.databases.confectionery.infrastructure;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.kis.databases.confectionery.domain.Chocolate;
import pl.edu.agh.kis.databases.confectionery.domain.ChocolateNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ChocolateResource {

    @GetMapping("/chocolates")
    public List<Chocolate> retrieveAllChocolates() {
        return chocolateRepository.findAll();
    }

    @GetMapping("/chocolates/{id}")
    public Resource<Chocolate> retrieveChocolate(@PathVariable String id) {
        Optional<Chocolate> chocolate = chocolateRepository.findById(id);

        if (!chocolate.isPresent()) {
            throw new ChocolateNotFoundException(id);
        }

        Resource<Chocolate> resource = new Resource<>(chocolate.get());
        resource.add(linkTo(methodOn(this.getClass()).retrieveAllChocolates()).withRel("all-chocolates"));
        resource.add(linkTo(methodOn(this.getClass()).retrieveChocolate(id)).withSelfRel());
        return resource;
    }

    @PostMapping("/chocolates")
    public ResponseEntity<Object> createChocolate(@RequestBody Chocolate chocolate) {
        Chocolate savedChocolate = chocolateRepository.save(chocolate);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedChocolate.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    

    @Autowired
    private ChocolateRepository chocolateRepository;
}
