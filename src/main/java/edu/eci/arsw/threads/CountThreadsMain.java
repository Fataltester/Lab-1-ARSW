/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import edu.eci.arsw.threads.CountThread;
/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        CountThread thread1 = new CountThread();
        CountThread thread2 = new CountThread();
        CountThread thread3 = new CountThread();
        
        thread1.counting(0,99);
        thread2.counting(99,199);
        thread3.counting(200,299);
        
        thread1.start();
        thread2.start();
        thread3.start();
    }
    
}
