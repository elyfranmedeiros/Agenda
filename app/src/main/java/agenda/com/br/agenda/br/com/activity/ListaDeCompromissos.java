package agenda.com.br.agenda.br.com.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.DAO.CompromissoDAO;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

public class ListaDeCompromissos extends ActionBarActivity {

    String descricao, contato, data;
    List<Compromisso> compromissos;
    CompromissoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_compromissos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dao = new CompromissoDAO(this);
        compromissos = dao.buscaCompromisso();
        dao.close();

        final ListView listView = (ListView) findViewById(R.id.listaCompromissos);
        final ArrayAdapter<Compromisso> adapter = new ArrayAdapter<Compromisso>(this, android.R.layout.simple_list_item_1, compromissos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Compromisso compromisso = compromissos.get(position);
                Intent intent = new Intent(ListaDeCompromissos.this, DetalheCompromisso.class);
                Bundle bundle = new Bundle();
                bundle.putString("descricao", compromisso.getDescricao());
                bundle.putString("contato", compromisso.getContato());
                bundle.putString("data", compromisso.getData());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button btnNovoContato = (Button) findViewById(R.id.btnNovoContato);
        btnNovoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaDeCompromissos.this, CadastroCompromisso.class);
                startActivity(intent);
            }
        });


    }
    private void exibeCompromisso(Compromisso compromisso) {

        StringBuilder str = new StringBuilder();
        str.append("Descrição: " + compromisso.getDescricao() + "\n");
        str.append("Data: " + compromisso.getData() +"\n");
        str.append("Contato: " + compromisso.getContato() + "\n");
        AlertDialog.Builder msg = new AlertDialog.Builder(ListaDeCompromissos.this);
        msg.setTitle("Detalhes do Compromisso");
        msg.setMessage(str.toString());
        msg.setNeutralButton("OK", null);
        msg.show();
    }
}
