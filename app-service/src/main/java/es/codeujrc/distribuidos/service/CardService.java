package es.codeujrc.distribuidos.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
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
	public Map<String, Object> getMetaCardsData() {
		
    // Obtenemos los nombres y el tamaño de su lista de mazos
    List<Object[]> results = repository.countCardUsageInDecks();
    
    List<String> names = new ArrayList<>();
    List<Integer> deckCounts = new ArrayList<>();

    // Limitamos a las 7 cartas más populares
    results.stream().limit(7).forEach(row -> {
        names.add((String) row[0]);
        deckCounts.add((Integer) row[1]);
    });

    Map<String, Object> map = new HashMap<>();
    map.put("names", names);
    map.put("counts", deckCounts);
    return map;
}
}