package com.pandakings;

import com.pandakings.utilities.Contact;
import com.pandakings.utilities.Payment;
import com.pandakings.utilities.Student;
import com.pandakings.utilities.enumclass.Belt;
import com.pandakings.utilities.enumclass.ExpirationStatus;
import com.pandakings.utilities.enumclass.ParentRelationship;
import com.pandakings.utilities.enumclass.PaymentStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PandaKings extends JFrame implements Runnable, WindowListener {
  private static final Color HEADER_BG = new Color(255, 0, 0);
  private static final Color MAIN_FG = new Color(255, 255, 255);
  private static final Color SIDE_BG = new Color(0, 0, 200);
  private static final Color BOTTOM_BG = new Color(184, 207, 229);
  private static final Color BOTTOM_FG = new Color(51, 51, 51);
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("mm/dd/yyyy");
  private static final int UNKNOWN = -1;
  private static final long serialVersionUID = 20180813;
  private static final String APPLICATION_NAME = "Panda King's Tae Kwon Do";
  private static final String CONTACT_INFO_FILE = "csv/contact-list.csv";
  private static final String PAYMENT_INFO_FILE = "csv/payment-list.csv";
  private static final String STUDENT_INFO_FILE = "csv/student-list.csv";
  
  private static File contactListFile;
  private static File paymentListFile;
  private static File studentListFile;
  private static List<Contact> contactList;
  private static List<Payment> paymentList;
  private static List<Student> studentList;
  
  private boolean isInformationSaved;
  private JButton informationBtn;
  private JButton registrationBtn;
  private JButton paymentBtn;
  private JButton promotionBtn;
  private JLabel status;
  private JLabel companyNameLabel;
  private JLabel currentTabLabel;
  private JLabel dateLabel;
  private JMenu fileMenue;
  private JMenuItem fileSave;
  private JMenuItem fileExit;
  private JPanel mainPanel;
  
  public PandaKings() {
    addWindowListener(this);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setTitle(APPLICATION_NAME);
    setIcon();
    
    loadContactData();
    loadPaymentData();
    loadStudentData();
    
    setupUserInterface();
    setupInformation();//Show the student info w/ contact (Reading purpose).
    
    enableControls(true);
    pack();
    
    setStatus("");
    setVisible(true);
  }
  
  private void setupUserInterface() {
    setupControls();
    setupMenus;
    
    getContentPane().setLayout(new BorderLayout());
    
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BorderLayout());
    getContentPane().add(headerPanel, BorderLayout.NORTH);
    headerPanel.add(companyNameLabel, BorderLayout.WEST);
    headerPanel.add(currentTabLabel, BorderLayout.CENTER);
    headerPanel.add(dateLabel, BorderLayout.EAST);
    
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    getContentPane().add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(setupOperationPanel(), BorderLayout.WEST);
    mainPanel.add(setupStatusPanel(), BorderLayout.SOUTH);
  }
  
  private void setupControls() {
//    private JLabel status;
//    private JLabel companyNameLabel;
//    private JLabel currentTabLabel;
//    private JLabel dateLabel;
    informationBtn = new JButton("Student Information");
    informationBtn.setToolTipText("Show every student's general information");
    informationBtn.addActionListener(new informationListener());
    
    registrationBtn = new JButton("Registration");
    registrationBtn.setToolTipText("Register new student");
    registrationBtn.addActionListener(new registrationListener());
    
    paymentBtn = new JButton("Payment");
    paymentBtn.setToolTipText("Check payment records");
    paymentBtn.addActionListener(new paymentListener());
    
    promotionBtn = new JButton("Promotion");
    promotionBtn.setToolTipText("Check students who are ready for promotion test");
    promotionBtn.addActionListener(new promotionListener());
  }
  
  private JPanel setupOperationPanel() {
    JPanel operationPanel = new JPanel();
    
    operationPanel.setLayout(new GridLayout(4,1));
    operationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    operationPanel.add(makeFlowPanel(informationBtn, FlowLayout.CENTER));
    operationPanel.add(makeFlowPanel(registrationBtn, FlowLayout.CENTER));
    operationPanel.add(makeFlowPanel(paymentBtn, FlowLayout.CENTER));
    operationPanel.add(makeFlowPanel(promotionBtn, FlowLayout.CENTER));
    
    return operationPanel;
  }
  
  private JPanel setupStatusPanel() {
    JPanel statusPanel = new JPanel();
    
    statusPanel.setLayout(new GridLayout(1,1));
    statusPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Status"));
    statusPanel.add(makeFlowPanel(status, FlowLayout.LEFT));
    
    return statusPanel;
  }
  
  private JPanel makeFlowPanel(JComponent component, int alignment) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(alignment));
    panel.add(component);
    
    return panel;
  }
  
  private void setIcon() {
    try {
      ImageIcon icon = new ImageIcon("com/pandakings/images/AppIcon.png");
      setIconImage(icon.getImage());
    } catch (Throwable throwable) {
      setStatus("Cannot load icon");
    }
  }
  
  private void loadStudentData() {
    try {
      studentList = new ArrayList<Student>();
      studentListFile = new File(STUDENT_INFO_FILE);
      
      if (!studentListFile.createNewFile()) {
        Scanner studentFileScanner = new Scanner(studentListFile);
        Student currentStudent;
        String lineInfo;
        String[] informations;
        String name;
        Belt currentBelt;
        String contactListStr;
        String[] contactListParts;
        List<Integer> contactListIds = new ArrayList<Integer>();
        List<Contact> currentStudentContactList = new ArrayList<Contact>();
        int paymentAmount;
        PaymentStatus paymentState;
        ExpirationStatus expiredState;
        Date birthday;
        Date startDate;
        Date expiredDate;
        
        while (studentFileScanner.hasNext()) {
          lineInfo = studentFileScanner.nextLine();
          informations = lineInfo.split(",");
          name = informations[0];
          currentBelt = Belt.getBelt(Integer.parseInt(informations[1]));
          contactListStr = informations[2];
          paymentAmount = Integer.parseInt(informations[3]);
          paymentState = PaymentStatus.getPaymentStatus(Integer.parseInt(informations[4]));
          expiredState = ExpirationStatus.getExpirationStatus(Integer.parseInt(informations[5]));
          birthday = DATE_FORMAT.parse(informations[6]);
          startDate = DATE_FORMAT.parse(informations[7]);
          expiredDate = DATE_FORMAT.parse(informations[8]);
          
          contactListStr = contactListStr.replace("[","");
          contactListStr = contactListStr.replace("]","");
          contactListParts = contactListStr.split("/");
          for (String str : contactListParts) {
            contactListIds.add(Integer.parseInt(str));
          }
          
          Contact studentContact;
          for (int id : contactListIds) {
            for (int i = 0; i < contactList.size(); i++) {
              studentContact = contactList.get(i);
              if (studentContact.getId() == id) {
                currentStudentContactList.add(studentContact);
                break;
              }
            }
          }
          
          currentStudent = new Student(name, currentBelt, currentStudentContactList, 
              paymentAmount, paymentState, expiredState, birthday, startDate, expiredDate);
          
          studentList.add(currentStudent);
        }
        studentFileScanner.close();
      }
    } catch (IOException ioe) {
      setStatus("Student list file cannot be loaded.");
    } catch (NumberFormatException nfe) {
      setStatus("File contain non-numeric payment amount");
    } catch (ParseException e) {
      setStatus("Date format incorrect");
    } catch (SecurityException se) {
      setStatus("Student-info-file cannot be access due to security rights");
    }
  }
  
  private void loadContactData() {
    try {
      contactList = new ArrayList<Contact>();
      contactListFile = new File(CONTACT_INFO_FILE);
      if (!contactListFile.createNewFile()) {
        Scanner fileScanner = new Scanner(contactListFile);
        Contact currentContact;
        String lineInfo;
        String[] informations;
        int id;
        String name;
        String phone;
        ParentRelationship relationship;
        String otherRelationship;
        
        while (fileScanner.hasNext()) {
          lineInfo = fileScanner.nextLine();
          informations = lineInfo.split(",");
          id = Integer.parseInt(informations[0]);
          name = informations[1];
          phone = informations[2];
          relationship = ParentRelationship.getParentRelationship(informations[3]);
          if (relationship == ParentRelationship.OTHER) {
            otherRelationship = informations[4];
          } else {
            otherRelationship = "";
          }
          
          currentContact = new Contact(id, name, phone, relationship, otherRelationship);
          contactList.add(currentContact);
        }
        
        fileScanner.close();
      }
    } catch (NumberFormatException nfe) {
      setStatus("Contact id is nun-numeric");
    } catch (IOException ioe) {
      setStatus("Cannot load the contact file");
    } catch (SecurityException se) {
      setStatus("Cannot access the contact file due to security rights");
    }
  }
  
  private void setStatus(String message) {
    status.setText(message);
  }
  
  @Override
  public void windowOpened(WindowEvent e) {
  }

  @Override
  public void windowClosing(WindowEvent e) {
    closeApplication();
  }

  @Override
  public void windowClosed(WindowEvent e) {
  }

  @Override
  public void windowIconified(WindowEvent e) {
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
  }

  @Override
  public void windowActivated(WindowEvent e) {
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
  }
  
  /**
   * The execution point of the program.
   * 
   * @param args Array of input, (not used).
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new PandaKings();
      }
    });
  }
}
