package agenda.com.br.agenda.br.com.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.DAO.CompromissoDAO;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

public class CadastroCompromisso extends Activity {

    private TextView pDisplayDate;
    private Button pPickDate;
    private int pYear;
    private int pMonth;
    private int pDay;

    private Compromisso compromisso;
    static final int DIALOG_ID = 0;
    private EditText campDescricao;
    private EditText campContato;
    final Calendar cal = Calendar.getInstance();
    private Button btnDataHora;
    private List<Compromisso> compromissos;

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

        Intent intent = getIntent();
        compromisso = (Compromisso) intent.getSerializableExtra("compromisso");
        if(compromisso != null) {
            campDescricao.setText(compromisso.getDescricao());
            campContato.setText(compromisso.getContato());
            pDisplayDate.setText(compromisso.getData());
        }
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompromissoDAO dao = new CompromissoDAO(CadastroCompromisso.this);
                compromisso.setDescricao(campDescricao.getText().toString());
                compromisso.setContato(campContato.getText().toString());
                compromisso.setData(pDisplayDate.getText().toString());
                if(compromisso.getId() != null){
                    dao.editar(compromisso);
                }else{
                    dao.insere(compromisso);
                }
                dao.close();
                Intent intent = new Intent(CadastroCompromisso.this, ListaDeCompromissos.class);
                startActivity(intent);
            }
        });
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