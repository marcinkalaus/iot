package agh.iot.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "data")
public class DeviceData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Integer dataInt;
    private long epochDate;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;
}
