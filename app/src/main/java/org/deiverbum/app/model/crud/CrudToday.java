package org.deiverbum.app.model.crud;

import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

public class CrudToday {

    public List<Today> c;
    public List<Today> u;
    public List<Today> d;

    @SuppressWarnings("unused")
    public CrudToday() {
    }

    public void c(Today t) {
        if (c == null) {
            c = new ArrayList<>();
        }
        c.add(t);
    }

    public void addUpdate(Today t) {
        if (u == null) {
            u = new ArrayList<>();
        }
        u.add(t);
    }

    public void addCreate(Today t) {
        if (c == null) {
            c = new ArrayList<>();
        }
        c.add(t);
    }

    public void addDelete(Today t) {
        if (d == null) {
            d = new ArrayList<>();
        }
        d.add(t);
    }


}
