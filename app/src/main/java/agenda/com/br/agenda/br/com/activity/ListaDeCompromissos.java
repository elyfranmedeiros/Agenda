package agenda.com.br.agenda.br.com.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
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
    private ListView listCompromissos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_compromissos);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaCompromisso();
        listCompromissos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        registerForContextMenu(listCompromissos);

    }

    private void carregaCompromisso() {
        dao = new CompromissoDAO(this);
        compromissos = dao.buscaCompromisso();
        dao.close();
        listCompromissos = (ListView) findViewById(R.id.listaCompromissos);
        ArrayAdapter<Compromisso> adapter = new ArrayAdapter<Compromisso>(this, android.R.layout.simple_list_item_1, compromissos);
        listCompromissos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDel = menu.add("Deletar");
        menuDel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Compromisso compromisso = (Compromisso) listCompromissos.getItemAtPosition(info.position);

                CompromissoDAO dao = new CompromissoDAO(ListaDeCompromissos.this);
                dao.deleteCompromisso(compromisso);
                dao.close();
                carregaCompromisso();
                return false;
            }
        });

        MenuItem menuEdit = menu.add("Editar");
        menuEdit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Compromisso compromisso = (Compromisso) listCompromissos.getItemAtPosition(info.position);

                Intent intent = new Intent(ListaDeCompromissos.this, CadastroCompromisso.class);
                intent.putExtra("compromisso", compromisso);
                startActivity(intent);

                return false;
            }
        });


    }
}
