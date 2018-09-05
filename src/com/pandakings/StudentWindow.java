package com.pandakings;

import com.pandakings.utilities.Contact;
import com.pandakings.utilities.Student;
import com.pandakings.utilities.enumclass.Belt;
import com.pandakings.utilities.enumclass.Club;
import com.pandakings.utilities.enumclass.ExpirationStatus;
import com.pandakings.utilities.enumclass.ParentRelationship;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Student window for showing specific student.
 * 
 * @author Dickfoong
 */
public class StudentWindow extends JFrame implements Runnable {
  /**
   * ID of this student window.
   */
  private static final long serialVersionUID = 201808230201L;
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
  private static final Belt[] BELT_LEVELS = {Belt.WHITE, Belt.YELLOW, Belt.ORANGE,
      Belt.GREEN, Belt.BLUE, Belt.PURPLE, Belt.RED, Belt.BROWN,
      Belt.RED_BLACK, Belt.SEMI_BLACK, Belt.BLACK};
  private static final Club[] CLUB_TYPES = {Club.NORMAL, Club.BLACK_BELT_CLUB, Club.MASTER_CLUB};

  private Student student;
  private JLabel nameLabel;
  private JLabel statusLabel;
  private JComboBox<Belt> currentBelt;
  private JComboBox<Club> club;
  private JTextField expiredDateTextField;
  private JPanel mainPanel;
  private JPanel informationPanel;
  private JPanel colorPanel;
  private JPanel contactPanel;
  private JPanel addContactBtnPanel;
  private JButton saveBtn;
  private JButton addContactBtn;

  public StudentWindow(Student student) {
    this.student = student;
  }

  @Override
  public void run() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle(student.getName());

    setupUserInterface();

