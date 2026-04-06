package es.codeujrc.distribuidos.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeujrc.distribuidos.entity.Deck;
import es.codeujrc.distribuidos.repository.DeckRepository;

@Service
public class DeckService {

	@Autowired
	private DeckRepository repository;

	public Optional<Deck> findById(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Deck> findAll() {
		return repository.findAll();
	}

	public void save(Deck deck) {
		repository.save(deck);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
}