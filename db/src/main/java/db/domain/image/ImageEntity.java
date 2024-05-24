package db.domain.image;


import db.common.BaseEntity;
import db.domain.image.enums.ImageKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "image")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageEntity extends BaseEntity {

    @Column(length = 200, nullable = false)
    private String imageUrl;

    @Column(length = 100, nullable = false)
    private String originalName;

    @Column(length = 100, nullable = false)
    private String serverName;

    @Column(length = 100)
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private ImageKind kind;

    @Column(nullable = false)
    private Long goodsId;

    @Column(nullable = false, length = 10)
    private String extension;

}
