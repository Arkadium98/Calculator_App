package calculadoraservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//Agregado
import java.io.File;
//        


public class CalculadoraServidor {

    public static void main(String[] args) throws IOException {
        //Creamos un arreglo para guardar los numeros asignados a los puetos
        int puertos[] = new int[1000];
        //Le asignamos los puertos 7000 --> 7999 para los sockets
        for(int i=0;i<puertos.length; i++)
        {
            puertos[i]=i+7000;
        }
        //Creamos los sockets del servidor
        ServerSocket Servidor = create(puertos);
        
        String mensaje="";
        final String HOST ="127.0.0.1";
        int PUERTO =8000;
        DataInputStream in;
        DataOutputStream out;
        var Buscando=true;
        while(Buscando){
            try {
                //Creamos un servidor para el cliente
                Socket elsocket = new Socket(HOST,PUERTO);
                
                in = new DataInputStream(elsocket.getInputStream());
                out = new DataOutputStream(elsocket.getOutputStream());

                //Mandamos el saludo al middleware
                out.writeUTF("Servidor "+Servidor.getLocalPort());

                //Recojemos el mensaje del middleware
                mensaje = in.readUTF(); //"Se acaba de conectar"
                //Imprimimos el mensaje recibido
                System.out.println(mensaje);
                //Sí el mensaje es distinto a cero, ya nos pudimos conectar
                if (Integer.parseInt(mensaje) !=0)
                {
                    System.out.println(PUERTO);
                    System.out.println(mensaje);
                    Buscando = false;
                    break;
                }
                //Sino pudimos conectarnos, cerramos el socket
                elsocket.close();

            } catch (IOException ex) {
                //Mandamos un mensaje para avisar que no se conectar
                System.out.println("Fallo, error en la conexion");
            }
            // Le sumamos a 200 al numero de pueto para cambiar de puerto
            PUERTO=PUERTO+200;
            //Sí el puerto es mayor a 60000, terminamos el ciclo while
            if (PUERTO>60000)
            {
                Buscando = false;
            }
        }
        //Se crea un hilo para recibir los datos del cliente
        RecibirDatos Serv= new RecibirDatos(Servidor);
        int mipuerto = Serv.SocketServidor.getLocalPort();
        Thread llegada = new Thread(Serv);
        
        llegada.start(); 
        
        
    }
    //En esta funcion se crean los sockets del servidor
    static public ServerSocket create(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; 
            }
        }

        
        throw new IOException("No se encontro ningun puerto libre");
    }
    
}


//------------------------------------------------------



