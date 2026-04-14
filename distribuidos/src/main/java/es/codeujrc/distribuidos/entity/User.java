package es.codeujrc.distribuidos.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    public enum Role {
        ADMIN, REGISTERED
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deck> decks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentary> commentaries;

    @ManyToMany
    @JoinTable(
        name = "user_following",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<User> following;

    @ManyToMany(mappedBy = "following")
    private List<User> followers;


    public User() {
    }

    public User(String username, String password, String email, Role role, byte[] image) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public byte[] getImage() {
        return image;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public List<Commentary> getCommentaries() {
        return commentaries;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
}