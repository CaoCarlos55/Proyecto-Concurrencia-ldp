import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Principal{
    public static final String RIDERY = "ridery";
    public static final String BIPBIP = "bipbip";
    public static final String YUMMY = "yummy";

    public static final String MOTO = "motorcycle";
    public static final String CARRO = "car"; 

    public static int MAX_USER;
    public static int MAX_RIDERY;
    public static int MAX_BIPBIP;
    public static int MAX_YUMMY;

    private static int contRider = 1;
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Por favor, proporciona el nombre del archivo de entrada como argumento.");
            return;
        }

        leer(args[0]);
        
        App appMonitor = new App(MAX_RIDERY, MAX_BIPBIP, MAX_YUMMY);
        Usuario[] U  = new Usuario[MAX_USER];

        int app;
        int tipo;
        int tiempo;

        //Generar usuarios
        for(int i=0; i<MAX_USER; i++){
            tipo = (int)(Math.random()*2+1);
            app = (int)(Math.random()*3+1);
            tiempo = (int)(Math.random()*50+1);
            
            if(app == 1){
                if(tipo == 1){
                    U[i] = new Usuario(i+1, appMonitor, RIDERY, MOTO, tiempo);
                }else{
                    U[i] = new Usuario(i+1, appMonitor, RIDERY, CARRO, tiempo);
                }
            }else if(app == 2){
                if(tipo == 1){
                    U[i] = new Usuario(i+1, appMonitor, BIPBIP, MOTO, tiempo);
                }else{
                    U[i] = new Usuario(i+1, appMonitor, BIPBIP, CARRO, tiempo);
                }
            }else{
                if(tipo == 1){
                    U[i] = new Usuario(i+1, appMonitor, YUMMY, MOTO, tiempo);
                }else{
                    U[i] = new Usuario(i+1, appMonitor, YUMMY, CARRO, tiempo);
                }
            }
            U[i].imprimirInfo();
        }
        inicializarRider(appMonitor,BIPBIP, MAX_BIPBIP);
        inicializarRider(appMonitor,RIDERY, MAX_RIDERY);
        inicializarRider(appMonitor,YUMMY, MAX_YUMMY);

        for(int i=0; i<MAX_USER; i++){
            U[i].start();
        }
    }

    public static void inicializarRider(App appMonitor, String tipoRider, int max_tip){
        int tiempo;
        int tipo;
        for(int i=0; i<max_tip; i++){
            tipo = (int)(Math.random()*2+1);
            tiempo = (int)(Math.random()*30+1);
            
            if(tipo == 1){
                appMonitor.agregarRider(new Rider(contRider, tipoRider, MOTO, tiempo));
            }else{
                appMonitor.agregarRider(new Rider(contRider, tipoRider, CARRO, tiempo));
            }
            contRider++;
            
        }
    }

    public static void leer (String ruta){
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            MAX_USER = Integer.parseInt(br.readLine().trim());
            MAX_BIPBIP = Integer.parseInt(br.readLine().split(",")[1].trim());
            MAX_RIDERY = Integer.parseInt(br.readLine().split(",")[1].trim());
            MAX_YUMMY = Integer.parseInt(br.readLine().split(",")[1].trim());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
