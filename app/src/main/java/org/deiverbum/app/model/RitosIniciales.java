package org.deiverbum.app.model;

public class RitosIniciales {

    //    @SerializedName("invocacion")
//    @Expose
    private String invocacion;
    //    @SerializedName("kyrie")
//    @Expose
    private Kyrie kyrie;

    public RitosIniciales() {
    }
    public String getInvocacion() {
        return invocacion;
    }

    public void setInvocacion(String invocacion) {
        this.invocacion = invocacion;
    }

    public Kyrie getKyrie() {
        return kyrie;
    }

    public void setKyrie(Kyrie kyrie) {
        this.kyrie = kyrie;
    }

}
