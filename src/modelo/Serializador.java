package modelo;

import java.io.*;
import java.util.HashMap;

public class Serializador implements Serializable{
    /**
     * Serializa el objeto recibido como parametro en el archivo recibido.
     * @param nombreArchivo: nombre del archivo a escribir
     * @param objetoASerializar : objeto a serializar
     * @return void
     **/
    public void escribir(String nombreArchivo, Object objetoASerializar){
        try {
            FileOutputStream fos = new FileOutputStream(nombreArchivo);
            var oos = new ObjectOutputStream(fos);
            oos.writeObject(objetoASerializar);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lee del archivo recibido como parametro un objeto serializado.
     * @param nombreArchivo: nombre del archivo a leer
     * @return Object: objeto recuperado del archivo
     **/
    public Object leer(String nombreArchivo){
        try {
            FileInputStream fos = new FileInputStream(nombreArchivo);
            var oos = new ObjectInputStream(fos);
            var obj = oos.readObject();
            fos.close();
            return obj;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
