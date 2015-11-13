package agenda.com.br.agenda.br.com.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.DAO.CompromissoDAO;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

public class CadastroCompromisso extends ActionBarActivity {

    static final int DIALOG_ID = 0;

    private TextView pDisplayDate;
    private int pYear;
    private int pMonth;
    private int pDay;
    private Compromisso compromisso;
    private EditText campDescricao;
    private EditText campContato;
    final Calendar cal = Calendar.getInstance();
    private Button btnDataHora;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_compromisso);
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        this.compromisso = new Compromisso();

        btnDataHora = (Button) findViewById(R.id.btnDataHora);
        btnDataHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        pDisplayDate = (TextView) findViewById(R.id.displayDate);
        campDescricao = (EditText) findViewById(R.id.btndescricao);
        campContato = (EditText) findViewById(R.id.btncontato);

        compromisso = recuperaCompromissoSelecionado();
        if (compromisso != null) {
            campDescricao.setText(compromisso.getDescricao());
            campContato.setText(compromisso.getContato());
            pDisplayDate.setText(compromisso.getData());
        }
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompromissoDAO dao = new CompromissoDAO(CadastroCompromisso.this);
                Compromisso compromisso = preencheCompromisso();
                if (compromisso.getId() != null) {
                    dao.editar(compromisso);
                } else {
                    dao.insere(compromisso);
                }
                dao.close();
                Intent intent = new Intent(CadastroCompromisso.this, ListaDeCompromissos.class);
                startActivity(intent);
            }
        });
    }

    @NonNull
    private Compromisso preencheCompromisso() {
        Compromisso compromisso = new Compromisso();
        compromisso.setDescricao(campDescricao.getText().toString());
        compromisso.setContato(campContato.getText().toString());
        compromisso.setData(pDisplayDate.getText().toString());
        return compromisso;
    }

    private Compromisso recuperaCompromissoSelecionado() {
        Compromisso compromisso = new Compromisso();
        Intent intent = getIntent();
        compromisso = (Compromisso) intent.getSerializableExtra("compromisso");
        return compromisso;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            pYear = year;
            pMonth = monthOfYear;
            pDay = dayOfMonth;
            updateDisplay();
        }
    };

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                }
            };

    /**
     * Updates the date in the TextView
     */
    private void updateDisplay() {
        pDisplayDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append("/")
                        .append(pMonth + 1).append("/")
                        .append(pYear).append(" "));
    }

}