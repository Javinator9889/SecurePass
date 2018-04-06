package javinator9889.securepass.data.entry;

import java.io.Serializable;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class Category implements Serializable {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {
        this.id = 0;
        this.name = "Global";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
