package org.deiverbum.app.model;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since
 */




@SuppressWarnings("unused")
public class Todo {

    public String padre;
    public int id_homilia;
    public String texto;
    public List<Homilia> homilias;
}

