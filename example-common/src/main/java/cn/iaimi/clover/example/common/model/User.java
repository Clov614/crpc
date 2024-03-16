package cn.iaimi.clover.example.common.model;

import java.io.Serializable;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/15
 */
public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
