package agh.iot.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "module")
public class Module {
    @Id
    private long id;
    @Column
    private String name;
    @Column
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "module_id")
    private Set<DataModel> data;
}
