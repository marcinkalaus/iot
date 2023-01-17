package agh.iot.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "data")
public class ModuleData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Integer dataInt;
    private String dataString;
    private Boolean dataBool;
    private Float dataFloat;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;
}
