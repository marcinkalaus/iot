package agh.iot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String guid;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "devices_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Module> modules;
    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<User> users = new ArrayList<>();

}
