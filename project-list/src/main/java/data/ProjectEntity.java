package data;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EngineerEntity engineerID;

    @Override
    public String toString() {
        return  "id: " + id +
                ", наименование: " + name +
                ", шифр: " + code +
                ", дата: " + date +
                ", исполнитель: " + engineerID.getEngineerName();
    }
}
