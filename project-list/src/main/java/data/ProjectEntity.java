package data;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "engineerId")
    private EngineerEntity engineerId;

    @Override
    public String toString() {
        return  "id: " + id +
                ", наименование: " + name +
                ", шифр: " + code +
                ", дата: " + date +
                ", исполнитель: " + engineerId.getEngineerName();
    }
}
