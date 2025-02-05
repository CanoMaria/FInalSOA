package ar.edu.iua.iw3.modelo.cache;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.edu.iua.iw3.modelo.Historico;
import net.spy.memcached.MemcachedClient;

@Component
public class Memcache{

private   MemcachedClient mcc;


    public Memcache( @Value("${conexioncache.nombre}") String nombreServicio, @Value("${conexioncache.puerto}") Integer puerto) throws IOException {

        this.mcc = new MemcachedClient(new InetSocketAddress(nombreServicio,puerto));
        // Connecting to Memcached server on localhost
}

    public String buscar(String id){
        String dato = "";
        dato = (String) mcc.get(id);
        return mcc.get(id) == null ? null : dato;
    }



    public boolean agregar(Historico historico,String key, int tiempo){
        return  mcc.add(key, tiempo, historico.getJson(historico)).isDone();
    }


    /*public boolean actualizar(Historico historico,int tiempo){
        return  mcc.set(String.valueOf(historico.getId_historico()), tiempo, historico.getJson(historico)).isDone();
    }*/
    public boolean actualizar(Historico historico, int tiempo) {
        // Utilizamos el identificador como la clave
        String key = String.valueOf(historico.getIdentificador());
        return mcc.set(key, tiempo, historico.getJson(historico)).isDone();
    }



    public boolean eliminar(Long id){
        return mcc.delete(String.valueOf(id)).isDone();
    }
}