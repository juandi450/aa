package com.bocio23.dam.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.bocio23.dam.broadcast.POJO.LLamada;

import java.util.Calendar;
import java.util.Date;

public class ReceptorEstadoTelefono extends BroadcastReceiver {
    

   // -----------------



    private static int lastState=TelephonyManager.CALL_STATE_IDLE;
    private  static String callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;
    private static String typeCall;
    private static LLamada llamada;

    @Override
    public void onReceive(final Context context, Intent intent) {

        //si la accion que se llama es una nueva llamada saliente
        //guardamos los datos en savedNumber
        if (intent.getAction().equals("OUTGOING_CALL_ACTION")) {
            savedNumber = intent.getExtras().getString("INTENT_PHONE_NUMBER");
            Log.d("zzzdd","llamada saliente tipo: "+typeCall);
            typeCall="llamada saliente";

        } else {
            typeCall="llamada entrante";
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;

            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state=TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context, state, number,typeCall);
            //System.out.println("llamada desde clase principal"+llamada.toString());
        }

    }



    public void onCallStateChanged(Context context, int state, String number,String typeCall) {
        if(lastState == state){
            //No cambios
            return;
        }
        switch (state) {
            case 1:
                /*
                en caso de estado 1 es tono de llamada
                 */
                //isIncoming pasa a valer true
                isIncoming = true;
                callStartTime = new Date().toString();
                savedNumber = number;
                llamada=new LLamada(number,callStartTime,typeCall,new Date().toString());
                System.out.println(llamada.toString());
                break;

            case 2:
                /*
                en caso de 2 que es descolgar si el estado anterior es distinto de tono entonces
                entra en el bucle en le que cambiamos iscoming a false
                 */

                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date().toString();

                }else{
                    callStartTime = new Date().toString();
                }


                break;
            case TelephonyManager.CALL_STATE_IDLE:
                /*
                en caso de estar en estado 0 es que no habido cambios o a vuelto a su estado inicial
                 */
                Log.d("AABB","entra en el case 3");
                callStartTime=new Date().toString();

                //cuando el estado anterior ha sido 1 se considera como llamada perdida
                if(lastState == TelephonyManager.CALL_STATE_RINGING){


                    String date=new Date().toString();
                    typeCall="llamada perdida";
                    llamada=new LLamada(number,callStartTime,typeCall,date);
                    Intent intent=new Intent(context,ServicioClienteRest.class);
                    intent.putExtra("number",llamada.getNumber());
                    intent.putExtra("type",llamada.getState_call());
                    intent.putExtra("date",llamada.getDate());
                    intent.putExtra("callend",llamada.getEndCall());


                    context.startService(intent);
                    System.out.println(llamada.toString());
                }

                //si no, en caso de que isIncoming sera true se trata de una llamada entrante
                else if(isIncoming){

                    String date=new Date().toString();
                    typeCall="llamada entrante";

                    llamada=new LLamada(number,callStartTime,typeCall,date);
                    Intent intent=new Intent(context,ServicioClienteRest.class);
                    intent.putExtra("number",llamada.getNumber());
                    intent.putExtra("type",llamada.getState_call());
                    intent.putExtra("date",llamada.getDate());
                    intent.putExtra("callend",llamada.getEndCall());


                    context.startService(intent);
                    System.out.println(llamada.toString());

                }
                //si no es que isIncoming es false y es una llamada saliente
                else{

                    String date=new Date().toString();
                    typeCall="llamada saliente";

                    llamada=new LLamada(number,callStartTime,typeCall,date);
                    Intent intent=new Intent(context,ServicioClienteRest.class);
                    intent.putExtra("number",llamada.getNumber());
                    intent.putExtra("type",llamada.getState_call());
                    intent.putExtra("date",llamada.getDate());
                    intent.putExtra("callend",llamada.getEndCall());


                    context.startService(intent);
                    System.out.println(llamada.toString());
                }


                break;

        }

        //guarmamos el estado anterior
        lastState = state;
    }

        //cuando se produzca un cambio en el estdo de llamada se ejecuta la siguiente metodo
       /* final TelephonyManager tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

                tm.listen(new PhoneStateListener() {
                        public void onCallStateChanged(int state,String number) {
                            //me llega el estado y el numero de telefono
                            Log.d("zzdd",state+" "+number);

                            //creamos el intente que le pasamos al servicio
                            //esta mal porque nos envia muchos registro de una llamada
                            Intent intent = new Intent(context, ServicioClienteRest.class);
                            intent.putExtra("number", number);
                            intent.putExtra("state",state);

                            context.startService(intent);
                                /*if (state == android.telephony.TelephonyManager.CALL_STATE_RINGING) {
                                    //number
                                }
                        }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }*/
}

