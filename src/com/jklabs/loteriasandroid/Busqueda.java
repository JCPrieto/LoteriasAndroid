package com.jklabs.loteriasandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.jklabs.loteriasandroid.R.id;
import com.jklabs.loteriaselpais.Conexion;

public class Busqueda extends Activity {

	private String tipo;

	public void buscar(View view) {
		TextView numero = (TextView) this.findViewById(id.valorNumero);
		TextView resultado = (TextView) this.findViewById(id.resultados);
		String num = numero.getText().toString();
		numero.setText("");
		if (valido(num)) {
			Conexion c = new Conexion(tipo, num);
			if (c.consulta()) {
				com.jklabs.loteriaselpais.Busqueda dec = new com.jklabs.loteriaselpais.Busqueda(
						c.getResultado());
				resultado.append(dec.toString() + "\n");
			} else {
				resultado.append("Hay un problema con el sevidor\n");
			}
		} else {
			resultado
					.append("El numero " + num + " introducido no es válido\n");
		}
	}

	public void limpiar(View view) {
		TextView numero = (TextView) this.findViewById(id.valorNumero);
		TextView resultado = (TextView) this.findViewById(id.resultados);
		numero.setText("");
		resultado.setText("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		tipo = intent.getStringExtra("Tipo");
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.busqueda, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask(this);
				return true;
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

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	private boolean valido(CharSequence num) {
		// TODO Auto-generated method stub
		return num.length() > 0;
	}

}
