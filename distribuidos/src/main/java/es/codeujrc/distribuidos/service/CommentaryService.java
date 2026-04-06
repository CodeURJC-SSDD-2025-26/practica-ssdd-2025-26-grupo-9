package es.codeujrc.distribuidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeujrc.distribuidos.entity.Commentary;
import es.codeujrc.distribuidos.repository.CommentaryRepository;

@Service
public class CommentaryService {

	@Autowired
	private CommentaryRepository repository;

	public Optional<Commentary> findById(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Commentary> findAll() {
		return repository.findAll();
	}

	public void save(Commentary commentary) {
		repository.save(commentary);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
}