package com.pandakings;

import com.pandakings.utilities.Contact;
import com.pandakings.utilities.Payment;
import com.pandakings.utilities.Student;
import com.pandakings.utilities.enumclass.Belt;
import com.pandakings.utilities.enumclass.ClubType;
import com.pandakings.utilities.enumclass.ExpirationStatus;
import com.pandakings.utilities.enumclass.ParentRelationship;
import com.pandakings.utilities.enumclass.PaymentStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PandaKings extends JFrame implements Runnable, WindowListener {
  private static final Calendar CALENDAR = Calendar.getInstance();
  private static final Color HEADER_BG = new Color(255, 0, 0);
  private static final Color MAIN_FG = new Color(255, 255, 255);
  private static final Color SIDE_BG = new Color(0, 0, 200);
  private static final Color BOTTOM_BG = new Color(184, 207, 229);
  private static final Color BOTTOM_FG = new Color(51, 51, 51);
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
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
  private static List<Integer> studentIdList;

  private boolean isInformationSaved;
  private JButton informationBtn;
  private JButton registrationBtn;
  private JButton paymentBtn;
  private JButton promotionBtn;
  private JLabel status;
  private JLabel companyNameLabel;
  private JLabel currentTabLabel;
  private JLabel dateLabel;
  private JMenu fileMenu;
  private JMenuItem fileSave;
  private JMenuItem fileExit;
  private JPanel mainPanel;

  /**
   * Setup the application.
   */
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

    enableControls();
    pack();

    setStatus("");
    setVisible(true);
  }

  private void setupUserInterface() {
    setupControls();
    setupMenus();

    getContentPane().setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BorderLayout());
    headerPanel.setBackground(HEADER_BG);
    getContentPane().add(headerPanel, BorderLayout.NORTH);
    headerPanel.add(companyNameLabel, BorderLayout.WEST);
    headerPanel.add(currentTabLabel, BorderLayout.CENTER);
    headerPanel.add(setupDatePanel(), BorderLayout.EAST);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    getContentPane().add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(setupOperationPanel(), BorderLayout.WEST);
    mainPanel.add(setupStatusPanel(), BorderLayout.SOUTH);
  }

  private void setupControls() {
    informationBtn = new JButton("Student Information");
    informationBtn.setToolTipText("Show every student's general information");
    informationBtn.addActionListener(new InformationListener());

    registrationBtn = new JButton("Registration");
    registrationBtn.setToolTipText("Register new student");
    registrationBtn.addActionListener(new RegistrationListener());

    paymentBtn = new JButton("Payment");
    paymentBtn.setToolTipText("Check payment records");
    paymentBtn.addActionListener(new PaymentListener());

    promotionBtn = new JButton("Promotion");
    promotionBtn.setToolTipText("Check students who are ready for promotion test");
    promotionBtn.addActionListener(new PromotionListener());

    companyNameLabel = new JLabel("Panda King's Taekwondo");
    companyNameLabel.setHorizontalAlignment(JLabel.CENTER);

    currentTabLabel = new JLabel("Initializing");
    currentTabLabel.setHorizontalAlignment(JLabel.CENTER);

    dateLabel = new JLabel(DATE_FORMAT.format(CALENDAR.getTime()));
    dateLabel.setHorizontalAlignment(JLabel.CENTER);

    status = new JLabel("Initializing");
  }
  
  private void enableControls() {
    fileSave.setEnabled(true);
    fileExit.setEnabled(true);
    
    colorForeground();
    
    informationBtn.setEnabled(true);
    registrationBtn.setEnabled(true);
    paymentBtn.setEnabled(true);
    promotionBtn.setEnabled(true);
  }
  
  private void setupMenus() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    fileMenu.setToolTipText("Menu items related to access files");
    menuBar.add(fileMenu);
    
    setupFileMenu();
  }
  
  private void setupFileMenu() {
    fileSave = new JMenuItem("Save updates");
    fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_MASK));
    fileSave.setMnemonic(KeyEvent.VK_S);
    fileSave.setToolTipText("Save the updates");
    fileSave.addActionListener(new FileSaveListener());
    fileMenu.add(fileSave);
    
    fileExit = new JMenuItem("Exit");
    fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.ALT_MASK));
    fileExit.setMnemonic(KeyEvent.VK_Q);
    fileExit.setToolTipText("Quit the application");
    fileExit.addActionListener(new FileExitListener());
    fileMenu.add(fileExit);
  }
  
  private class FileSaveListener implements ActionListener {
    public FileSaveListener() {
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      saveFile();
    }
  }
  
  private class FileExitListener implements ActionListener {
    public FileExitListener() {
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      closeApplication();
    }
  }
  
  private class PromotionListener implements ActionListener {
    public PromotionListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupPromotion();
    }
  }

  private class PaymentListener implements ActionListener {
    public PaymentListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupPayment();
    }
  }

  private class RegistrationListener implements ActionListener {
    public RegistrationListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupRegistration();
    }
  }

  private class InformationListener implements ActionListener {
    public InformationListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupInformation();
    }
  }

  private void saveFile() {
    
    
    isInformationSaved = true;
  }
  
  private void closeApplication() {
    if (!isInformationSaved) {
      saveFile();
    }
    
    setVisible(false);
    System.exit(0);
  }
  
  private void setupInformation() {
    setTabTitle("Information");
  }

  private void setupRegistration() {
    setTabTitle("Registration");
  }

  private void setupPayment() {
    setTabTitle("Payment");
  }

  private void setupPromotion() {
    setTabTitle("Promotion");
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

  private JPanel setupDatePanel() {
    JPanel datePanel = new JPanel();
    
    datePanel.setLayout(new GridLayout(1,1));
    datePanel.setBackground(HEADER_BG);
    datePanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Date"));
    datePanel.add(dateLabel);
    
    return datePanel;
  }
  
  private JPanel makeFlowPanel(JComponent component, int alignment) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(alignment));
    panel.add(component);

    return panel;
  }

  private void colorForeground() {
//    private static final Color HEADER_BG = new Color(255, 0, 0);
    companyNameLabel.setForeground(MAIN_FG);
//    private static final Color MAIN_FG = new Color(255, 255, 255);
//    private static final Color SIDE_BG = new Color(0, 0, 200);
//    private static final Color BOTTOM_BG = new Color(184, 207, 229);
//    private static final Color BOTTOM_FG = new Color(51, 51, 51);
    
  }
  
  private void setIcon() {
    try {
      ImageIcon icon = new ImageIcon("com/pandakings/images/AppIcon.png");
      setIconImage(icon.getImage());
    } catch (Throwable throwable) {
      setStatus("Cannot load icon");
    }
  }

  private void loadPaymentData() {
    try {
      paymentList = new ArrayList<Payment>();
      paymentListFile = new File(PAYMENT_INFO_FILE);

      if (!paymentListFile.createNewFile()) {
        Scanner paymentFileScanner = new Scanner(paymentListFile);
        Payment payment;
        String lineInfo;
        String[] informations;
        int studentId;
        int amountTotal;
        int amountLeft;
        String message;
        PaymentStatus status;

        while (paymentFileScanner.hasNext()) {
          lineInfo = paymentFileScanner.nextLine();
          informations = lineInfo.split(",");

          studentId = Integer.parseInt(informations[0]);
          amountTotal = Integer.parseInt(informations[1]);
          amountLeft = Integer.parseInt(informations[2]);
          message = informations[3];
          status = PaymentStatus.getPaymentStatus(informations[4]);

          payment = new Payment(studentId, amountTotal, amountLeft, message, status);
          paymentList.add(payment);
        }
        paymentFileScanner.close();
      }
    } catch (NullPointerException npe) {
      setStatus("Payment file cannot be open");
    } catch (IOException ioe) {
      setStatus("Payment file cannot be created");
    } catch (SecurityException se) {
      setStatus("Payment file cannot be access due to security rights");
    } catch (NumberFormatException nfe) {
      setStatus("Error on either following: StudentID, AmountTotal, AmountLeft");
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
        int id;
        String name;
        Belt currentBelt;
        List<Contact> currentStudentContactList;
        ClubType clubType;
        List<Payment> currentStudentPaymentRecord;
        ExpirationStatus expiredState;
        Date birthday;
        Date startDate;
        Date expiredDate;

        while (studentFileScanner.hasNext()) {
          lineInfo = studentFileScanner.nextLine();
          informations = lineInfo.split(",");
          id = Integer.parseInt(informations[0]);
          name = informations[1];
          currentBelt = Belt.getBelt(Integer.parseInt(informations[2]));
          clubType = ClubType.getClubType(informations[3]);
          expiredState = ExpirationStatus.getExpirationStatus(informations[4]);
          birthday = DATE_FORMAT.parse(informations[5]);
          startDate = DATE_FORMAT.parse(informations[6]);
          expiredDate = DATE_FORMAT.parse(informations[7]);

          currentStudentContactList = getStudentContactList(id);
          currentStudentPaymentRecord = getStudentPaymentRecord(id);

          currentStudent = new Student(id, name, currentBelt, currentStudentContactList,
              clubType, currentStudentPaymentRecord, expiredState, 
              birthday, startDate, expiredDate);

          studentList.add(currentStudent);
          studentIdList.add(id);
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
        int studentId;
        String name;
        String phone;
        ParentRelationship relationship;
        String otherRelationship;

        while (fileScanner.hasNext()) {
          lineInfo = fileScanner.nextLine();
          informations = lineInfo.split(",");
          studentId = Integer.parseInt(informations[0]);
          name = informations[1];
          phone = informations[2];
          relationship = ParentRelationship.getParentRelationship(informations[3]);
          if (relationship == ParentRelationship.OTHER) {
            otherRelationship = informations[4];
          } else {
            otherRelationship = "";
          }

          currentContact = new Contact(studentId, name, phone, relationship, otherRelationship);
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

  private List<Contact> getStudentContactList(int id) {
    List<Contact> studentContactList = new ArrayList<Contact>();

    for (Contact contact : contactList) {
      if (contact.getStudentId() == id) {
        studentContactList.add(contact);
      }
    }

    return studentContactList;
  }

  private List<Payment> getStudentPaymentRecord(int id) {
    List<Payment> studentPaymentRecord = new ArrayList<Payment>();

    for (Payment record : paymentList) {
      if (record.getStudentId() == id) {
        studentPaymentRecord.add(record);
      }
    }

    return studentPaymentRecord;
  }

  private void setTabTitle(String title) {
    currentTabLabel.setText(title);
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
