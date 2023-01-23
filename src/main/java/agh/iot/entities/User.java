package agh.iot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column
    @Size(max = 20)
    private String username;
    @NotBlank
    @Column
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;

//    @ManyToMany
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    Set<Device> devices;
}