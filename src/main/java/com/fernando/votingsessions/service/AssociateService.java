package com.fernando.votingsessions.service;

import com.fernando.votingsessions.model.Associate;
import com.fernando.votingsessions.repository.AssociateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociateService {

    private final AssociateRepository associateRepository;

    public AssociateService(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    public List<Associate> findAll() {
        return associateRepository.findAll();
    }

    public Associate findById(Long id) {
        return associateRepository.findById(id).get();
    }

    public Associate create(Associate order) {
        return associateRepository.save(order);
    }

}
