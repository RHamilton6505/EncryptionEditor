import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;


class GUI extends JFrame implements ActionListener{

  JFrame mainFrame = new JFrame();
  JTextArea textArea = new JTextArea();
  JTextField password = new JTextField();
  JMenuBar menuBar = new JMenuBar();
  JMenu file = new JMenu("File");
  JMenuItem save = new JMenuItem("Save File");
  JMenuItem quit = new JMenuItem("Quit");
  JMenuItem storePassword = new JMenuItem("Assign Password");
  JMenuItem open = new JMenuItem("Open");



  VigEncrypt encrypt = new VigEncrypt();

  String key = new String();

  private static final long serialVersionUID = 1L;

  public GUI(){
    mainFrame.setPreferredSize(new Dimension(500, 750));
    setTitle("SAVE");

    textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    JScrollPane js = new JScrollPane(textArea);

    menuBar.add(file);
    file.add(save);
    file.add(quit);
    file.add(storePassword);
    file.add(open);


    mainFrame.add(js);
    mainFrame.add(menuBar, BorderLayout.NORTH);
    mainFrame.add(password, BorderLayout.SOUTH);


    setDefaultCloseOperation(EXIT_ON_CLOSE);
    mainFrame.setResizable(true);
    mainFrame.pack();
    mainFrame.setVisible(true);
    mainFrame.setTitle("Fancy Dancy Text Encypter");



    save.addActionListener(this);
    quit.addActionListener(this);
    storePassword.addActionListener(this);
    open.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == save){


      JFileChooser saveItem = new JFileChooser();
      saveItem.setCurrentDirectory(new File (System.getProperty("user.home") + System.getProperty("file.separator")+ "Memos"));
      int option = saveItem.showSaveDialog(this);
      File file = new File(saveItem.getSelectedFile().getPath());



      try{
        String source = textArea.getText();
        source = encryptVig(source);
        source = encryptAES(source, key);
        char buffer[] = new char[source.length()];
        source.getChars(0,source.length(),buffer,0);
        FileWriter f1 = new FileWriter(file);
        for(int i=0 ; i<buffer.length; i++){
          f1.write(buffer[i]);
        }
        f1.close();
      }
      catch(Exception ae){

      }

    }

    if(e.getSource() == open){

      try{
        JFileChooser openItem = new JFileChooser();
        openItem.setCurrentDirectory(new File (System.getProperty("user.home") + System.getProperty("file.separator")+ "Memos"));
        int option = openItem.showOpenDialog(this);
        File file = new File(openItem.getSelectedFile().getPath());
        Scanner in = new Scanner(file);
        String text = "";
        String nextChar;

        try{
          while(in.hasNext()){
            text += in.next();
          }
        }
        finally{
          text = decryptAES(text,key);
          text = decryptVig(text);
          textArea.setText(text);
          in.close();
        }
      }
      catch (FileNotFoundException e1){

      }

    }

    if(e.getSource() == quit){
      key = "";
      System.exit(1);
    }

    if(e.getSource() == storePassword){
      key = password.getText();
      password.setText("");
    }
  }

  private String encryptAES(String s, String key){
    String encrypted = AES.encrypt(s, key);
    return encrypted;
  }

  private String decryptAES(String s, String key){
    String decrypted = AES.decrypt(s, key);
    return decrypted;
  }


  public String encryptVig(String s){
    encrypt.setKey(this.key);
    encrypt.setText(s);
    encrypt.keyManipulation();
    s = encrypt.encode();
    return s;
  }

  public String decryptVig(String s){
    encrypt.setKey(this.key);
    encrypt.setText(s);
    encrypt.keyManipulation();
    s = encrypt.decode();
    return s;
  }


  public static void main(String args[]){
    GUI newGUI = new GUI();
  }
}
