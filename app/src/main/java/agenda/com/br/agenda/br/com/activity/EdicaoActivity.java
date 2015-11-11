package agenda.com.br.agenda.br.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.DAO.CompromissoDAO;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

/**
 * Created by great on 11/11/2015.
 */
public class EdicaoActivity extends Activity {

    private Compromisso compromisso;
    EditText txtDesc;
    EditText txtCont;
    TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_compromisso);

        txtDesc = (EditText) findViewById(R.id.btndescricao);
        txtCont = (EditText) findViewById(R.id.btncontato);
        txtData = (TextView) findViewById(R.id.displayDate);

        Intent intent = getIntent();
        compromisso = (Compromisso) intent.getSerializableExtra("compromisso");
        if(compromisso != null){
            preencheCompromisso(compromisso);
        }
        Button btnAtt = (Button) findViewById(R.id.btnAtt);
        btnAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompromissoDAO compromissoDAO = new CompromissoDAO(EdicaoActivity.this);
                preencheCompromisso(compromisso);
                compromissoDAO.editar(compromisso);
                compromissoDAO.close();

                Intent intent1 = new Intent(EdicaoActivity.this, ListaDeCompromissos.class);
                startActivity(intent1);
            }
        });
    }

    private void preencheCompromisso(Compromisso compromisso) {
        txtDesc.setText(compromisso.getDescricao());
        txtCont.setText(compromisso.getContato());
        txtData.setText(compromisso.getData());
        this.compromisso = compromisso;
    }


}
