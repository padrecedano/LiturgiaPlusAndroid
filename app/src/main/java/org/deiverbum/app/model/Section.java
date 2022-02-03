package org.deiverbum.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 26/1/22
 * @since
 */
public class Section {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderedlist")
    @Expose
    private List<OrderedList> orderedlist = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subsections")
    @Expose
    private List<SubSection> subsections = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Section() {
    }

    /**
     *
     * @param subsections
     * @param orderedlist
     * @param id
     * @param title
     */
    public Section(Integer id, List<OrderedList> orderedlist, String title,
                   List<SubSection> subsections) {
        super();
        this.id = id;
        this.orderedlist = orderedlist;
        this.title = title;
        this.subsections = subsections;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderedList> getOrderedlist() {
        return orderedlist;
    }

    public void setOrderedlist(List<OrderedList> orderedlist) {
        this.orderedlist = orderedlist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubSection> getSubsections() {
        return subsections;
    }

    public void setSubsections(List<SubSection> subsections) {
        this.subsections = subsections;
    }

}