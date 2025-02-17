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

    public synchronized Rider solicitarRider (String appUser, String tipo){
        for(int i=0; i<ridersActual; i++){
            if(Riders[i].getTipoServicio().equals(tipo) && Riders[i].getEstado()){
                Riders[i].setEstado(false); //Ocupo el rider
                return Riders[i];
            }
        }
        return null;
    }

    public boolean compararRiders(Rider nuevo, Rider actual, String appUser){
        if(nuevo.getTiempoLlegada() < actual.getTiempoLlegada()){
            return true;
        }else if(nuevo.getTiempoLlegada() == actual.getTiempoLlegada()){
            return nuevo.getAplicacion().equals(appUser) && !actual.getAplicacion().equals(appUser);
        }
        return false;
    }

    public synchronized Rider buscarNuevoRider(Rider actual, String appUser, String tipo){
        for(int i=0; i<ridersActual; i++){
            if(Riders[i].getEstado() && compararRiders(Riders[i], actual, appUser)){
                Riders[i].setEstado(false); //Ocupo el nuevo
                return Riders[i];
            }
        }
        return null;
    }

    public void liberarRider(Rider rider, String tipo){
        for(int i=0; i<ridersActual; i++){
            if(Riders[i].getId() == rider.getId()){
                Riders[i].setEstado(true); //Libero el rider
                break;
            }
        }
    }

    
}
