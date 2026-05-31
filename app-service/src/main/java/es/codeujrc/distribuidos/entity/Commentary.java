package es.codeujrc.distribuidos.entity;

import jakarta.persistence.*;

@Entity
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Deck deck;
    @ManyToOne
    private User user;

    public Commentary() {
    }

    public Commentary(String content, Deck deck, User user) {
        super();
        this.content = content;
        this.deck = deck;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Deck getDeck() {
        return deck;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setUser(User user) {
        this.user = user;
    }

}