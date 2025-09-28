package data;

import jakarta.persistence.*;
import lombok.Data;

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

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer id) {
        this.engineerId = id;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String name) {
        this.engineerName = name;
    }

    @Override
    public String toString() {
        return  "id: " + engineerId +
                ", имя: " + engineerName;
    }
}
