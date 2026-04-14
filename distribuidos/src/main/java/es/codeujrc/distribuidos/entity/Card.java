package es.codeujrc.distribuidos.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    public enum Counter {
        ONETHOUSAND, TWOTHOUSAND, NONE
    }

    public enum CardType {
        LEADER, EVENT, STAGE, CHARACTER
    }

    public enum Atribute {
        STRIKE, SLASH, SPECIAL, RANGED, WISDOM
    }

    public enum color {
        RED, BLUE, GREEN, YELLOW, PURPLE, BLACK
    }

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String triggerEffect;

    private String crew;

    private int cost;
    private int power;
    private int health;

    @Enumerated(EnumType.STRING)
    private CardType type;
    @Enumerated(EnumType.STRING)
    private Atribute attribute;
    @Enumerated(EnumType.STRING)
    private color color;
    @Enumerated(EnumType.STRING)
    private Counter counter;

    @ManyToMany(mappedBy = "cards")
    private List<Deck> decks;

    public Card() {
    }

    // Constructor for CHARACTER type cards
    public Card(String name, String description, String trigger, String crew, int cost, int power, Atribute atribute,
            Counter counter, color color, List<Deck> decks, byte[] image) {
        super();
        this.name = name;
        this.description = description;
        this.triggerEffect = trigger;
        this.crew = crew;
        this.cost = cost;
        this.power = power;
        this.type = CardType.CHARACTER;
        this.attribute = atribute;
        this.counter = counter;
        this.color = color;
        this.decks = decks;
        this.image = image;
    }

    // Constructor for events and stage type cards
    public Card(String name, String description, String trigger, String crew, int cost, CardType type, color color,
            List<Deck> decks, byte[] image) {
        super();
        this.name = name;
        this.description = description;
        this.triggerEffect = trigger;
        this.crew = crew;
        this.cost = cost;
        this.type = type;
        this.attribute = null;
        this.counter = Counter.NONE;
        this.color = color;
        this.decks = decks;
        this.image = image;
    }

    // Constructor for leader type cards
    public Card(String name, String description, String crew, int health, int power, Atribute atribute, color color,
            List<Deck> decks, byte[] image) {
        super();
        this.name = name;
        this.description = description;
        this.health = health;
        this.attribute = atribute;
        this.crew = crew;
        this.power = power;
        this.type = CardType.LEADER;
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

    public String getTriggerEffect() {
        return triggerEffect;
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

    public Atribute getAttribute() {
        return attribute;
    }

    public color getColor() {
        return color;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public byte[] getImage() {
        return image;
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

    public void setTriggerEffect(String triggerEffect) {
        this.triggerEffect = triggerEffect;
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

    public void setAttribute(Atribute attribute) {
        this.attribute = attribute;
    }

    public void setColor(color color) {
        this.color = color;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}