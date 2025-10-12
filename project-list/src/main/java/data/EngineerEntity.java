package data;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "engineers")
public class EngineerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engineerId")
    private Integer engineerId;

    @Column(name = "engineerName", nullable = false)
    private String engineerName;

    @OneToMany(mappedBy = "engineerId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProjectEntity> projects = new HashSet<>();

    @Override
    public String toString() {
        return engineerName;
    }
}
