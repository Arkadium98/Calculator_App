
package calculadoraservidor;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
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
public class RecibirDatos implements Runnable{
        
    public ServerSocket SocketServidor;

    public RecibirDatos(ServerSocket ports) throws IOException {
        this.SocketServidor = ports;
    }

    @Override
    public void run() {
         while(true)
        {
            Socket elsocket;
             try {
                 //Avisamos que el socket esta conectado
                System.out.println("Se acaba de conectar "+SocketServidor.getLocalPort());
                elsocket = SocketServidor.accept();
                DataInputStream in = new DataInputStream(elsocket.getInputStream());
                DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
                
                // Recojemos los mensajes y los imprimimos
                String Mensaje = in.readUTF();
                System.out.println(Mensaje);
                //System.out.println("66 "+huella);
                String resultado = opera(Mensaje); 
                 String[] arrSplit = Mensaje.split(" ");
                 String ACK = 66 + " "+arrSplit[1]+ " "+ huella +" " + 1;
                out.writeUTF(ACK+"\n"+resultado);
                System.out.println(ACK);
                System.out.println(resultado);
                elsocket.close();
             } catch (IOException ex) {
                 System.out.println("Fallo la conexion");
             }
        }
    }
public static String GeneraHuellaServidor()
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
   
    public static String huella = GeneraHuellaServidor(); //MD5(timestamp)
     public static String opera(String Total) throws MalformedURLException
    {
        String[] arrSplit = Total.split(" ");
        String[] operadores = arrSplit[3].split(":");
        double num1 =Double.parseDouble(operadores[0]); ////  double num1 =Double.parseDouble(arrSplit[1]);
        double num2 =Double.parseDouble(operadores[1]); ///   double num2 =Double.parseDouble(arrSplit[2])
        Total = arrSplit[0]+" " +num1+" "+num2;
       //operacion num1 num2 
        double res=0;
        String Op="";
        switch(arrSplit[0])
        {
            case "1":
                //res=num1+num2;
                try {
		File file = new File("C:/SPB_Data/Calculadora/Suma/suma.jar");
                URL url = new URL("file:" + file.getAbsolutePath());
                URLClassLoader classLoader = new URLClassLoader(new URL[] {url}, RecibirDatos.class.getClassLoader());
                Class<?> clase = classLoader.loadClass("Suma");
                for(Method m :clase.getMethods())
                {
                    if(m.getName().equals("run"))
                    {
                        res = (Double)(m.invoke(null, Total));
                        break;
                    }                    
                }

                classLoader.close();
                System.gc();
                System.out.println(res);
                 }
                catch(IOException |ClassNotFoundException | IllegalAccessException | IllegalArgumentException |  SecurityException | InvocationTargetException e) {
	         System.out.println(e);
                 
                 }
                
                Op="5";
                break;
            case "2":
                //res=num1-num2;
                
                try {
		File file = new File("C:/SPB_Data/Calculadora/Resta/resta.jar");
                URL url = new URL("file:" + file.getAbsolutePath());
                URLClassLoader classLoader = new URLClassLoader(new URL[] {url}, RecibirDatos.class.getClassLoader());
                Class<?> clase = classLoader.loadClass("Resta");
                for(Method m :clase.getMethods())
                {
                    if(m.getName().equals("run"))
                    {
                        res = (Double)(m.invoke(null, Total));
                        break;
                    }
                    
                }

                classLoader.close();
                System.gc();
                System.out.println(res);
                 }
                catch(IOException |ClassNotFoundException | IllegalAccessException | IllegalArgumentException |  SecurityException | InvocationTargetException e) {
	         System.out.println(e);
                 
                 }
                
                Op="6";
                break;
            case "3":
                //res=num1*num2;
                
                try {
		File file = new File("C:/SPB_Data/Calculadora/Multi/multi.jar");
                URL url = new URL("file:" + file.getAbsolutePath());
                URLClassLoader classLoader = new URLClassLoader(new URL[] {url}, RecibirDatos.class.getClassLoader());
                Class<?> clase = classLoader.loadClass("Multi");
                for(Method m :clase.getMethods())
                {
                    if(m.getName().equals("run"))
                    {
                        res = (Double)(m.invoke(null, Total));
                        break;
                    }
                    
                }

                classLoader.close();
                System.gc();
                System.out.println(res);
                 }
                catch(IOException |ClassNotFoundException | IllegalAccessException | IllegalArgumentException |  SecurityException | InvocationTargetException e) {
	         System.out.println(e);
                 
                 }
                
                Op="7";
                break;
            case "4":
                //res=num1/num2;
                
                try {
		File file = new File("C:/SPB_Data/Calculadora/Divi/divi.jar");
                URL url = new URL("file:" + file.getAbsolutePath());
                URLClassLoader classLoader = new URLClassLoader(new URL[] {url}, RecibirDatos.class.getClassLoader());
                Class<?> clase = classLoader.loadClass("Divi");
                for(Method m :clase.getMethods())
                {
                    if(m.getName().equals("run"))
                    {
                        res = (Double)(m.invoke(null, Total));
                        break;
                    }
                    
                }

                classLoader.close();
                System.gc();
                System.out.println(res);
                 }
                catch(IOException |ClassNotFoundException | IllegalAccessException | IllegalArgumentException |  SecurityException | InvocationTargetException e) {
	         System.out.println(e);
                 
                 }
                
                Op="8";
                break;
            default:
                res=num1;
                break;
        }
        
        
        // operacion num1 num2 resultado
        String Res = Op + " "+arrSplit[1]+ " "+ huella +" " + res; //String Res = Op + " "+arrSplit[1]+ " "+ arrSplit[2] +" " + res;
        return Res;  
    }

}
