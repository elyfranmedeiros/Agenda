package agenda.com.br.agenda.br.com.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.DAO.CompromissoDAO;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

public class CadastroCompromisso extends ActionBarActivity {

    static final int DIALOG_ID = 0;
    private static final int REQUEST_SELECT_CONTACT = 0;
    public static final String VAZIO = "";

    private TextView pDisplayDate;
    private int pYear;
    private int pMonth;
    private int pDay;
    private Compromisso compromisso;
    private EditText campDescricao;
    private Button btContato;
    final Calendar cal = Calendar.getInstance();
    private Button btnDataHora;
    private String personName;
    private String numeroContato;
    private TextView txtContato;
    private CompromissoHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_compromisso);
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
        this.compromisso = new Compromisso();
        this.helper = new CompromissoHelper(this);

        btnDataHora = (Button) findViewById(R.id.btnDataHora);
        btnDataHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

        pDisplayDate = (TextView) findViewById(R.id.displayDate);
        campDescricao = (EditText) findViewById(R.id.btndescricao);
        btContato = (Button) findViewById(R.id.btnCont);
        txtContato = (TextView)findViewById(R.id.displayCont);
        txtContato.setVisibility(View.GONE);

        btContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContato = new Intent(Intent.ACTION_PICK);
                intentContato.setType(ContactsContract.Contacts.CONTENT_TYPE);

                if (intentContato.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intentContato, REQUEST_SELECT_CONTACT);
                }
            }
        });

        compromisso = recuperaCompromissoSelecionado();
        if (compromisso != null) {
            helper.preencheDadosDoCompromisso(compromisso);
        }
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompromissoDAO dao = new CompromissoDAO(CadastroCompromisso.this);
                helper = new CompromissoHelper(CadastroCompromisso.this);
                Compromisso compromisso = helper.getCompromisso();
                if (VAZIO.equals(compromisso.getDescricao())) {
                    Toast.makeText(CadastroCompromisso.this, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show();
                }else {
                    if (compromisso.getId() != null) {
                        dao.editar(compromisso);
                    } else {
                        dao.insere(compromisso);
                    }
                    dao.close();
                    Intent intent = new Intent(CadastroCompromisso.this, ListaDeCompromissos.class);
                    startActivity(intent);
                }
            }
        });

        Button btnList = (Button)findViewById(R.id.btnVerCompromissos);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroCompromisso.this, ListaDeCompromissos.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            Uri uri = data.getData();

            switch (requestCode){
                case REQUEST_SELECT_CONTACT:
                    getContacts(this, uri.getLastPathSegment());
                    txtContato.setText(personName + " - " + numeroContato);
                    break;
            }
        }
    }

    private void getContacts(Context context, String contactId) {
        int type = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        String[] whereArgs = new String[] { String.valueOf(type) };

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                whereArgs,
                null);

        if(cursor != null && cursor.getCount()>0) {
            try {
                while (cursor.moveToNext()) {

                    if(contactId.equals(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)))) {
                        this.numeroContato = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        this.personName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        cursor.moveToLast();
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    @NonNull
    private Compromisso recuperaCompromissoDoFormulario() {
        Compromisso compromisso = new Compromisso();
        compromisso.setDescricao(campDescricao.getText().toString());
        compromisso.setContato(txtContato.getText().toString());
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