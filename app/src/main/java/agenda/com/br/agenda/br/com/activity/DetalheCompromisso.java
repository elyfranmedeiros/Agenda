package agenda.com.br.agenda.br.com.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.List;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.DAO.CompromissoDAO;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

public class DetalheCompromisso extends ActionBarActivity {

    private CompromissoDAO dao;
    private List<Compromisso> compromissos;
    String descricao, contato, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compromisso_detalhe);

        /*dao = new CompromissoDAO(this);
        compromissos = dao.buscaCompromisso();
        dao.close();*/

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView txtDesc = (TextView) findViewById(R.id.txtDescricao);
        TextView txtCont = (TextView) findViewById(R.id.txtContato);
        TextView txtData = (TextView) findViewById(R.id.txtData);

        txtDesc.setText(bundle.getString("descricao"));
        txtCont.setText(bundle.getString("contato"));
        txtData.setText(bundle.getString("data"));
        //teste


    }
}
