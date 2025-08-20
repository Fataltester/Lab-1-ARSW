/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{
    private int A = 0;
    private int B = 0;
    public  void counting(int a,int b){
        CountThread thread = new CountThread();
        A = a;
        B = b;
    }

    public void run(){
        Timer timer = new Timer();
        long tiempoInicio = System.currentTimeMillis();
        System.out.println("Tiempo inicial(ms): "+ tiempoInicio);
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        while(A <= B){
            System.out.println(A);
             A += 1;
        }
        System.out.println("Terminado..");
        System.out.println("Tiempo Final(ms): "+ tiempoTranscurrido);
        timer.cancel();
    }
        
}
