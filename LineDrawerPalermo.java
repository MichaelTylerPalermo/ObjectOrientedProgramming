/*
 * Michael Palermo
 * CPSC 24000
 * 11/28/18
 * This app draws a diagram made of line segments.
 * The user will add line segments by clicking on a panel.
 * This will illustrate how to respond to mouse events.
 * 
 * Process:
 * 1. Create the model - Point
 * 2. Test the Point class in main - just display a text printout
 * 3. Create a LineFrame that contains a LinePanel
 * 4. Equip the LinePanel with the ability to draw interconnected points
 * 5. Add a MouseListener so that a new point is added whenever the user
 * clicks the panel
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

// Our model Class
class Point {
    private int x;
    private int y;
    private int pointSize;
    private Color color;
    private boolean noLine;
    
    public int getPointSize() {
        return pointSize;
    }
    public void setPointSize(int ps) {
        if (ps < 0) {
            pointSize = 10;
        } else {
            pointSize = ps;
        }
    }
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    
	public boolean isNoLine() {
		return noLine;
	}
	public void setNoLine(boolean noLine) {
		this.noLine = noLine;
	}
    public Point(int x, int y, int ps, Color color,boolean noLine) {
        setX(x);
        setY(y);
        setPointSize(ps);
        setColor(color);
        setNoLine(noLine);
    }
    //overloaded constructor 
    public Point(int x, int y) { 
		setX(x);
		setY(y);
		setPointSize(10);
		setColor(Color.BLACK);
	}
	@Override
    public String toString() {
        return String.format("x=%d, y=%d, Point Size=%d",x,y,pointSize);
    }
}
// Class to draw the points and lines in the drawing panel.
// Also control mouse event and key events.
class LinePanel extends JPanel implements MouseListener,MouseMotionListener,
KeyListener {
	int x,y;
    private ArrayList<Point> points;
    private String message;
    private Color color;
    private int pointSize;
    private boolean lineEnable; // controls if all the points are connected with lines
    private boolean noLine; // controls an if an individual line is drawn
    
    public LinePanel(ArrayList<Point> points) {
        this.points = points;
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        message = "sup"; 
        pointSize = 10;
        color = Color.BLACK;
        lineEnable = true;
        noLine = false;
    }    
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
           setColor(Color.RED);
        } else if (e.getKeyCode() == KeyEvent.VK_B) {
           setColor(Color.BLUE);
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
        	setColor(Color.GREEN);
        } else if (e.getKeyCode() ==KeyEvent.VK_L) {
        	setColor(Color.BLACK);
        } else if (e.getKeyCode() ==KeyEvent.VK_ESCAPE) {
        	setNoLine(true); //turn off the next point having a line connected from the previous point
        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        points.add(new Point(x,y,pointSize,color,noLine));
        setNoLine(false);
        requestFocusInWindow();
        repaint();
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        message = String.format("Location: (%d, %d)", x, y);
        requestFocusInWindow();
        repaint();
    }
    public void mouseDragged(MouseEvent e) {
        points.add(new Point(e.getX(),e.getY()));
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) { // Drawing the points
        super.paintComponent(g);
        Point prevPoint = null; // Initially we have no previous point
        
        for (Point p : points) {
        	g.setColor(p.getColor());
            g.fillOval(p.getX(), p.getY(), pointSize, pointSize);
            if (prevPoint != null && getLineEnable() == true && p.isNoLine() == false) {
                g.drawLine(prevPoint.getX()+pointSize/2, 
                        prevPoint.getY()+pointSize/2,
                        p.getX()+pointSize/2, p.getY()+pointSize/2);
            }
            prevPoint = p;
        }
        // add the guider line to show where we will place the next line
        if (prevPoint!=null && getLineEnable()==true && isNoLine()==false) {
        	g.drawLine(prevPoint.getX()+pointSize/2,
                    prevPoint.getY()+pointSize/2,x,y);
        }
        g.drawString(message, 200, 400);
    }
	public Color getColor() {
		return color;
	}
	public void setColor(Color col) {
		color = col;
	}
    public int getPointSize() {
        return pointSize;
    }
    public void setPointSize(int ps) {
        if (ps < 0) {
            pointSize = 5;
        } else {
            pointSize = ps;
        }
    }
	public boolean getLineEnable() {
		return lineEnable;
	}
	public void setLineEnable(boolean lineEnable) {
		this.lineEnable = lineEnable;
	}
	public boolean isNoLine() {
		return noLine;
	}
	public void setNoLine(boolean noLine) {
		this.noLine = noLine;
	}
}

class PointRandomizer {
    private Random rnd;
    public void randomize(ArrayList<Point> points) {
        for (Point p : points) {
            p.setX(p.getX() + -10 + rnd.nextInt(20));
        }
    }
    public PointRandomizer() {
        rnd = new Random();
    }
}
class LineFrame extends JFrame implements ActionListener,KeyListener {
    private ArrayList<Point> points;
    private PointRandomizer pr;
    private Timer slow,med,fast; // controls the speed of the timers for the points
    private LinePanel lpan;
    private JTextField txtPointSize;
    
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            lpan.setPointSize(Integer.parseInt(txtPointSize.getText()));
            lpan.repaint();
        } 
    }
    public void configureMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu mnuFile = new JMenu("File");
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnuFile.add(miExit);
        bar.add(mnuFile);
        JMenu mnuEdit = new JMenu("Edit");
        JMenuItem miClear = new JMenuItem("Clear");
        miClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                points.clear();
                repaint();
            }
        });
        mnuEdit.add(miClear);
        JMenuItem miUndo = new JMenuItem("Undo");
        miUndo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// use built inArrayList methods remove() and size()
				points.remove((points.size()-1)); // removes the last point of the arraylist
				repaint();
        	}
        });
        mnuEdit.add(miUndo);
        bar.add(mnuEdit);
        JMenu mnuColor = new JMenu("Color");
        JMenuItem miRed = new JMenuItem("Red");
        miRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lpan.setColor(Color.RED);
                repaint();
            }
        });
        mnuColor.add(miRed);
        JMenuItem miBlue = new JMenuItem("Blue");
        miBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lpan.setColor(Color.BLUE);
                repaint();
            }
        });
        mnuColor.add(miBlue);
        JMenuItem miGreen = new JMenuItem("Green");
        miGreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lpan.setColor(Color.GREEN);
                repaint();
            }
        });
        mnuColor.add(miGreen);
        bar.add(mnuColor);
        JMenu mnuTimer = new JMenu("Timer");
        JMenuItem miStart = new JMenuItem("Start");
        miStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                slow.start();
            }
        });
        mnuTimer.add(miStart);
        JMenuItem miStop = new JMenuItem("Stop");
        miStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fast.stop();
                med.stop();
                slow.stop();
            }
        });
        mnuTimer.add(miStart);
        mnuTimer.add(miStop);
        bar.add(mnuTimer);
        JMenu mnuTSpeed = new JMenu("Timer Speed");
		JMenuItem miFast = new JMenuItem("Fast"); 
		miFast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fast.start();
				med.stop();
				slow.stop();
			}
		});
		mnuTSpeed.add(miFast);
		JMenuItem miMed = new JMenuItem("Medium"); 
		miMed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fast.stop();
				med.start();
				slow.stop();
			}
		});
		mnuTSpeed.add(miMed);
		JMenuItem miSlow = new JMenuItem("Slow"); 
		miSlow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fast.stop();
				med.stop();
				slow.start();
			}
		});
		mnuTSpeed.add(miSlow);
		bar.add(mnuTSpeed);
        JMenu mnuPointSize = new JMenu ("PointSize");
        JMenuItem miSmall = new JMenuItem("Small");
        miSmall.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		lpan.setPointSize(5);
        		repaint();
        	}
        });
        mnuPointSize.add(miSmall);
        JMenuItem miMedium = new JMenuItem("Medium");
        miMedium.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		lpan.setPointSize(15);
        		repaint();
        	}
        });
        mnuPointSize.add(miMedium);
        JMenuItem miLarge = new JMenuItem("Large");
        miLarge.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		lpan.setPointSize(25);
        		repaint();
        	}
        });
        mnuPointSize.add(miLarge);
        bar.add(mnuPointSize);
        JMenu lineEnabler = new JMenu ("Line Enable");
        JMenuItem lineOn = new JMenuItem ("ON");
        lineOn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lpan.setLineEnable(true);
        		repaint();
        	}
        });
        lineEnabler.add(lineOn);
        JMenuItem lineOff = new JMenuItem ("OFF");
        lineOff.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lpan.setLineEnable(false);
        		repaint();
        	}
        });
        lineEnabler.add(lineOff);
        bar.add(lineEnabler);
        setJMenuBar(bar);
    }
    public void actionPerformed(ActionEvent e) {
        pr.randomize(points);
        repaint();
    }
    public void configureUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100,100,500,500);
        setTitle("LineDrawer V0.1");
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        lpan = new LinePanel(points);
        c.add(lpan,BorderLayout.CENTER);
        JPanel panSouth = new JPanel();
        panSouth.setLayout(new FlowLayout());
        JLabel lblPointSize = new JLabel("Point Size");
        txtPointSize = new JTextField(5);
        txtPointSize.addKeyListener(this);
        panSouth.add(lblPointSize);
        panSouth.add(txtPointSize);
        c.add(panSouth,BorderLayout.SOUTH);
        configureMenu();
    }
    public LineFrame(ArrayList<Point> points) {
        this.points = points;
        pr = new PointRandomizer();
        // initializing how fast each timer is
        slow = new Timer(1000,this);
        med = new Timer(400,this);
        fast = new Timer(50,this);
        configureUI();
    }
}
public class LineDrawerPalermo {
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(17,21,10,Color.BLACK,false));
        points.add(new Point(58,120,10,Color.BLACK,false));
        
        for (Point p : points) {
            System.out.println(p);
        }
        LineFrame lf = new LineFrame(points);
        lf.setVisible(true);
    }
}


