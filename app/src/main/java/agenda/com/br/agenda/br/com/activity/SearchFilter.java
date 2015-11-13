package agenda.com.br.agenda.br.com.activity;

import android.support.v7.app.ActionBarActivity;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by great on 12/11/2015.
 */
public class SearchFilter extends ActionBarActivity implements SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        //mensagem(s);
        return false;
    }

    public void mensagem(String query) {
        // mostrando a mensagem na tela
    }
}
