package com.wen.crawler.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Type")
public class Type implements Serializable {
    private static final long serialVersionUID = 7419229779731522702L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    @Column(name = "TypeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long TypeId;

    @Column(name = "name",length = 20)
    private String name;

    public long getTypeId() {
        return TypeId;
    }

    public void setTypeId(long typeId) {
        TypeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
