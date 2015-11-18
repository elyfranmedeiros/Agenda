package agenda.com.br.agenda.br.com.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import agenda.com.br.agenda.R;
import agenda.com.br.agenda.br.com.modelo.Compromisso;

/**
 * Created by great on 18/11/2015.
 */
public class CompromissoHelper {

    private final TextView pDisplayDate;
    private final EditText campDescricao;
    private final Button btContato;
    private final TextView txtContato;
    private Compromisso compromisso;

    public CompromissoHelper(CadastroCompromisso activity) {
        compromisso = new Compromisso();

        pDisplayDate = (TextView) activity.findViewById(R.id.displayDate);
        campDescricao = (EditText) activity.findViewById(R.id.btndescricao);
        btContato = (Button) activity.findViewById(R.id.btnCont);
        txtContato = (TextView) activity.findViewById(R.id.displayCont);
        txtContato.setVisibility(View.GONE);
    }

    public Compromisso getCompromisso(){
        compromisso.setDescricao(campDescricao.getText().toString());
        compromisso.setContato(txtContato.getText().toString());
        compromisso.setData(pDisplayDate.getText().toString());

        return compromisso;
    }

    public void preencheDadosDoCompromisso(Compromisso compromisso){
        campDescricao.setText(compromisso.getDescricao());
        txtContato.setText(compromisso.getContato());
        pDisplayDate.setText(compromisso.getData());
    }




}
