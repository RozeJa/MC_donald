package cz.rozek.jan.mc_donald.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.rozek.jan.mc_donald.models.Improvement;
import cz.rozek.jan.mc_donald.repositories.ImprovementRepository;

@Service
public class ImprovementService implements CrudService<Improvement, String> {
    
    @Autowired
    private ImprovementRepository improvementRepository;

    @Override
    public Improvement create(Improvement improvement) throws ValidationException, DuplicateKeyException {
        validate(improvement);

        improvement.setId(null);
        Improvement i = improvementRepository.save(improvement);
        return i;
    }

    @Override
    public Improvement read(String id) {
        Improvement i = improvementRepository.findById(id).get();
        return i;
    }

    @Override
    public List<Improvement> readAll() {
        List<Improvement> improvements = improvementRepository.findAll();
        return improvements;
    }

    @Override
    public Improvement update(String id, Improvement improvement) throws ValidationException, DuplicateKeyException {
        validate(improvement);

        improvement.setId(id);
        Improvement updated = improvementRepository.save(improvement);
        return updated;
    }

    @Override
    public Improvement delete(String id) {
        Improvement i = improvementRepository.findById(id).get();
        i.setAvailable(false);

        improvementRepository.save(i);
        return i;
    }


    @Override
    public void validate(Improvement improvement) throws ValidationException, DuplicateKeyException {
        StringBuilder sb = new StringBuilder();
        
        if (improvement.getName().isBlank()) {
            sb.append("Improvement name mustn't be ''.\n");
        }

        if (!sb.toString().isEmpty()) {
            throw new ValidationException(sb.toString());
        }
        
        if (improvementRepository.findByName(improvement.getName()) != null && improvement.getId().isBlank()) 
            throw new DuplicateKeyException("Improvement name: " + improvement.getName() + " is already exists.");
    }
}
