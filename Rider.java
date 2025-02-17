public class Rider{
    String aplicacion;
    String tipoServicio;
    int tiempoLlegada;
    int id;
    boolean disponible;

    public Rider(int i, String s, String tipo, int t){
        this.id = i;
        this.aplicacion = s;
        this.tipoServicio = tipo;
        this.tiempoLlegada = t;
        this.disponible = true;
    } 
    
    public String getAplicacion() {
        return aplicacion;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getId() {
        return id;
    }
    public void setEstado(boolean estado){
        this.disponible = estado;
    }
    public boolean getEstado(){
        return disponible;
    }

    public void imprimirInfo() {
        System.out.println( "Rider: " + id + ", app: " + aplicacion + ", vehiculo: " + tipoServicio + ", tiempo de llegada:" + tiempoLlegada);
    }

}
