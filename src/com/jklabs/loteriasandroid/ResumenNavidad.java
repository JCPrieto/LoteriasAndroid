package com.jklabs.loteriasandroid;

import java.util.Timer;
import java.util.TimerTask;

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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.jklabs.loteriasandroid.R.id;
import com.jklabs.loteriaselpais.Conexion;
import com.jklabs.loteriaselpais.ResultadosNavidad;

public class ResumenNavidad extends Activity {

	private ResultadosNavidad res;

	private void actualizarValores() {
		// TODO Auto-generated method stub
		TextView gordo = (TextView) this.findViewById(id.valorGordo);
		gordo.setText(res.getGordo());
		TextView segundo = (TextView) this.findViewById(id.valorSegundoNavidad);
		segundo.setText(res.getSegundo());
		TextView tercero = (TextView) this.findViewById(id.valorTercero);
		tercero.setText(res.getTercero());
		TextView cuarto = (TextView) this.findViewById(id.valorCuarto);
		cuarto.setText(res.getCuarto1() + " - " + res.getCuarto2());
		TextView quinto = (TextView) this.findViewById(id.valorQuinto);
		quinto.setText(res.getQuinto1() + " - " + res.getQuinto2() + " - "
				+ res.getQuinto3() + " - " + res.getQuinto4() + " - "
				+ res.getQuinto5() + " - " + res.getQuinto6() + " - "
				+ res.getQuinto7() + " - " + res.getQuinto8());
		TextView estado = (TextView) this.findViewById(id.estadoSorteoNavidad);
		estado.setText(res.getEstado());
		TextView fecha = (TextView) this
				.findViewById(id.actualizacionSorteoNavidad);
		fecha.setText("Ultima actualización:" + res.getFecha());
		TextView pdf = (TextView) this.findViewById(id.pdfSorteoNavidad);
		if (res.getPDF().length() > 0) {
			pdf.setText(Html.fromHtml("<a href=\"" + res.getPDF()
					+ "\">Descargar PDF con los resultados de Sorteo</a>"));
			pdf.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}

	private void cargarDatos() {
		// TODO Auto-generated method stub
		Conexion c = new Conexion("Navidad", "resumen");
		while (!c.consulta()) {
		}
		res = new ResultadosNavidad(c.getResultado());
		actualizarValores();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resumen_navidad);
		// Show the Up button in the action bar.
		setupActionBar();
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		final ProgressBar progress = (ProgressBar) this
				.findViewById(id.progressBar2);
		final TextView actu = (TextView) this
				.findViewById(id.actalizacionNavidad);
		Timer t = new Timer();

		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progress.setVisibility(View.GONE);
						actu.setText("Actualizando Datos");
						cargarDatos();
						progress.setVisibility(View.INVISIBLE);
						actu.setText("");
					}

				});
			}

		}, 0, 30000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.resumen_navidad, menu);
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

}
