package es.codeujrc.distribuidos.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity

public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "decks", fetch = FetchType.EAGER)
    private List<Card> cards;

    @OneToMany(mappedBy = "deck")
    private  List<Commentary> commentaries;

    @ManyToOne
    private User user;

    public Deck() {
    }
    public Deck( String name, String description, List<Card> cards, List<Commentary> commentaries, User user) {
        super();
        this.name = name;
        this.description = description;
        this.cards = cards;
        this.commentaries = commentaries;
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<Card> getCards() {
        return cards;
    }
    public List<Commentary> getCommentaries() {
        return commentaries;
    }
    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
