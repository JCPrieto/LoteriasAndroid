package com.jklabs.loteriasandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import com.jklabs.loteriasandroid.R.id;
import com.jklabs.loteriaselpais.Conexion;
import com.jklabs.loteriaselpais.ResultadosNino;

import java.util.Timer;
import java.util.TimerTask;

public class ResumenNino extends Activity {

	private ResultadosNino res;

	/**
	 * Actualizamos los valores de las etiquetas en funcion del resultado de la
	 * consulta.
	 */
	private void actualizarValores() {
		TextView primero = (TextView) this.findViewById(id.valorPrimero);
		primero.setText(res.getPrimero());
		TextView segundo = (TextView) this.findViewById(id.valorSegundo);
		segundo.setText(res.getSegundo());
		TextView tercero = (TextView) this.findViewById(id.valorTercero);
		tercero.setText(res.getTercero());
		TextView ext4 = (TextView) this.findViewById(id.valorExt4);
		ext4.setText(trataArray(res.getExtraccionCuatro()));
		TextView ext3 = (TextView) this.findViewById(id.valorExt3);
		ext3.setText(trataArray(res.getExtraccionTres()));
		TextView ext2 = (TextView) this.findViewById(id.valorExt2);
		ext2.setText(trataArray(res.getExtraccionDos()));
		TextView reintegro = (TextView) this.findViewById(id.valorReintegro);
		reintegro.setText(trataArray(res.getReintegros()));
		TextView estado = (TextView) this.findViewById(id.estado);
		estado.setText(res.getEstado());
		TextView fecha = (TextView) this.findViewById(id.fecha);
		fecha.setText("Ultima actualización:" + res.getFecha());
		TextView pdf = (TextView) this.findViewById(id.pdf);
		if (res.getPDF().length() > 0) {
			pdf.setText(Html.fromHtml("<a href=\"" + res.getPDF()
					+ "\">Descargar PDF con los resultados de Sorteo</a>"));
			pdf.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}

	private void cargarDatos() {
		Conexion c = new Conexion("Nino", "resumen");
		if (c.consulta()) {
			res = new ResultadosNino(c.getResultado());
			actualizarValores();
		}
	}

	/**
	 * Establecemos el layout.
	 * <p>
	 * Comprobamos la version del SDK del terminal, para que permita la conexion
	 * con el servidor.
	 * <p>
	 * Cargamos un temporizador para que cada 30 segundos refresque los
	 * resultados.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resumen_nino);
		// Show the Up button in the action bar.
		setupActionBar();
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Timer t = new Timer();

		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						cargarDatos();
					}

				});
			}

		}, 0, 30000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.resumen_nino, menu);
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
			try {
				getActionBar().setDisplayHomeAsUpEnabled(true);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Convierte un array de string en una cadena
	 * 
	 * @param extraccionTres
	 *            Array que contiene los numeros
	 * @return String con los numeros
	 */
	private CharSequence trataArray(String[] extraccionTres) {
		String res = "";
		for (int i = 0; i < extraccionTres.length - 1; i++) {
			res = res.concat(extraccionTres[i] + ", ");
		}
		res = res.concat(extraccionTres[extraccionTres.length - 1]);
		return res;
	}
}
