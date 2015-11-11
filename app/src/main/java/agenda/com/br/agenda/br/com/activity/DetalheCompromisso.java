package agenda.com.br.agenda.br.com.activity;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView txtDesc = (TextView) findViewById(R.id.txtReferDesc);
        TextView txtCont = (TextView) findViewById(R.id.txtReferContato);
        TextView txtData = (TextView) findViewById(R.id.txtReferDt);

        txtDesc.setText(bundle.getString("descricao"));
        txtCont.setText(bundle.getString("contato"));
        txtData.setText(bundle.getString("data"));

    }
}
