import java.awt.*;

public class Graphic extends Frame {

    Button button1 = new Button("Bottone1");
    Button button2 = new Button("Bottone2");
    Button button3 = new Button("Bottone3");



    public Graphic(){

        super("Progetto ADM");
        setLocation(500,200);
        setSize(500,500);
        setVisible(true);


        setLayout(new FlowLayout());
        add(button1);
        add(button2);
        add(button3);

    }





}
