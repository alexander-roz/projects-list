package data;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "engineers")
public class EngineerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eng_id")
    private Integer id;

    @Column(name = "eng_name")
    private String name;

    @Override
    public String toString() {
        return  "id: " + id +
                ", имя: " + name;
    }
}
