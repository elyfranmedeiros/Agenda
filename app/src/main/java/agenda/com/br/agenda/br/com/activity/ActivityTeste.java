package agenda.com.br.agenda.br.com.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import agenda.com.br.agenda.R;

public class ActivityTeste extends ActionBarActivity {


    private static final int REQUEST_SELECT_CONTACT = 0;
    private TextView txt;
    private String personName;
    private String numeroContato;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_teste);

        txt = (TextView) findViewById(R.id.displayCont);
        Button btnTest = (Button) findViewById(R.id.btnCont);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContato = new Intent(Intent.ACTION_PICK);
                intentContato.setType(ContactsContract.Contacts.CONTENT_TYPE);

                if (intentContato.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intentContato, REQUEST_SELECT_CONTACT);
                }
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
                    txt.setText(personName);
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
}


