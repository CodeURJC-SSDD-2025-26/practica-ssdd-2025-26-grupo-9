package es.codeujrc.distribuidos.service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.repository.CardRepository;

@Service
public class CardService {

	@Autowired
	private CardRepository repository;

	public Optional<Card> findById(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Card> findAll() {
		return repository.findAll();
	}
	public void save(Card card) {
		repository.save(card);
	}
	
	public void saveCard(Card card, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            
            card.setImage(imageFile.getBytes());
        }
        repository.save(card);
    }

	public void delete(long id) {
		repository.deleteById(id);
	}

	public byte[] getCardImage(long id) {
		Optional<Card> card = repository.findById(id);
		if (card.isPresent()) {
			return card.get().getImage();
		}
		return null;
	}
}