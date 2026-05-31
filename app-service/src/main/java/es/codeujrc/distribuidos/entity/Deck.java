package es.codeujrc.distribuidos.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Card> cards;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentary> commentaries;

    @ManyToOne
    private User user;

    public Deck() {
    }

    public Deck(String name, String description, List<Card> cards, List<Commentary> commentaries, User user) {
        super();
        this.name = name;
        this.description = description;
        this.cards = cards;
        this.commentaries = commentaries;
        this.user = user;
        this.creationDate = LocalDate.now();
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

    public LocalDate getCreationDate() {
        return creationDate;
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

    public String getFormattedDate() {
        if (this.creationDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return this.creationDate.format(formatter);
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

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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