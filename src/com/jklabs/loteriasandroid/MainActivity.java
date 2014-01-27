package com.jklabs.loteriasandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jklabs.loteriasandroid.R.id;

public class MainActivity extends Activity {

	public void cargarBuscarNavidad(View view) {
		Intent intent = new Intent(this, Busqueda.class);
		intent.putExtra("Tipo", "Navidad");
		startActivity(intent);
	}

	public void cargarBuscarNino(View view) {
		Intent intent = new Intent(this, Busqueda.class);
		intent.putExtra("Tipo", "Nino");
		startActivity(intent);
	}

	public void cargarResumenNavidad(View view) {
		Intent intent = new Intent(this, ResumenNavidad.class);
		startActivity(intent);
	}

	public void cargarResumenNino(View view) {
		Intent intent = new Intent(this, ResumenNino.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case id.action_settings:
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				TextView mensaje = new TextView(this);
				mensaje.setText(Html.fromHtml(this.getString(R.string.acercaDe)));
				mensaje.setMovementMethod(LinkMovementMethod.getInstance());
				alertbox.setTitle(this.getString(R.string.titulo));
				alertbox.setView(mensaje);
				alertbox.setNeutralButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
				alertbox.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
