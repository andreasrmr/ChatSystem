package com.company.threads;

public class Test {

    public static void main(String[] args) throws InterruptedException{
/*
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
    //code in thread

            }
        });
*/

        Thread thread = new Thread(() -> {
            //code in thread:
            System.out.println("We are now in thread " + Thread.currentThread().getName());
            System.out.println("Current thread priority is " + Thread.currentThread().getPriority());

            try{
                Thread.sleep(15000);
                System.out.println("new worker thread slept for 15000");
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        });

        //giv navn
        thread.setName("New Worker Thread");

        //set priority
        thread.setPriority(Thread.MAX_PRIORITY);


        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");
        Thread.sleep(10000);
        System.out.println("Main thread slept for 10 and exited now");
    }
}

