
public class Usuario extends Thread {
    App appMonitor;
    String aplicacion;
    String tipoServicio;
    int tiempoViaje;
    int id;

    public Usuario(int i, App a, String s, String tipo, int t){
        this.id = i;
        this.appMonitor = a;
        this.aplicacion = s;
        this.tipoServicio = tipo;
        this.tiempoViaje = t;
    } 

    public void imprimirInfo() {
        System.out.println( "Usuario: " + id + ", app: " + aplicacion + ", vehiculo seleccionado: " + tipoServicio + ", tiempo de viaje:" + tiempoViaje);
    }

    @Override
    public void run(){
        Rider riderActual = null;

        while(riderActual == null){
            riderActual = appMonitor.solicitarRider(aplicacion, tipoServicio);
            if (riderActual == null) {
                //System.out.println("Usuario "+ id +" de " + aplicacion + " no encontró riders de " + tipoServicio + " disponibles.");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("**-Usuario "+ id +" (" + aplicacion + ", " + tipoServicio + ") asignado al Rider " + riderActual.getId());

        int tiempoDeEsperaTotal = riderActual.getTiempoLlegada();

        while (tiempoDeEsperaTotal > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // Espera 1 segundo
            tiempoDeEsperaTotal--;

            Rider nuevoRider = appMonitor.buscarNuevoRider(riderActual, aplicacion, tipoServicio);
            
            if (nuevoRider != null) {
                System.out.println("<--> Usuario " + id +" (" + aplicacion + ") cambió del rider " + riderActual.getId() + " a " + nuevoRider.getId());
                appMonitor.liberarRider(riderActual);

                riderActual = nuevoRider;
                tiempoDeEsperaTotal = riderActual.getTiempoLlegada();
            }
        }
        // Inicia el viaje
        System.out.println("-->Usuario " + id +" (" + aplicacion + ") inicia viaje con el rider " + riderActual.getId() + ". Duración: " + tiempoViaje + "s.");
        try {
            sleep(tiempoViaje * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Libera el rider
        appMonitor.liberarRider(riderActual);
        System.out.println("!!!!!----Usuario " + id +" (" + aplicacion + ") completó el viaje.");
    }
}
