
package Calculadora;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.HashMap;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.lang.Object;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.GeneralSecurityException;
import java.lang.Exception;
import java.lang.Throwable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


//public abstract class MessageDigest extends MessageDigestSpi;
public class CalculadoraController implements Initializable {

    String acum="";
    int NodoAConectar=0;
    @FXML
    private Button num1;
    @FXML
    private Button num2;
    @FXML
    private Button num3;
    @FXML
    private Button num4;
    @FXML
    private Button num5;
    @FXML
    private Button num6;
    @FXML
    private Button num7;
    @FXML
    private Button num8;
    @FXML
    private Button num9;
    @FXML
    private Button num0;
    @FXML
    private Button punt;
    @FXML
    private Button bora;
    @FXML
    private Button suma;
    @FXML
    private Button resta;
    @FXML
    private Button multiplica;
    @FXML
    private Button divide;
    @FXML
    private Label Pantalla;
    @FXML
    private TextArea ResultadoSuma;
    @FXML
    private TextArea ResultadoResta;
    @FXML
    private TextArea ResultadoMultiplicacion;
    @FXML
    private TextArea ResultadoDivision;
    
    
    double acumulador=0;
    char operacion='0';
    @FXML
    private Button igual;
    HiloRecibir Recibir;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerSocket Llegada = create(6000);
        
        String mensaje="";
        final String HOST ="127.0.0.1";
        int PUERTO =8000;
        DataInputStream in;
        DataOutputStream out;
        var Buscando=true;
        while(Buscando){
            try {
                Socket elsocket = new Socket(HOST,PUERTO);
                in = new DataInputStream(elsocket.getInputStream());
                out = new DataOutputStream(elsocket.getOutputStream());

                out.writeUTF("Cliente "+Llegada.getLocalPort());

                mensaje = in.readUTF();
                if (Integer.parseInt(mensaje) !=0)
                {
                    System.out.println(PUERTO);
                    System.out.println(mensaje);
                    Buscando = false;
                    break;
                }
                elsocket.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexion");
            }
            PUERTO= PUERTO+200;
            if (PUERTO>60000)
            {
                Buscando = false;
            }
        }
        
        NodoAConectar = Integer.parseInt(mensaje);
        
