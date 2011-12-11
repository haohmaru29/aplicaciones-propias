package cl.acgp.listeners;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.acgp.run.Inicio;

public class FileMonitor implements Runnable {
	
	private static int fileCount;  
    private static Thread thread;  
    private static File[] existingFiles;  
    private static int counterCheck = 0;  
    private List<File> list;  
    private String folderToMonitor; 
    private String logFileToCreate;  
    
    public FileMonitor(String folderToMonitor, String logFileToCreate) {  
         this.folderToMonitor = folderToMonitor;  
         this.logFileToCreate = logFileToCreate;  
    }  
    
    public void run() {  
         thread = Inicio.thread;  
         Date date = new Date();  
         int currentFilecount = 0;  
         File f = new File(this.folderToMonitor);  
         File log = new File(this.logFileToCreate);  
         BufferedWriter bufferedWriter = null;  
         FileWriter fstream = null;  
         boolean status = f.isDirectory();  
         File[] currentFiles = (File[]) null;  
         if (status) {  
              currentFilecount = f.listFiles().length;  
              if (counterCheck == 0) {  
                   existingFiles = f.listFiles();  
                   fileCount = currentFilecount;  
                   counterCheck += 1;  
              }  
         } else {  
              f.mkdir();  
         }  
         if (fileCount != currentFilecount) {  
              if (fileCount > currentFilecount) {  
                   String str = date.toString() + ": Archivo borrada :"  
                             + (fileCount - currentFilecount);  
                   try {  
                        fstream = new FileWriter(log, true);  
                        bufferedWriter = new BufferedWriter(fstream);  
                        currentFiles = f.listFiles();  
                        bufferedWriter.newLine();  
                        bufferedWriter.write(str);  
                        bufferedWriter.flush();  
                        this.list = compareArray(existingFiles, currentFiles, bufferedWriter);  
                        fileCount = currentFilecount;  
                        existingFiles = currentFiles;  
                        bufferedWriter.close();  
                   } catch (IOException e) {  
                        e.printStackTrace();  
                   }  
              } else {  
                   String str1 = date.toString() + ": Archivo agregado: "  
                             + (currentFilecount - fileCount);  
                   try {  
                        fstream = new FileWriter(log, true);  
                        bufferedWriter = new BufferedWriter(fstream);  
                        currentFiles = f.listFiles();  
                        bufferedWriter.newLine();  
                        bufferedWriter.write(str1);  
                        bufferedWriter.flush();  
                        this.list = compaireArray(existingFiles, currentFiles, bufferedWriter);  
                        fileCount = currentFilecount;  
                        existingFiles = currentFiles;  
                        bufferedWriter.close();  
                   } catch (IOException e) {  
                        e.printStackTrace();  
                   }  
              }  
         }  
         try {  
              Thread.sleep(7000L);  
         } catch (InterruptedException e) {  
              e.printStackTrace();  
         }  
         thread.run();  
    }  
    private List<File> compareArray(File[] existingFiles2, File[] currentFiles,  
              BufferedWriter buffWriter) {  
         List<File> list = new ArrayList<File>();  
         int counter = 0;  
         boolean flag = true;  
         for (int i = 0; i < existingFiles2.length; ++i) {  
              for (int j = 0; j < currentFiles.length; ++j) {  
                   if (existingFiles2[i].getName().equals(  
                             currentFiles[j].getName())) {  
                        flag = true;  
                        break;  
                   }  
                   flag = false;  
              }  
              if (!(flag)) {  
                   list.add(existingFiles2[i]);  
                   ++counter;  
              }  
         }  
         if (list != null) {  
              for (int i = 0; i < list.size(); ++i) {  
                   String str = null;  
                   if (((File) list.get(i)).isDirectory()) {  
                        str = "Folder:" + ((File) list.get(i)).getName();  
                   } else {  
                        System.out  
                                  .println("File:" + ((File) list.get(i)).getName());  
                        str = "File:" + ((File) list.get(i)).getName();  
                   }  
                   try {  
                        buffWriter.newLine();  
                        buffWriter.write(str);  
                        buffWriter.flush();  
                   } catch (IOException e) {  
                        e.printStackTrace();  
                   }  
              }  
         }  
         return list;  
    }  
    private List<File> compaireArray(File[] existingFiles2, File[] currentFiles,  
              BufferedWriter buffWriter) {  
         List<File> list = new ArrayList<File>();  
         int counter = 0;  
         boolean flag = true;  
         for (int i = 0; i < currentFiles.length; ++i) {  
              for (int j = 0; j < existingFiles2.length; ++j) {  
                   if (currentFiles[i].getName().equals(  
                             existingFiles2[j].getName())) {  
                        flag = true;  
                        break;  
                   }  
                   flag = false;  
              }  
              if (!(flag)) {  
                   list.add(currentFiles[i]);  
                   ++counter;  
              }  
         }  
         if (list != null) {  
              for (int i = 0; i < list.size(); ++i) {  
                   String str = null;  
                   if (((File) list.get(i)).isDirectory()) {  
                        str = "New Folder:" + ((File) list.get(i)).getName();  
                   } else  
                        str = "New File:" + ((File) list.get(i)).getName();  
                   try {  
                        buffWriter.newLine();  
                        buffWriter.write(str);  
                        buffWriter.flush();  
                   } catch (IOException e) {  
                        e.printStackTrace();  
                   }  
              }  
         }  
         return list;  
    }  
    public List<File> getNewFile() {  
         return this.list;  
    }  
}
