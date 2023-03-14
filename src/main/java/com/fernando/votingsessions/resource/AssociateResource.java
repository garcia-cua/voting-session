package com.fernando.votingsessions.resource;

import com.fernando.votingsessions.model.Associate;
import com.fernando.votingsessions.service.AssociateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/associates", produces = "application/json")
public class AssociateResource {

    final private AssociateService associateService;

    public AssociateResource(AssociateService associateService) {
        this.associateService = associateService;
    }

    @GetMapping
    public ResponseEntity<List<Associate>> findAll() {
        return ResponseEntity.ok(associateService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Associate> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(associateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Associate> create(@RequestBody Associate associate) {
        return ResponseEntity.ok(associateService.create(associate));
    }

}
