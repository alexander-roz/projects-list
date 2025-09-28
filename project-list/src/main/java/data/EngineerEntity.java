package data;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "engineers")
public class EngineerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engineerId")
    private Integer engineerId;

    @Column(name = "engineerName")
    private String engineerName;

    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectEntity> projects = new ArrayList<>();

    @Override
    public String toString() {
        return  "id: " + engineerId +
                ", имя: " + engineerName;
    }
}
