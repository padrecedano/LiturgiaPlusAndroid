package org.deiverbum.app.model;


public class Misa {
    private MisaLecturas misaLecturas;
    @SuppressWarnings("unused")
    private LiturgiaPalabra liturgiaPalabra;

    public Misa() {
    }
        public MisaLecturas getMisaLecturas() {
            return misaLecturas;
        }

    @SuppressWarnings("unused")
    public void setMisaLecturas(MisaLecturas misaLecturas) {
            this.misaLecturas = misaLecturas;
        }


}
