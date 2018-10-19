package pl.edu.agh.kis.databases.confectionery.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import javax.persistence.*;

@Entity
@Table(name="czekoladki")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chocolate {
    @Id
    @Column(name="idczekoladki")
    String id;
    @Column(name="nazwa")
    private String name;
    @Column(name="orzechy")
    //@Enumerated(EnumType.STRING)
    private String nuts;
    @Column(name="czekolada")
    private String chocolate;
    @Column(name="nadzienie")
    private String ganache;
    @Column(name="opis")
    private String description;
    @Column(name="koszt")
    private MonetaryAmount cost;
    @Column(name="masa")
    private int mass;
}