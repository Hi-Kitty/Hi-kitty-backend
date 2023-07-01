package io.nuabo.hikitty.user.infrastructure.entity;


import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "profile")
@Table(name = "profile")
@SQLDelete(sql = "update profile set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class ProfileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static ProfileEntity from(Profile profile, User user) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.id = profile.getId();
        profileEntity.originalName = profile.getOriginalName();
        profileEntity.savedName = profile.getSavedName();
        profileEntity.url = profile.getUrl();
        profileEntity.user = UserEntity.from(user);
        return profileEntity;
    }

    public Profile toModel() {
        return Profile.builder()
                .id(id)
                .originalName(originalName)
                .savedName(savedName)
                .url(url)
                .build();
    }
}
