/*****************
Author: Yours truly
Date: Before I knew Java all that well
*****************/

import java.lang.*;
import java.util.*;
import java.util.Scanner;

public class VigEncrypt{
  int tableSize = 126;
  char[][] charArray = getCharArray();
  String key;
  String encryptedText;
  String Text;
  String keyManipulated = "";



  public char[][] getCharArray(){
    char[][] retArray = new char[tableSize][tableSize];
    for(int i = tableSize; i>0 ; i--){
      int ascii = tableSize;
      int temp = ascii - (tableSize - i);
      retArray[i-1][tableSize-1] = (char)temp;
    }
    for(int i = tableSize - 1 ; i>0 ; i--){
      for(int j = 0 ; j<tableSize ; j++ ){
        if(j == 0){
          retArray[j][i-1] = retArray[tableSize-1][i];
        }
        else{
          retArray[j][i-1] = retArray[j-1][i];
        }
      }
    }
    return retArray;
  }

  public void printArray(char[][] array){
    int numCols = array.length;
    int numRows = array.length;
    for(int i = 0 ; i<numRows ; i++){
      for(int j = 0 ; j<numCols ; j++){
        System.out.print(array[i][j]);
      }
      System.out.println();
    }

  }

  public String encode(){
    String cypherText = "";
    int positionX=0;
    int positionY=0;

    for(int i=0 ; i<Text.length() ; i++){
      for(int j=0 ; j<tableSize ; j++){
        if(Text.charAt(i) == charArray[tableSize-1][j]){
          positionX = j;
        }
        if(keyManipulated.charAt(i) == charArray[j][0]){
          positionY = j;
        }
      }
      cypherText += charArray[positionY][positionX];
    }
    return cypherText;
  }

  public String decode(){
    String decrypted = "";
    int positionX=0;
    int positionY=0;

    for(int i=0 ; i<Text.length() ; i++){
      for(int j=0 ; j<tableSize ; j++){
        if(keyManipulated.charAt(i) == charArray[j][0]){
          positionY = j;
          for(int k=0 ; k<tableSize ; k++){
            if(Text.charAt(i) == charArray[j][k]){
              positionX = k;
            }
          }
        }

      }

      decrypted += charArray[tableSize-1][positionX];

    }
    return decrypted;

  }

  public void setKey(String key){
    this.key = key;
  }

  public void setText(String Text){
    this.Text = Text;
  }

  public void keyManipulation(){
    int keyLength = this.key.length();
    int TextLength = this.Text.length();
    int count = 0;

    for(int i=0 ; i<TextLength ; i++){
      keyManipulated += key.charAt(count);
      count++;
      if(count==keyLength){
        count = 0;
      }
    }
  }

  public String getNewKey(){
    String key;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter key: ");
    key = sc.next();
    return key;
  }

  public String getNewText(){
    String text;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter text: ");
    text = sc.nextLine();
    return text;
  }


  public static void main(String[] args){

    VigEncrypt cypher = new VigEncrypt();
    char cond;
    cypher.setKey(cypher.getNewKey());
    cypher.setText(cypher.getNewText());
    cypher.keyManipulation();


    System.out.println("Encode or decode? e or d");
    Scanner sc = new Scanner(System.in);
    cond = sc.next().toUpperCase().charAt(0);

    if(cond == 'E'){
      System.out.println("Encoding: " + cypher.Text + "\n");
      System.out.println(cypher.encode() + "\n");
    }
    else if(cond == 'D'){
      System.out.println("Decoding: " + cypher.Text + "\n");
      System.out.println(cypher.decode());
    }
    else{
      System.out.println("WRONG INPUT");
    }
  }
}
