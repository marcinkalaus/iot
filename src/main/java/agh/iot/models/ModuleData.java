package agh.iot.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