    pack();
    setVisible(true);
    setLocationRelativeTo(null);
  }

  private void setupUserInterface() {
    setupControls();
    setEnable(false);

    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(2,1));
    mainPanel.add(setupStudentInfoPanel());
    mainPanel.add(setupContactInfoPanel());

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(mainPanel, BorderLayout.CENTER);
    getContentPane().add(setupSaveBtnPanel(), BorderLayout.NORTH);
  }

  private JPanel setupSaveBtnPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    panel.add(saveBtn);

    return panel;
  }

  private JPanel setupStateInfoPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1,2));
    panel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Expired Status"));

    panel.add(makeFlowLayoutPanel(statusLabel, FlowLayout.RIGHT));
    panel.add(expiredDateTextField);

    return panel;
  }

  private JPanel setupContactInfoPanel() {
    contactPanel = new JPanel();
    contactPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Contact"));
    contactPanel.setLayout(new GridLayout(0,1));

    JLabel name;
    JTextField phone;
    JLabel relationship;
    JLabel other;
    JButton editBtn;
    JButton removeBtn;
    JPanel btnPanel;


    List<Contact> contactList = student.getContactList();
    for (Contact contact : contactList) {
      JPanel linePanel = new JPanel();
      linePanel.setLayout(new GridLayout(1,5));

      name = new JLabel(contact.getName());
      phone = new JTextField(contact.getPhone());
      relationship = new JLabel(contact.getRelationship().description());
      other = new JLabel(contact.getOtherRelationship());
      setTextFieldEditable(phone, false);

      editBtn = new JButton("Edit");
      editBtn.addActionListener(new EditContactActionListener(contact, phone, editBtn));

      removeBtn = new JButton("Remove");
      removeBtn.addActionListener(new RemoveContactActionListener(contact));

      btnPanel = makeFlowLayoutPanel(editBtn, FlowLayout.LEFT);
      btnPanel.add(removeBtn);

      linePanel.setBorder(BorderFactory.createLineBorder(Color.black));
      linePanel.add(makeFlowLayoutPanel(name, FlowLayout.CENTER));
      linePanel.add(makeFlowLayoutPanel(phone, FlowLayout.CENTER));
      linePanel.add(makeFlowLayoutPanel(relationship, FlowLayout.CENTER));
      linePanel.add(makeFlowLayoutPanel(other, FlowLayout.CENTER));
      linePanel.add(btnPanel);

      contactPanel.add(linePanel);
    }
    addContactBtnPanel = makeFlowLayoutPanel(addContactBtn, FlowLayout.CENTER);
    contactPanel.add(addContactBtnPanel);

    return contactPanel;
  }

  private JPanel setupStudentInfoPanel() {
    informationPanel = new JPanel();
    informationPanel.setLayout(new BorderLayout());

    colorPanel = getBeltColorPanel(student.getCurrentBelt());
    if (colorPanel != null) {
      informationPanel.add(colorPanel, BorderLayout.NORTH);
    }
    informationPanel.add(getInfoPanel(), BorderLayout.CENTER);
    informationPanel.add(setupStateInfoPanel(), BorderLayout.SOUTH);

    return informationPanel;
  }

  private JPanel getInfoPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2,1));
    panel.add(makeFlowLayoutPanel(nameLabel, FlowLayout.CENTER));

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(1,2));
    infoPanel.add(currentBelt);
    infoPanel.add(club);
    panel.add(infoPanel);

    return panel;
  }

  private JPanel getBeltColorPanel(Belt belt) {
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(mainPanel.getSize().width, 20));
    JPanel optionalPanel1 = new JPanel();
    JPanel optionalPanel2 = new JPanel();
    JPanel optionalPanel3 = new JPanel();

    switch (belt) {
      case WHITE:
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(Color.white);
        panel.add(optionalPanel1);
        return panel;
      case YELLOW:        
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(Color.yellow);
        panel.add(optionalPanel1);
        return panel;
      case ORANGE:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(255,140,26));
        panel.add(optionalPanel1);
        return panel;
      case GREEN:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(45,134,45));
        panel.add(optionalPanel1);
        return panel;
      case BLUE:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(0,0,230));
        panel.add(optionalPanel1);
        return panel;
      case PURPLE:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(89,0,179));
        panel.add(optionalPanel1);
        return panel;
      case RED:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(179,45,0));
        panel.add(optionalPanel1);
        return panel;
      case BROWN:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(134,89,45));
        panel.add(optionalPanel1);
        return panel;
      case RED_BLACK:
        panel.removeAll();
        panel.setLayout(new GridLayout(2,1));
        optionalPanel1.setBackground(new Color(179,45,0));
        optionalPanel2.setBackground(new Color(0,0,0));
        panel.add(optionalPanel1);
        panel.add(optionalPanel2);
        return panel;
      case SEMI_BLACK:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(new Color(179,45,0));
        optionalPanel1.setLayout(new GridLayout(2,1,5,5));
        optionalPanel2.setBackground(Color.black);
        optionalPanel3.setBackground(Color.black);
        optionalPanel1.add(optionalPanel2);
        optionalPanel1.add(optionalPanel3);
        panel.add(optionalPanel1);
        return panel;
      case BLACK:
        panel.removeAll();
        panel.setLayout(new GridLayout(1,1));
        optionalPanel1.setBackground(Color.black);
        panel.add(optionalPanel1);
        return panel;
      default:
        return null;
    }
  }

  private JPanel makeTitledGridLayoutPanel(JComponent component, String title, int row, int col) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(row,col));
    panel.setBorder(BorderFactory.createTitledBorder(title));
    panel.add(component);

    return panel;
  }

  private JPanel makeFlowLayoutPanel(JComponent component, int alignment) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(alignment));
    panel.add(component);

    return panel;
  }
  
  private void setupControls() {
    nameLabel = new JLabel(student.getName());
    nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.BOLD, 25));

    currentBelt = new JComboBox<Belt>(BELT_LEVELS);
    currentBelt.setSelectedIndex(student.getCurrentBelt().description());
    currentBelt.setEnabled(true);
    currentBelt.addActionListener(new CurrentBeltActionListener());

    club = new JComboBox<Club>(CLUB_TYPES);
    club.setSelectedItem(student.getClub());
    club.setEnabled(true);
    club.addActionListener(new ClubActionListener());

    statusLabel = new JLabel(getStatusText());
    statusLabel.setToolTipText("Click to edit the expired date");
    statusLabel.addMouseListener(new ExpiredDateTextFieldMouseListener());
    statusLabel.setFont(new Font(statusLabel.getFont().getName(), Font.PLAIN, 15));

    expiredDateTextField = new JTextField(DATE_FORMAT.format(student.getExpiredDate()));
    expiredDateTextField.setToolTipText("Click to edit the expired date");
    setTextFieldEditable(expiredDateTextField, false);
    expiredDateTextField.addMouseListener(new ExpiredDateTextFieldMouseListener());
    expiredDateTextField.setFont(
        new Font(expiredDateTextField.getFont().getName(), Font.PLAIN, 15));

    saveBtn = new JButton("Save");
    saveBtn.addActionListener(new SaveActionListener());

    addContactBtn = new JButton("Add Contact");
    addContactBtn.addActionListener(new AddContactActionListener());
  }

  private String getStatusText() {
    String conjunction;
    if (student.getExpiredState() == ExpirationStatus.EXPIRED) {
      conjunction = " on ";
    } else {
      conjunction = " until ";
    }

    String text = student.getExpiredState().description() + conjunction;

    return text;
  }

  private void saveInformation() {
    try {
      student.setCurrentBelt((Belt) currentBelt.getSelectedItem());
      student.setClub((Club) club.getSelectedItem());
      student.setExpiredDate(DATE_FORMAT.parse(expiredDateTextField.getText()));
      statusLabel.setText(getStatusText());
      setTextFieldEditable(expiredDateTextField, false);

      refreshColorPanel();
      setEnable(false);
    } catch (ParseException pe) {
      expiredDateTextField.setText("Date format incorrect: MM/DD/YYYY");
    }

  }

  private void setTextFieldEditable(JTextField textField, boolean enable) {
    textField.setEditable(enable);
    if (enable) {
      textField.setBorder(BorderFactory.createLineBorder(Color.blue));
      textField.setBackground(Color.white);
    } else {
      textField.setBorder(BorderFactory.createLineBorder(getBackground()));
      textField.setBackground(getBackground());
    }
  }

  private void setEnable(boolean enable) {
    saveBtn.setEnabled(enable);
  }

  private void refreshColorPanel() {
    informationPanel.remove(colorPanel);
    colorPanel = getBeltColorPanel((Belt)currentBelt.getSelectedItem());
    informationPanel.add(colorPanel, BorderLayout.NORTH);

    revalidate();
    pack();
  }

  private void refreshContactPanel() {
    contactPanel.removeAll();
    mainPanel.remove(contactPanel);
    mainPanel.add(setupContactInfoPanel());

    revalidate();
    pack();
  }

  private void setupContactForm() {
    JPanel contactForm = new JPanel();
    contactForm.setLayout(new GridLayout(1,5));

    JTextField nameInput = new JTextField();
    contactForm.add(makeTitledGridLayoutPanel(nameInput, "Name:", 1, 1));

    JTextField phoneInput = new JTextField();
    contactForm.add(makeTitledGridLayoutPanel(phoneInput, "Phone:", 1, 1));

    ParentRelationship[] parentRelationships = {
        ParentRelationship.FATHER, ParentRelationship.MOTHER,
        ParentRelationship.UNCLE, ParentRelationship.AUNT, ParentRelationship.OTHER};

    JTextField otherInput = new JTextField();

    JComboBox<ParentRelationship> relationship = 
        new JComboBox<ParentRelationship>(parentRelationships);
    relationship.setSelectedIndex(0);
    relationship.setEnabled(true);
    relationship.setEditable(false);
    relationship.addActionListener(new RelationshipActionListener(relationship, otherInput));
    contactForm.add(makeTitledGridLayoutPanel(relationship, "Relationship:", 1, 1));

    contactForm.add(makeTitledGridLayoutPanel(otherInput, "Other Relationship:", 1, 1));    
    setTextFieldEditable(otherInput, false);

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JButton add = new JButton("Add");
    add.addActionListener(new AddActionListener(nameInput, phoneInput, relationship, otherInput));
    btnPanel.add(add);
    JButton cancel = new JButton("Cancel");
    cancel.addActionListener(new CancelActionListener());
    btnPanel.add(cancel);
    contactForm.add(btnPanel);

    contactPanel.remove(addContactBtnPanel);
    contactPanel.add(contactForm);

    revalidate();
    pack();
  }

  private void addContact(String name, String phone, 
      ParentRelationship relationship, String other) {
    Contact contact = new Contact(student.getId(), name, phone, relationship, other);
    student.addContact(contact);
  }

  private void relationshipComboBoxAction(
      JComboBox<ParentRelationship> relationship, JTextField other) {
    if (relationship.getSelectedIndex() == 4) {
      setTextFieldEditable(other, true);
    } else {
      setTextFieldEditable(other, false);
    }
  }

  private class AddActionListener implements ActionListener {
    private JTextField nameInput;
    private JTextField phoneInput;
    private JComboBox<ParentRelationship> relationshipInput;
    private JTextField otherInput;

    public AddActionListener(JTextField nameInput, JTextField phoneInput,
        JComboBox<ParentRelationship> relationshipInput, JTextField otherInput) {
      this.nameInput = nameInput;
      this.phoneInput = phoneInput;
      this.relationshipInput = relationshipInput;
      this.otherInput = otherInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String name = nameInput.getText();
      String phone = phoneInput.getText();
      ParentRelationship relationship = (ParentRelationship) relationshipInput.getSelectedItem();
      String other;

      if (relationship == ParentRelationship.OTHER) {
        other = otherInput.getText();
      } else {
        other = "";
      }

      addContact(name, phone, relationship, other);
      refreshContactPanel();
    }
  }
  
  private class AddContactActionListener implements ActionListener {
    public AddContactActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupContactForm();
    }
  }

  private class CancelActionListener implements ActionListener {
    public CancelActionListener() { 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      refreshContactPanel();
    }

  }

  private class ClubActionListener implements ActionListener {
    public ClubActionListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setEnable(true);
    }
  }
  
  private class CurrentBeltActionListener implements ActionListener {
    public CurrentBeltActionListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setEnable(true);
    }
  }
  
  private class EditContactActionListener implements ActionListener {
    private JTextField phone;
    private JButton button;
    private Contact contact;

    public EditContactActionListener(Contact contact, JTextField phone, JButton button) {
      this.contact = contact;
      this.phone = phone;
      this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (button.getText().equals("Edit")) {
        setTextFieldEditable(phone, true);
        button.setText("Save");
      } else {
        setTextFieldEditable(phone, false);
        saveContactInformation();
        button.setText("Edit");
      }

    }

    private void saveContactInformation() {
      contact.setPhone(phone.getText());
    }

  }

  private class ExpiredDateTextFieldMouseListener implements MouseListener {
    public ExpiredDateTextFieldMouseListener() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
      setTextFieldEditable(expiredDateTextField, true);
      setEnable(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

  }
  
  private class RelationshipActionListener implements ActionListener {
    JComboBox<ParentRelationship> relationship;
    JTextField otherInput;

    public RelationshipActionListener(
        JComboBox<ParentRelationship> relationship, JTextField otherInput) {
      this.relationship = relationship;
      this.otherInput = otherInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      relationshipComboBoxAction(relationship, otherInput);
    }

  }
  
  private class RemoveContactActionListener implements ActionListener {
    private Contact contact;

    public RemoveContactActionListener(Contact contact) {
      this.contact = contact;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      student.removeContact(contact);
      refreshContactPanel();
    }

  }

  private class SaveActionListener implements ActionListener {
    public SaveActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      saveInformation();
    }
  }

}