        Recibir = new HiloRecibir(Llegada);
        Thread T1 = new Thread(Recibir);
        T1.start();
    }

    public ServerSocket create(int ports){
        for (int port= ports;port < 7000; port++) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; 
            }
        }
        try {
            
            throw new IOException("no free port found");
        } catch (IOException ex) {
            Logger.getLogger(Appmain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    

    @FXML
    private void button1(ActionEvent event) {
        acum=acum+"1";
        Pantalla.setText(acum);
    }

    @FXML
    private void button2(ActionEvent event) {
        acum=acum+"2";
        Pantalla.setText(acum);
    }

    @FXML
    private void button3(ActionEvent event) {
        acum=acum+"3";
        Pantalla.setText(acum);
    }

    @FXML
    private void button4(ActionEvent event) {
        acum=acum+"4";
        Pantalla.setText(acum);
    }

    @FXML
    private void button5(ActionEvent event) {
        acum=acum+"5";
        Pantalla.setText(acum);
    }

    @FXML
    private void button6(ActionEvent event) {
        acum=acum+"6";
        Pantalla.setText(acum);
    }

    @FXML
    private void Button7(ActionEvent event) {
        acum=acum+"7";
        Pantalla.setText(acum);
    }

    @FXML
    private void button8(ActionEvent event) {
        acum=acum+"8";
        Pantalla.setText(acum);
    }

    @FXML
    private void button9(ActionEvent event) {
        acum=acum+"9";
        Pantalla.setText(acum);
    }

    @FXML
    private void button0(ActionEvent event) {
        acum=acum+"0";
        Pantalla.setText(acum);
    }

    @FXML
    private void buttonpunt(ActionEvent event) {
        acum=acum+".";
        Pantalla.setText(acum);
    }

    @FXML
    private void buttonbor(ActionEvent event) {
        acum = "";
        Pantalla.setText(acum);
    }

    @FXML
    private void buttonsum(ActionEvent event) {
        
        acumulador = Double.parseDouble(acum);
        operacion = '1';
        acum=acum + " + ";
        Pantalla.setText(acum);
        
    }

    @FXML
    private void buttonmen(ActionEvent event) {
        
        acumulador = Double.parseDouble(acum);
        operacion = '2';
        acum=acum + " - ";
        Pantalla.setText(acum);
    }

    @FXML
    private void buttonmult(ActionEvent event) {
       acumulador = Double.parseDouble(acum);
        operacion = '3';
        acum=acum + " * ";
        Pantalla.setText(acum);
    }

    @FXML
    private void buttondiv(ActionEvent event) {
        acumulador = Double.parseDouble(acum);
        operacion = '4';
        acum=acum + " / ";
        Pantalla.setText(acum);
    }
    
    String sumat="";
    String restt="";
    String multt="";
    String divit="";
    List<String> Sumas = new ArrayList<String>(); 
    List<String> Restas = new ArrayList<String>();
    List<String> Multiplicaciones= new ArrayList<String>();
    List<String> Divisiones = new ArrayList<String>();
    HashMap<String, String> eventos = new HashMap<String, String>();
    HashMap<String, Set<String>> acuses = new HashMap<String, Set<String>>();
    @FXML
    private void botonigua(ActionEvent event) {
        String[] arrSplit = acum.split(" ");//<-----  :
        
        String total=arrSplit[0] + ":"+ arrSplit[2]; // < -----  2 : 3 :
        
        
        switch(operacion)
        {
            case '1':
                suma.setDisable(true);
                Sumas.add(acum);
                sumat=sumat+"\n"+acum;
                Recibir.hilo.e_sumita = HashearMensaje(total,"1");//"MD5(total+huella+1)"
                eventos.put(Recibir.hilo.e_sumita, total);
                acuses.put(Recibir.hilo.e_sumita, new HashSet<String>());
                Recibir.hilo.sumita = total;
                
                break;
            case '2':
                resta.setDisable(true);
                Restas.add(acum);
                restt=restt+"\n"+acum;
                Recibir.hilo.e_restita = HashearMensaje(total,"2");
                eventos.put(Recibir.hilo.e_restita, total);
                acuses.put(Recibir.hilo.e_restita, new HashSet<String>());
                Recibir.hilo.restita = total;
                
                break;
            case '3':
                multiplica.setDisable(true);
                Multiplicaciones.add(acum);
                multt=multt+"\n"+acum;
                Recibir.hilo.e_multita = HashearMensaje(total,"3");
                eventos.put(Recibir.hilo.e_multita, total);
                acuses.put(Recibir.hilo.e_multita, new HashSet<String>());
                Recibir.hilo.multita = total;
               
                break;
            case '4':
                divide.setDisable(true);
                Divisiones.add(acum);
                divit=divit+"\n"+acum;
                Recibir.hilo.e_divita = HashearMensaje(total,"4");
                eventos.put(Recibir.hilo.e_divita, total);
                acuses.put(Recibir.hilo.e_divita, new HashSet<String>());
                Recibir.hilo.divita = total;
                
                break;
        }
        acum="";
        Pantalla.setText(acum);
    }
     public static String HashearMensaje(String total, String ope)
    {
        String mensaje = total+huella +ope;
         String hashtext = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(mensaje.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            hashtext = bigInt.toString(16);
        }catch(NoSuchAlgorithmException e){
        
        }
        
        return hashtext;
    }
    public void conexion(String Total, char operacion)
    {
        String mensaje="";
        final String HOST ="127.0.0.1";
        DataInputStream in;
        DataOutputStream out;
        
        
        
        try {
            Socket elsocket = new Socket(HOST,NodoAConectar);
            out = new DataOutputStream(elsocket.getOutputStream());
            
            out.writeUTF(Total);
            

            elsocket.close();

        } catch (IOException ex) {
            System.out.println("Fallo, error en la conexion");
        }
    }
    public static String GeneraHuella()
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
       
        String hashtext = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(timeStamp.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            hashtext = bigInt.toString(16);
        }catch(NoSuchAlgorithmException e){
        
        }
        
        return hashtext;
    }
   
    public static String huella = GeneraHuella(); //MD5(timestamp)
    public class HiloReenviar implements Runnable
    {
        
        public String sumita, restita,multita,divita;
        public String e_sumita, e_restita,e_multita,e_divita;
        @Override
        public void run() {
            while(true)
            {
                if(sumita != null)
                {
                    if(acuses.get(e_sumita).size() < 1)
                    {
                    conexion(("1 "+ e_sumita + " "+huella +" "+ sumita),'+');
                    System.out.println("Servicio de Suma no disponible");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    conexion(("99 "+ e_sumita + " "+huella +" "+ 1),'+');
                    }
                    else
                    {
                        
                        acuses.remove(e_sumita);
                        suma.setDisable(false);
                        this.sumita = null;
                    }
                }
        
                if(restita != null)
                {
                    if(acuses.get(e_restita).size() < 1)
                    {
                    conexion(("2 "+ e_restita + " "+huella +" "+ restita),'-');
                    System.out.println("Servicio de Resta no disponible");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    conexion(("99 "+ e_restita + " "+huella +" "+ 1),'+');
                    }
                    else
                    {
                        acuses.remove(e_restita);
                        resta.setDisable(false);
                        this.restita = null;
                    }
                    
                }
                if(multita != null)
                {
                    if(acuses.get(e_multita).size() < 1)
                    {
                    conexion(("3 "+ e_multita + " "+huella +" "+ multita),'*');
                    System.out.println("Servicio de Multiplica no disponible");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    conexion(("99 "+ e_multita + " "+huella +" "+ 1),'+');
                    }
                    else
                    {
                        acuses.remove(e_multita);
                        multiplica.setDisable(false);
                        this.multita = null;
                    }
                    
                }
                if(divita != null)
                {
                    if(acuses.get(e_divita).size() < 1)
                    {
                    conexion(("4 "+ e_divita + " "+huella +" "+ divita),'/');
                    System.out.println("Servicio de Divide no disponible");
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    conexion(("99 "+ e_divita + " "+huella +" "+ 1),'+');
                    }
                    else
                    {
                        acuses.remove(e_divita);
                        divide.setDisable(false);
                        this.divita = null;
                    }
                    
                }
                try
                {
                    Thread.sleep(1000);
                }catch(InterruptedException e)
                {}
            }
        }
    }
    public class HiloRecibir implements Runnable{

        ServerSocket A;
        public HiloReenviar hilo;
        public HiloRecibir(ServerSocket Creado) {
            this.A= Creado;
            this.hilo = new HiloReenviar();
            
        }

        
        @Override
        public void run() {
            Thread t = new Thread(this.hilo);
            t.start();
            
            while(true)
            {
                
                Socket elsocket;
                
                try{
                    System.out.println("Se acaba de conectar "+A.getLocalPort());
                    elsocket = A.accept();
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                   
                    String m = in.readUTF();
                    for(String Mensaje:m.split("\n")){
                    System.out.println(Mensaje);
                    
                        String[] arrSplit = Mensaje.split(" ");
                if(Integer.parseInt(arrSplit[0]) >= 5)
                {
                    if(Double.parseDouble(arrSplit[3])== 0D)
                    {
                        continue;
                    }
                    String Total ="";
                    switch (arrSplit[0])
                    {
                        case "5":
                            
                            String evento = eventos.get(arrSplit[1]);
                            if(evento != null)
                            {
                            String[] operacion = evento.split(":");
                            
                            Total = operacion[0] +" + "+  operacion[1]+ " = " + arrSplit[3];                        
                            ResultadoSuma.appendText(Total + "\n");
                            }
                            
                            break;
                        case "6":
                            
                            String evento2 = eventos.get(arrSplit[1]);
                            if(evento2 != null)
                            {
                            String[] operacion2 = evento2.split(":");
                            
                            Total = operacion2[0] +" - "+  operacion2[1]+ " = " + arrSplit[3];
                            ResultadoResta.appendText(Total+ "\n");
                            }
                            break;
                        case "7":
                            
                            String evento3 = eventos.get(arrSplit[1]);
                            if(evento3 != null)
                            {
                            String[] operacion3 = evento3.split(":");
                           
                            Total = operacion3[0] +" * "+  operacion3[1]+ " = " + arrSplit[3];
                            ResultadoMultiplicacion.appendText(Total+ "\n");
                            }
                            break;
                        case "8":
                            
                            String evento4 = eventos.get(arrSplit[1]);
                            if(evento4 != null)
                            {
                            String[] operacion4 = evento4.split(":");
                            
                            Total = operacion4[0] +" / "+  operacion4[1]+ " = " + arrSplit[3];
                            ResultadoDivision.appendText(Total+ "\n");
                            }
                            break;
                         case "66":
                            if(acuses.containsKey(arrSplit[1]))
                            {
                                if(!acuses.get(arrSplit[1]).contains(arrSplit[2]))
                                {
                                acuses.get(arrSplit[1]).add(arrSplit[2]);
                                Integer cuenta = acuses.get(arrSplit[1]).size();
                                System.out.println("Numero total de acuses :"+cuenta);
                                System.out.println(Mensaje);
                                }   
                            }
                            
                            break;
                         case "99":
                            if(acuses.containsKey(arrSplit[1]))
                            {
                                if(!acuses.get(arrSplit[1]).contains(arrSplit[2]))
                                {
                                acuses.get(arrSplit[1]).add(arrSplit[2]);
                                Integer cuenta = acuses.get(arrSplit[1]).size();
                                
                                 Runtime.getRuntime().exec("java -jar C:/SPB_Data/Calculadora/Servidor/CalculadoraServidor.jar");
                                System.out.println("Clonando.. ");
                                
                                }   
                            }
                          
                            break;
                    }
                }
                    
                    
                    }
                    elsocket.close();
                    
                } catch (IOException ex) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex1) {
                    }
                    System.out.println("Fallo la conexion");
                }
                
                
                
            }
            
        }

    }
    
}



