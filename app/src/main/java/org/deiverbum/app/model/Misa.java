package org.deiverbum.app.model;


public class Misa {
    private MisaLecturas misaLecturas;
    private LiturgiaPalabra liturgiaPalabra;

    public Misa() {
    }
        public MisaLecturas getMisaLecturas() {
            return misaLecturas;
        }

        public void setMisaLecturas(MisaLecturas misaLecturas) {
            this.misaLecturas = misaLecturas;
        }

    public LiturgiaPalabra getLiturgiaPalabra() {
        return liturgiaPalabra;
    }

    public String toString() {

        return "This is" + this.getClass();
    }

    public void setLiturgiaPalabra(LiturgiaPalabra liturgiaPalabra) {
        this.liturgiaPalabra = liturgiaPalabra;
    }
}
