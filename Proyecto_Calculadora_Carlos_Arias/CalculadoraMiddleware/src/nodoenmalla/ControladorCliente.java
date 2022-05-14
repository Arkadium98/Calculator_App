
package nodoenmalla;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorCliente  implements Runnable{
    
    public ServerSocket EscuchaCliente;
    int puerto_I;
    int Cliente;

    public ControladorCliente(int Num_Nodo, int puerto_I, int Cliente) {
        try {
             this.EscuchaCliente = new ServerSocket(Num_Nodo);
         } catch (IOException ex) {
             Logger.getLogger(ControladorNodo.class.getName()).log(Level.SEVERE, null, ex);
         }
        this.puerto_I = puerto_I;
        this.Cliente = Cliente;
    }
    
    @Override
    public void run() {
        System.out.println("Yo escucho al cliente en "+ Cliente);
        while(true)
        {
             Socket elsocket;
             String Mensaje="";
            try{
               System.out.println("Se acaba de conectar el Cliente "+EscuchaCliente.getLocalPort());
               elsocket = EscuchaCliente.accept();
               DataInputStream in = new DataInputStream(elsocket.getInputStream());
               DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
               Mensaje = in.readUTF();
               System.out.println(Mensaje );
               elsocket.close();
           } catch (IOException ex) {
            System.out.println("Fallo la conexion");
           }
           String Nodos ="";
           try {
                Socket Pedir_nodos = new Socket("127.0.0.1",puerto_I);
                DataInputStream in = new DataInputStream(Pedir_nodos.getInputStream());
                DataOutputStream out = new DataOutputStream(Pedir_nodos.getOutputStream());

                out.writeUTF(Mensaje);
                Nodos = in.readUTF();
                System.out.println(Nodos);
                Pedir_nodos.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexion");
            }
           String[] Nodo = Nodos.split(" ");
           for (int  i = 0; i < Nodo.length;i++)
           {
                try {
                    Socket Enviar = new Socket("127.0.0.1",Integer.parseInt(Nodo[i]));
                    DataOutputStream outE = new DataOutputStream(Enviar.getOutputStream());

                    outE.writeUTF(Mensaje);
                    Enviar.close();

                } catch (IOException ex) {
                    System.out.println("Fallo, error en la conexion");
                }
                
           }
        }
    }
    
}
