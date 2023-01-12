package agh.iot.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "data")
public class DataModel {
    @Id
    private long id;
    private int dataInt;
    private String dataString;
    private boolean dataBool;
    private float dataFloat;
}
