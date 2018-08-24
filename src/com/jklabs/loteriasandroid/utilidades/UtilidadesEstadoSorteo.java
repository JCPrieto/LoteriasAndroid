package com.jklabs.loteriasandroid.utilidades;

import es.jklabs.lib.loteria.enumeradores.EstadoSorteo;

public class UtilidadesEstadoSorteo {

    public static String getHumanReadable(EstadoSorteo estado) {
        String retorno;
        switch (estado) {
            case NO_INICIADO:
                retorno = "El sorteo aun no ha comenzado";
                break;
            case EN_PROCESO:
                retorno = "El sorteo esta en proceso";
                break;
            case TERMINADO_PROVISIONAL:
                retorno = "El sorteo ha concluido. Los resultados aun son provisionales";
                break;
            default:
                retorno = "El sorteo ha concluido y los resultados se basan el la lista oficial";
                break;
        }
        return retorno;
    }
}
