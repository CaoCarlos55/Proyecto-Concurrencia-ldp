public class App {
    private int ridersActual = 0;
    Rider[] Riders;

    public App(int ridery, int bipbip, int yummy){
        this.Riders = new Rider[ridery+bipbip+yummy];
    }

    public void agregarRider(Rider rider){
        rider.imprimirInfo();
        Riders[ridersActual]=rider;
        ridersActual++;
    }

    public boolean verificarServicio(String tipo){
        for(int i = 0 ; i <ridersActual; i++){
            if(Riders[i].getTipoServicio().equals(tipo)){
                return true;
            }
        }
        return false;
    }

    public synchronized Rider solicitarRider (String appUsuario, String tipo){
        Rider riderActual = null;
        boolean primero = true;
        
        for(int i=0; i<ridersActual; i++){
            
            if(Riders[i].getTipoServicio().equals(tipo) && Riders[i].getEstado()){
                
                if(primero){ //establecer el primer rider disponible como el primero que cumpla
                    riderActual = Riders[i];
                    riderActual.setEstado(false);
                    primero = false;
                }
                if(compararRiders(Riders[i], riderActual, appUsuario)){
                    riderActual.setEstado(true);//Libero el rider viejo
                    Riders[i].setEstado(false); //Ocupo el rider nuevo
                    riderActual = Riders[i]; //asigno el rider nuevo
                }
            }
        }
        return riderActual;
    }

    public boolean compararRiders(Rider nuevo, Rider actual, String appUsuario){
        if(nuevo.getTiempoLlegada() < actual.getTiempoLlegada()){
            return true;
        }else if(nuevo.getTiempoLlegada() == actual.getTiempoLlegada()){
            return nuevo.getAplicacion().equals(appUsuario) && !actual.getAplicacion().equals(appUsuario);
        }
        return false;
    }

    public synchronized Rider buscarNuevoRider(Rider actual, String appUsuario, String tipo){
        for(int i=0; i<ridersActual; i++){
            if(Riders[i].getTipoServicio().equals(tipo) && Riders[i].getEstado() && compararRiders(Riders[i], actual, appUsuario)){
                Riders[i].setEstado(false); //Ocupo el nuevo
                return Riders[i];
            }
        }
        return null;
    }

    public void liberarRider(Rider rider){
        for(int i=0; i<ridersActual; i++){
            if(Riders[i].getId() == rider.getId()){
                Riders[i].setEstado(true); //Libero el rider
                break;
            }
        }
    }

    
}
