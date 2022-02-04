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

    @SuppressWarnings("unused")
    public String getInvocacion() {
        return invocacion;
    }

    @SuppressWarnings("unused")
    public void setInvocacion(String invocacion) {
        this.invocacion = invocacion;
    }

    public Kyrie getKyrie() {
        return kyrie;
    }

    @SuppressWarnings("unused")
    public void setKyrie(Kyrie kyrie) {
        this.kyrie = kyrie;
    }

}
