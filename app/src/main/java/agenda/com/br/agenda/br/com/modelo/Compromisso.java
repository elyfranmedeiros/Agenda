package agenda.com.br.agenda.br.com.modelo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by great on 05/11/2015.
 */
public class Compromisso implements Serializable {


    private int id;
    private String descricao;
    private String data;
    private String contato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;

    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return getId() + " - " + getDescricao() + " - " + getData();
    }
}
