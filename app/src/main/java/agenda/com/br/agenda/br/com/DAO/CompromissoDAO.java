package agenda.com.br.agenda.br.com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import agenda.com.br.agenda.br.com.modelo.Compromisso;

/**
 * Created by great on 05/11/2015.
 */
public class CompromissoDAO extends SQLiteOpenHelper {

    public CompromissoDAO(Context context) {
        super(context, "Compromisso", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Compromisso (id INTEGER PRIMARY KEY, descricao TEXT NOT NULL, data DATE NOT NULL, contato TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Compromisso";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Compromisso compromisso) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = preencheCompromisso(compromisso);

        db.insert("Compromisso", null, values);
    }

    public List<Compromisso> buscaCompromisso() {
        String sql = "SELECT * FROM Compromisso";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Compromisso> compromissos = new ArrayList<Compromisso>();

        while(cursor.moveToNext()){
            Compromisso compromisso = new Compromisso();
            compromisso.setId(cursor.getLong(cursor.getColumnIndex("id")));
            compromisso.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            compromisso.setData(cursor.getString(cursor.getColumnIndex("data")));
            compromisso.setContato(cursor.getString(cursor.getColumnIndex("contato")));

            compromissos.add(compromisso);
        }
        cursor.close();
        return compromissos;
    }

    public void deleteCompromisso(Compromisso compromisso) {
        SQLiteDatabase db = getWritableDatabase();
        String id = String.valueOf(compromisso.getId());
        String[] params = {id};
        db.delete("Compromisso", "id = ?", params);
    }

    public void editar(Compromisso compromisso) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = preencheCompromisso(compromisso);
        String[] params = {String.valueOf(compromisso.getId())};

        db.update("Compromisso", values, "id = ?", params);
    }

    @NonNull
    private ContentValues preencheCompromisso(Compromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put("descricao", compromisso.getDescricao());
        values.put("data", compromisso.getData());
        values.put("contato", compromisso.getContato());
        return values;
    }

}
