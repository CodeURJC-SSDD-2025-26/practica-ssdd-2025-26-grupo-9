package es.codeujrc.distribuidos.entity;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private enum CardType {
        LEADER, EVENT, STAGE, CHARACTER
    }
    private enum Atribute {
        STRIKE, SLASH, SPECIAL, RANGED, WISDOM
    }
    private enum color {
        RED, BLUE, GREEN, YELLOW, PURPLE, BLACK
    }
     @Lob
    private byte[] image;

    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String trigger;

    private String crew;

    private int cost;
    private int power;
    private int health;

    @Enumerated(EnumType.STRING)
    private CardType type;
    @Enumerated(EnumType.STRING)
    private Atribute atribute;
    @Enumerated(EnumType.STRING)
    private color color;

    @ManyToMany
    private List<Deck> decks;

    public Card() {
    }

    public Card(String name, String description, String trigger, String crew, int cost, int power, int health, CardType type, Atribute atribute, color color, List<Deck> decks, byte[] image) {
        super();
        this.name = name;
        this.description = description;
        this.trigger = trigger;
        this.crew = crew;
        this.cost = cost;
        this.power = power;
        this.health = health;
        this.type = type;
        this.atribute = atribute;
        this.color = color;
        this.decks = decks;
        this.image = image;
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
    public String getTrigger() {
        return trigger;
    }
    public String getCrew() {
        return crew;
    }
    public int getCost() {
        return cost;
    }
    public int getPower() {
        return power;
    }
    public int getHealth() {
        return health;
    }
    public CardType getType() {
        return type;
    }
    public Atribute getAtribute() {
        return atribute;
    }
    public color getColor() {
        return color;
    }
    public List<Deck> getDecks() {
        return decks;
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
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }
    public void setCrew(String crew) {
        this.crew = crew;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setType(CardType type) {
        this.type = type;
    }
    public void setAtribute(Atribute atribute) {
        this.atribute = atribute;
    }
    public void setColor(color color) {
        this.color = color;
    }
    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

}
